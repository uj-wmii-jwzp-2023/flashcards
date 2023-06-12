package wmii.jwzp.flashcards.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import wmii.jwzp.flashcards.model.internal.GroupAchievementModelId;

@Entity
@IdClass(GroupAchievementModelId.class)
@Table(name = "group_achievements")
public class GroupAchievementModel {

  @Id
  @Column(name = "name")
  private String name;

  @Id
  @Column(name = "group_id")
  private String group_id;

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
}
