package wmii.jwzp.flashcards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import wmii.jwzp.flashcards.model.db.FlashcardSetModel;

public interface FlashcardSetRepository extends JpaRepository<FlashcardSetModel, String> {
  List<FlashcardSetModel> findAllByGroupId(String groupId);

  List<FlashcardSetModel> findAllByUserId(String userId);

  @Query(value = "select s.* from sets s where s.is_public = true", nativeQuery = true)
  List<FlashcardSetModel> findAllPublic();
}