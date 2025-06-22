package com.bigdata.app.config;

import lombok.NonNull;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class KafkaTopicConfig {

  @NonNull
  @Value("${application.topic.name}")
  private String topic;

  @Bean
  @Profile("local")
  public NewTopic streamingConfig() {
    Map<String, String> config = new HashMap<>();
    config.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
    config.put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)));
    config.put(TopicConfig.SEGMENT_MS_CONFIG, String.valueOf(TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)));
    config.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1048576");

    return TopicBuilder.name(topic)
      .partitions(2)
      .replicas(1)
      .configs(config)
      .build();
  }
}
