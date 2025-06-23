package com.bigdata.app;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.formats.json.JsonSerializationSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkKafkaToKafkaJob {
  public static void main(String[] args) throws Exception {
    final String SASL_JAAS_CONFIG = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=\"Endpoint=sb://bigdatademo.servicebus.windows.net/;SharedAccessKeyName=SendReceiveOnly;SharedAccessKey=cH9gN10hk7jEoEp7G0PcmpWMDSElblUPt+AEhAYJJIA=\";";
    final String SASL_MECHANISM = "PLAIN";
    final String SECURITY_PROTOCOL = "SASL_SSL";

    JsonDeserializationSchema<EventMetadata> eventDeserializerSchema = new JsonDeserializationSchema<>(EventMetadata.class);
    JsonSerializationSchema<UserLikes> userLikesSerializerSchema = new JsonSerializationSchema<>();

    KafkaSource<EventMetadata> source = KafkaSource.<EventMetadata>builder()
      .setBootstrapServers("bigdatademo.servicebus.windows.net:9093")
      .setProperty("sasl.jaas.config", SASL_JAAS_CONFIG)
      .setProperty("sasl.mechanism", SASL_MECHANISM)
      .setProperty("security.protocol", SECURITY_PROTOCOL)
      .setTopics("bigdata-event")
      .setValueOnlyDeserializer(eventDeserializerSchema)
      .build();

    KafkaSink<UserLikes> sink = KafkaSink.<UserLikes>builder()
      .setBootstrapServers("bigdatademo.servicebus.windows.net:9093")
      .setProperty("sasl.jaas.config", SASL_JAAS_CONFIG)
      .setProperty("sasl.mechanism", SASL_MECHANISM)
      .setProperty("security.protocol", SECURITY_PROTOCOL)
      .setRecordSerializer(KafkaRecordSerializationSchema.<UserLikes>builder()
        .setTopic("bigdata-likes")
        .setKeySerializationSchema(element -> new SimpleStringSchema().serialize(element.getUsername()))
        .setValueSerializationSchema(userLikesSerializerSchema)
        .build())
      .build();

    StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
    environment.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source")
      .filter(event -> event.getLike().equals(true))
      .map(event -> UserLikes.builder()
        .username(event.getUsername())
        .totalLikes(1)
        .build())
      .keyBy(UserLikes::getUsername)
      .reduce((a, b) -> UserLikes.builder()
        .username(a.getUsername())
        .totalLikes(a.getTotalLikes() + b.getTotalLikes())
        .build())
      .sinkTo(sink);

    environment.execute("Flink Kafka Likes Filter Job");
  }
}