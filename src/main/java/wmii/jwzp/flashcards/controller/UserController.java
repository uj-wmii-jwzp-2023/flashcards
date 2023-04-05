package wmii.jwzp.flashcards.controller;

import org.springframework.web.bind.annotation.RestController;

import wmii.jwzp.flashcards.model.api.input.UserCreationInput;
import wmii.jwzp.flashcards.model.api.input.UserLoginInput;
import wmii.jwzp.flashcards.model.api.output.SessionResponse;
import wmii.jwzp.flashcards.model.api.output.UserResponse;
import wmii.jwzp.flashcards.service.UserService;
import wmii.jwzp.flashcards.utils.Headers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
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

  // TODO: this should probably be done using Authorization header insetad of body
  @PostMapping("/login")
  public ResponseEntity<SessionResponse> loginUser(@RequestBody UserLoginInput userInput) {
    var session = userService.login(userInput.getNick(), userInput.getPassword());
    var sessionResponse = new SessionResponse(session);
    return ResponseEntity.ok().headers(
        new Headers().addCookie(new HttpCookie("sid", session.getId()))).body(sessionResponse);
  }

}