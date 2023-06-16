package wmii.jwzp.flashcards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wmii.jwzp.flashcards.model.db.UserAchievementModel;
import wmii.jwzp.flashcards.model.internal.UserAchievementModelId;

public interface UserAchievementRepository extends JpaRepository<UserAchievementModel, UserAchievementModelId> {
  @Query(value = "select a.* from user_achievements a where a.user_id = :userid", nativeQuery = true)
  public List<UserAchievementModel> findAllByUserId(@Param("userid") String userId);

  @Query(value = "select a.* from user_achievements a where a.user_id = :userid and a.grou_id = :groupid", nativeQuery = true)
  public List<UserAchievementModel> findAllByGroupIdUserId(@Param("groupid") String groupId,
      @Param("userid") String userId);

  @Query(value = "select a.* from user_achievements a where a.user_id = :userid and a.name = :name", nativeQuery = true)
  public List<UserAchievementModel> findByUserIdAndName(@Param("userid") String userId, @Param("name") String name);

}