package wmii.jwzp.flashcards.model.db;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import wmii.jwzp.flashcards.model.internal.UserGroupLinkModelId;

@Entity
@IdClass(UserGroupLinkModelId.class)
public class UserGroupLinkModel {
  @Id
  private String user_id;

  @Id
  private String group_id;

  @Column(name = "created_at")
  private @CreatedDate LocalDateTime createdAt;

  @Column(name = "access_level")
  private int accessLevel;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("user_id")
  private UserModel user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("group_id")
  private StudyGroupModel group;

  public String getUserId() {
    return user_id;
  }

  public void setUserId(String userId) {
    this.user_id = userId;
  }

  public String getGroupId() {
    return group_id;
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

  public UserModel getUser() {
    return user;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }

}
