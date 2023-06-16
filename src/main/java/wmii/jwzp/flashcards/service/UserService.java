package wmii.jwzp.flashcards.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import wmii.jwzp.flashcards.model.api.input.UserCreationInput;
import wmii.jwzp.flashcards.model.db.SessionModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.SessionRepository;
import wmii.jwzp.flashcards.repository.UserRepository;
import wmii.jwzp.flashcards.utils.PasswordHash;
import wmii.jwzp.flashcards.utils.errors.BadRequest;
import wmii.jwzp.flashcards.utils.errors.NotFound;
import wmii.jwzp.flashcards.utils.errors.ResourceConflict;
import wmii.jwzp.flashcards.utils.errors.Unauthorized;

@Service
public class UserService {

  Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  UserRepository userRepository;
  @Autowired
  SessionRepository sessionRepository;

  public UserModel createUser(UserCreationInput input) {

    if (input.nick == null || input.password == null) {
      throw new BadRequest("Invalid input");
    }

    if (userRepository.existsByNick(input.nick)) {
      throw new ResourceConflict("User with nick " + input.nick + " already exists");
    }

    String hashedPassword = PasswordHash.hash(input.password);
    String id = UUID.randomUUID().toString();

    UserModel user = new UserModel();
    user.setId(id);
    user.setNick(input.nick);
    user.setHashedPassword(hashedPassword);
    user.setCreatedAt();

    try {
      userRepository.insert(user.getId(), user.getNick(), user.getHashedPassword(), user.getCreatedAt());
    } catch (DataIntegrityViolationException e) {
      throw new ResourceConflict("User with nick " + input.nick + " already exists");
    }

    return user;
  }

  public void deleteUser(String id) {
    UserModel user = userRepository.findById(id).orElse(null);

    if (user == null) {
      throw new Unauthorized("User not found");
    }

    userRepository.delete(user);
  }

  public SessionModel login(String nick, String password) {
    UserModel user = userRepository.findByNick(nick);

    if (user == null || !PasswordHash.compare(password, user.getHashedPassword())) {
      throw new Unauthorized("Incorrect credentials");
    }

    String sessionId = UUID.randomUUID().toString();
    SessionModel session = new SessionModel();
    session.setId(sessionId);
    session.setUserId(user.getId());
    session.setCreatedAt();
    session.setUpdatedAt();
    session.setExpiresAt();

    this.sessionRepository.save(session);

    return session;
  }

  public void logout(String sessionId) {
    SessionModel session = this.sessionRepository.findById(sessionId).orElse(null);

    if (session == null) {
      throw new Unauthorized("Session not found");
    }

    this.sessionRepository.delete(session);
  }

  public SessionModel refreshSession(String sessionId) {
    SessionModel session = this.sessionRepository.findById(sessionId).orElse(null);

    if (session == null) {
      throw new Unauthorized("Session not found");
    }

    if (session.isExpired()) {
      this.sessionRepository.delete(session);
      throw new Unauthorized("Session expired");
    }

    session.setUpdatedAt();
    session.setExpiresAt();

    this.sessionRepository.save(session);

    return session;
  }

  public UserModel getUserBySessionToken(String sessionId) {

    if ("".equals(sessionId)) {
      throw new Unauthorized("Not logged in");
    }

    SessionModel session = this.sessionRepository.findById(sessionId).orElse(null);

    if (session == null) {
      throw new Unauthorized("Session not found");
    }

    if (session.isExpired()) {
      this.sessionRepository.delete(session);
      throw new Unauthorized("Session expired");
    }

    UserModel user = this.userRepository.findById(session.getUserId()).orElse(null);

    if (user == null) {
      throw new Unauthorized("User not found");
    }

    return user;
  }

  public UserModel getUserById(String userId) {
    UserModel user = this.userRepository.findById(userId).orElse(null);

    if (user == null) {
      throw new NotFound("User not found");
    }

    return user;
  }

}