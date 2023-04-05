package wmii.jwzp.flashcards.repository;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import wmii.jwzp.flashcards.model.db.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {
  boolean existsByNick(String nick);

  UserModel findByNick(String nick);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO users (id, nick, hashed_password, created_at) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
  void insert(String id, String nick, String hashedPassword, LocalDateTime createdAt)
      throws DataIntegrityViolationException;

}