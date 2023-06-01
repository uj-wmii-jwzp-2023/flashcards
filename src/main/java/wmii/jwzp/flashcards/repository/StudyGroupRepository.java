package wmii.jwzp.flashcards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wmii.jwzp.flashcards.model.db.StudyGroupModel;

public interface StudyGroupRepository extends JpaRepository<StudyGroupModel, String> {
  @Query(value = "select g.* from groups g join users_groups l on l.group_id = g.id where l.user_id = :userid", nativeQuery = true)
  List<StudyGroupModel> findAllByUser(@Param("userid") String userId);
}