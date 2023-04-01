package wmii.jwzp.flashcards.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.UserRepository;
import wmii.jwzp.flashcards.utils.PasswordHash;
import wmii.jwzp.flashcards.utils.errors.ResourceConflict;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public UserModel createUser(String nick, String password) {
    String hashedPassword = PasswordHash.hash(password);
    String id = UUID.randomUUID().toString();

    // check if user with nick exists, if it does then reject, if not then create
    // new user
    if (userRepository.existsByNick(nick)) {
      throw new ResourceConflict("User with nick " + nick + " already exists");
    }

    UserModel user = new UserModel();
    user.setId(id);
    user.setNick(nick);
    user.setHashedPassword(hashedPassword);
    user.setCreatedAt();

    userRepository.save(user);

    return user;
  }
}