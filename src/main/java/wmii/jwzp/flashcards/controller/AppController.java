package wmii.jwzp.flashcards.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/hello")
public class AppController {
  Logger logger = LoggerFactory.getLogger(AppController.class);

  @GetMapping()
  public ResponseEntity<String> hello() {
    return ResponseEntity.status(200).body("Hello");
  }
}