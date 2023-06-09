package wmii.jwzp.flashcards.model.db;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import wmii.jwzp.flashcards.model.internal.UserGroupLinkModelId;

@Entity
@IdClass(UserGroupLinkModelId.class)
@Table(name = "users_groups")
public class UserGroupLinkModel {
  // @EmbeddedId
  // UserGroupLinkModelId id = new UserGroupLinkModelId();

  @Id
  public String user_id;

  @Id
  public String group_id;

  @Column(name = "created_at")
  private @CreatedDate LocalDateTime createdAt;

  @Column(name = "access_level")
  private int accessLevel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
  public UserModel user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false)
  public StudyGroupModel group;

  public String getUserId() {
    return this.user_id;
  }

  public void setUserId(String userId) {
    this.user_id = userId;
  }

  public String getGroupId() {
    return this.group_id;
  }

  public void setGroupId(String groupId) {
    this.group_id = groupId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt() {
    this.createdAt = LocalDateTime.now();
  }

  public int getAccessLevel() {
    return accessLevel;
  }

  public void setAccessLevel(int accessLevel) {
    this.accessLevel = accessLevel;
  }

}
