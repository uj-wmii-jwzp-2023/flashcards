package wmii.jwzp.flashcards.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;
import wmii.jwzp.flashcards.model.internal.UserGroupLinkModelId;

public interface UserGroupLinkRepository extends JpaRepository<UserGroupLinkModel, UserGroupLinkModelId> {
  @Query(value = "select ug.* from users_groups ug where ug.user_id = :userid and ug.group_id = :groupid", nativeQuery = true)
  Optional<UserGroupLinkModel> getById(@Param("userid") String userId, @Param("groupid") String groupId);

  @Query(value = "select ug.* from users_groups ug where ug.group_id = :groupid", nativeQuery = true)
  List<UserGroupLinkModel> findByGroupId(@Param("groupid") String groupId);
}