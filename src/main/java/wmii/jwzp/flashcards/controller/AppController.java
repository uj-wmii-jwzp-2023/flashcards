package wmii.jwzp.flashcards.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/hello")
public class AppController {
  @GetMapping()
  public ResponseEntity<String> hello() {
    return ResponseEntity.status(200).body("Hello");
  }
}