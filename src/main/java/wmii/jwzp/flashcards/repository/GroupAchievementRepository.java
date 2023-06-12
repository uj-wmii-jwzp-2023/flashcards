package wmii.jwzp.flashcards.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wmii.jwzp.flashcards.model.db.GroupAchievementModel;
import wmii.jwzp.flashcards.model.internal.GroupAchievementModelId;

public interface GroupAchievementRepository extends JpaRepository<GroupAchievementModel, GroupAchievementModelId> {
  @Query(value = "select a.* from group_achievements a where a.group_id = :groupid", nativeQuery = true)
  public List<GroupAchievementModel> findAllByGroupId(@Param("groupid") String groupId);

  @Query(value = "select a.* from group_achievements a where a.group_id = :groupid and a.name = :name", nativeQuery = true)
  Optional<GroupAchievementModel> getById(@Param("groupid") String groupId, @Param("name") String name);
}