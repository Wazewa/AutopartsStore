package org.korolev.autopartsstore.api.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.korolev.autopartsstore.api.event.entity.EventEntity;
import org.korolev.autopartsstore.api.event.entity.EventStatus;
import org.korolev.autopartsstore.api.event.mapper.EventMapper;
import org.korolev.autopartsstore.api.event.repository.EventRepository;
import org.korolev.autopartsstore.api.kafka.producer.EventProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxPublisher {
    private static final int BATCH_SIZE = 20;
    private static final int RETRY_COUNT = 5;

    private final EventRepository eventRepository;
    private final EventProducer eventProducer;
    private final EventMapper eventMapper;

    @Transactional
    @SchedulerLock(name = "outbox_publisher", lockAtMostFor = "PT5M", lockAtLeastFor = "PT1S")
    @Scheduled(fixedDelay = 5000)
    public void publishNewEvent() {
        List<EventEntity> eventList = eventRepository.findNewForUpdate(BATCH_SIZE);
        if (eventList.isEmpty()) {
            return;
        }
        log.info("Found {} new events to publish", eventList.size());

        for(EventEntity event: eventList) {
            try {
                eventProducer.sendMessage(eventMapper.toMessage(event));
                event.setStatus(EventStatus.SENT);
                event.setSentAt(Instant.now());
                log.info("Sent event {} to Kafka", event.getId());
            }
            catch (Exception e) {
                event.setRetryCount(event.getRetryCount() + 1);
                if(event.getRetryCount() >= RETRY_COUNT) {
                    event.setStatus(EventStatus.FAILED);
                    log.error("Event {} failed after {} retries", event.getId(), RETRY_COUNT, e);
                }
                else {
                    log.warn("Failed to send event {}, retry {}/{}",
                            event.getId(), event.getRetryCount(), RETRY_COUNT);
                }
            }
        }
    }
}
