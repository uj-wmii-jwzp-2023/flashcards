package wmii.jwzp.flashcards.controller;

import org.springframework.web.bind.annotation.RestController;

import wmii.jwzp.flashcards.model.api.input.UserCreationInput;
import wmii.jwzp.flashcards.model.api.output.UserResponse;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired()
  private UserService userService;

  @PostMapping()
  public ResponseEntity<UserResponse> registerUser(@RequestBody UserCreationInput userInput) {
    var user = userService.createUser(userInput.getNick(), userInput.getPassword());
    var userResponse = new UserResponse(user);
    return ResponseEntity.ok(userResponse);
  }

}