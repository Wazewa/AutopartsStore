package org.korolev.autopartsstore.api.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final EventRepository eventRepository;
    private final EventProducer eventProducer;
    private final EventMapper eventMapper;

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void publishNewEvent() {
        List<EventEntity> eventList = eventRepository.findNewForUpdate(100);
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
                if(event.getRetryCount() >= 5) {
                    event.setStatus(EventStatus.FAILED);
                    log.error("Event {} failed after 5 retries", event.getId(), e);
                }
                else {
                    log.warn("Failed to send event {}, retry {}/5",
                            event.getId(), event.getRetryCount());
                }
            }
        }
    }
}
