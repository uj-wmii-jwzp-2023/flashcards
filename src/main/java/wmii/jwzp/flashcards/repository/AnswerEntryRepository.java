package wmii.jwzp.flashcards.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import wmii.jwzp.flashcards.model.db.AnswerEntryModel;

public interface AnswerEntryRepository extends JpaRepository<AnswerEntryModel, String> {
  @Query(value = "select a.* from answers a where a.user_id = :userid and a.end_at is null", nativeQuery = true)
  public Optional<AnswerEntryModel> findExistingForUser(@Param("userid") String userId);

  @Query(value = "select a.* from answers a where a.user_id = :userid anda.set_id = :setid anda.end_at is null", nativeQuery = true)
  public Optional<AnswerEntryModel> findExistingForUserBySet(@Param("userid") String userId,
      @Param("setid") String setId);

  @Query(value = "select a.* from answers a where a.user_id = :userid", nativeQuery = true)
  public List<AnswerEntryModel> findAllForUser(@Param("userid") String userId);

  @Query(value = "select a.* from answers a where a.user_id = :userid and a.set_id = :setid", nativeQuery = true)
  public List<AnswerEntryModel> findAllForUserBySet(@Param("userid") String userId,
      @Param("setid") String setId);

  @Query(value = "select a.* from answers a join sets s on a.set_id = s.id where a.user_id = :userid and s.group_id = :groupid", nativeQuery = true)
  public List<AnswerEntryModel> findAllForUserByGroup(@Param("userid") String userId,
      @Param("groupid") String groupId);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO answers (id, user_id, set_id) VALUES (?1, ?2, ?3)", nativeQuery = true)
  void insert(String id, String userId, String setId)
      throws DataIntegrityViolationException;
}