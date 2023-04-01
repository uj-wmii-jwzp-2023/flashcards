package wmii.jwzp.flashcards.repository;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wmii.jwzp.flashcards.model.db.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {

  Boolean existsByNick(String nick);
}