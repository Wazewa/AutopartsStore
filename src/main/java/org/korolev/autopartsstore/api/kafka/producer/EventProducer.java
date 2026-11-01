package org.korolev.autopartsstore.api.kafka.producer;

import org.korolev.autopartsstore.api.kafka.exception.ProducerSendFailedException;
import tools.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.korolev.autopartsstore.api.event.dto.EventMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonMapper jsonMapper;

    public void sendMessage(EventMessage eventMessage) {
        try {
            String json = jsonMapper.writeValueAsString(eventMessage);
            kafkaTemplate.send("client-events-topic",
                    eventMessage.eventId().toString(), json).get(5, TimeUnit.SECONDS);
            log.info("Sent event to Kafka {}", eventMessage.eventId());
        }
        catch (Exception e) {
            log.error("Failed to send event {}", eventMessage.eventId(), e);
            throw new ProducerSendFailedException("Failed to send event: " + eventMessage.eventId(), e);
        }
    }
}
