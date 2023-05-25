package wmii.jwzp.flashcards.model.db;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import wmii.jwzp.flashcards.model.internal.UserGroupLinkModelId;

@Entity
// @IdClass(UserGroupLinkModelId.class)
public class UserGroupLinkModel {
  // @EmbeddedId
  // UserGroupLinkModelId id;

  @Id
  String id;

  @Column(name = "created_at")
  private @CreatedDate LocalDateTime createdAt;

  @Column(name = "access_level")
  private int accessLevel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserModel user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id", nullable = false)
  private StudyGroupModel group;

  public String getUserId() {
    return this.user.getId();
  }

  public void setUserId(String userId) {
    this.user.setId(userId);
  }

  public String getGroupId() {
    return this.group.getId();
  }

  public void setGroupId(String groupId) {
    this.group.setId(groupId);
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
