package wmii.jwzp.flashcards.model.internal;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public class GroupAchievementModelId implements Serializable {
  @Id
  public String name;

  @Id
  public String group_id;

  @Override
  public int hashCode() {
    return (this.name + this.group_id).hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupAchievementModelId that = (GroupAchievementModelId) o;
    return this.name.equals(that.group_id) && this.group_id.equals(that.group_id);
  }

}