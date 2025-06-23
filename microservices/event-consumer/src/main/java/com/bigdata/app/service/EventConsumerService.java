package com.bigdata.app.service;

import com.bigdata.app.domain.model.UserLikesMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventConsumerService {
  private final SimpMessagingTemplate template;

  public EventConsumerService(SimpMessagingTemplate template) {
    this.template = template;
  }

  @KafkaListener(topics = "${application.topic.name}")
  public void receive(UserLikesMessage userLikesMessage) {
    log.info("Message received: {}", userLikesMessage.toString());
    this.template.convertAndSend("/topic/likes", userLikesMessage);
  }
}
