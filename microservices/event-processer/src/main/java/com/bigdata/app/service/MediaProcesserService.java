package com.bigdata.app.service;

import com.bigdata.app.domain.model.EventMetadata;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MediaProcesserService {

  @NonNull
  @Value("${application.topic.name}")
  private String topic;

  private final KafkaTemplate<String, EventMetadata> kafkaTemplate;

  public MediaProcesserService(KafkaTemplate<String, EventMetadata> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public Mono<Void> produceEvents(EventMetadata eventMetadata) {
    return Mono.when(Mono.fromFuture(() -> kafkaTemplate.send(topic, eventMetadata.getUsername(), eventMetadata)))
      .doOnError(e -> log.error("Error sending event: {}", e.getMessage()))
      .then();
  }
}
