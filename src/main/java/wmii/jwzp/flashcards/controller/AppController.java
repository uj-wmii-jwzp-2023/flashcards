package wmii.jwzp.flashcards.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/")
public class AppController {
  @GetMapping(path = "/hello")
  public ResponseEntity<String> hello() {
    return ResponseEntity.status(200).body("Hello");
  }
}