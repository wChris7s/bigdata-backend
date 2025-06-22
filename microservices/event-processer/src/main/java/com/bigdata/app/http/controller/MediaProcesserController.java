package com.bigdata.app.http.controller;

import com.bigdata.app.http.dto.EventMetadataDTO;
import com.bigdata.app.http.mapper.EventMetadataDTOMapper;
import com.bigdata.app.service.MediaProcesserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/media-processer")
public class MediaProcesserController {

  private final MediaProcesserService mediaProcesserService;

  public MediaProcesserController(MediaProcesserService mediaProcesserService) {
    this.mediaProcesserService = mediaProcesserService;
  }

  @GetMapping()
  public Mono<ResponseEntity<Mono<Map<String, String>>>> test() {
    return Mono.just(ResponseEntity.ok(Mono.just(Map.of("message", "I'm alive..."))));
  }

  @PostMapping()
  public Mono<ResponseEntity<Mono<Object>>> processMedia(@RequestBody EventMetadataDTO payload) {
    log.debug("An event was received...");
    return Mono.fromCallable(() -> EventMetadataDTOMapper.mapToModel(payload))
      .flatMap(this.mediaProcesserService::produceEvents)
      .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())));
  }
}
