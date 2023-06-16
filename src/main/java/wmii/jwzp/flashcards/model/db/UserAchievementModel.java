package wmii.jwzp.flashcards.model.db;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import wmii.jwzp.flashcards.model.internal.UserAchievementModelId;

@Entity
@IdClass(UserAchievementModelId.class)
@Table(name = "user_achievements")
public class UserAchievementModel {

  @Id()
  @Column(name = "name")
  private String name;

  @Id()
  @Column(name = "group_id")
  private String group_id;

  @Id()
  @Column(name = "user_id")
  private String user_id;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt() {
    this.createdAt = LocalDateTime.now();
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroupId() {
    return this.group_id;
  }

  public void setGroupId(String groupId) {
    this.group_id = groupId;
  }

  public String getUserId() {
    return this.user_id;
  }

  public void setUserId(String userId) {
    this.user_id = userId;
  }
}
