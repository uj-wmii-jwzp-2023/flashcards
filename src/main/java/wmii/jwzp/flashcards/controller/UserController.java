package wmii.jwzp.flashcards.controller;

import org.springframework.web.bind.annotation.RestController;

import wmii.jwzp.flashcards.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired()
  private UserService userService;

  @GetMapping(path = "/users")
  public ResponseEntity<String> getUsers() {
    var users = userService.findAll();
    return ResponseEntity.ok(users.toString());
  }
}