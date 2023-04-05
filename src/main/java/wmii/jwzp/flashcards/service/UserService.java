package wmii.jwzp.flashcards.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.UserRepository;
import wmii.jwzp.flashcards.utils.PasswordHash;
import wmii.jwzp.flashcards.utils.errors.ResourceConflict;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public UserModel createUser(String nick, String password) {

    if (userRepository.existsByNick(nick)) {
      throw new ResourceConflict("User with nick " + nick + " already exists");
    }

    String hashedPassword = PasswordHash.hash(password);
    String id = UUID.randomUUID().toString();

    UserModel user = new UserModel();
    user.setId(id);
    user.setNick(nick);
    user.setHashedPassword(hashedPassword);
    user.setCreatedAt();

    try {
      userRepository.insert(user.getId(), user.getNick(), user.getHashedPassword(), user.getCreatedAt());
    } catch (DataIntegrityViolationException e) {
      throw new ResourceConflict("User with nick " + nick + " already exists");
    }

    return user;
  }
}