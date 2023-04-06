package wmii.jwzp.flashcards.controller;

import org.springframework.web.bind.annotation.RestController;

import wmii.jwzp.flashcards.model.api.input.UserCreationInput;
import wmii.jwzp.flashcards.model.api.output.SessionResponse;
import wmii.jwzp.flashcards.model.api.output.UserResponse;
import wmii.jwzp.flashcards.service.UserService;
import wmii.jwzp.flashcards.utils.BasicAuth;
import wmii.jwzp.flashcards.utils.Headers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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

  @PostMapping("/login")
  public ResponseEntity<SessionResponse> loginUser(@RequestHeader("Authorization") String authHeader) {
    var userInput = BasicAuth.decode(authHeader);
    var session = userService.login(userInput.getNick(), userInput.getPassword());
    var sessionResponse = new SessionResponse(session);
    return ResponseEntity.ok().headers(
        new Headers().addCookie(new HttpCookie("sid", session.getId()))).body(sessionResponse);
  }

  @PostMapping("/session")
  public ResponseEntity<SessionResponse> refreshSession(@CookieValue("sid") String authToken) {
    var session = userService.refreshSession(authToken);
    var sessionResponse = new SessionResponse(session);
    return ResponseEntity.ok().headers(
        new Headers().addCookie(new HttpCookie("sid", session.getId()))).body(sessionResponse);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logoutUser(@CookieValue("sid") String authToken) {
    userService.logout(authToken);
    return ResponseEntity.ok().headers(new Headers().addCookie(new HttpCookie("sid", ""))).build();
  }

}