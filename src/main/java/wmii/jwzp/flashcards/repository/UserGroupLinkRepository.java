package wmii.jwzp.flashcards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;
import wmii.jwzp.flashcards.model.internal.UserGroupLinkModelId;

public interface UserGroupLinkRepository extends JpaRepository<UserGroupLinkModel, UserGroupLinkModelId> {
  @Query(value = "select ug.* from users_groups ug where ug.user_id = :userid and ug.group_id = :groupid", nativeQuery = true)
  UserGroupLinkModel getById(@Param("userid") String userId, @Param("groupid") String groupId);
}