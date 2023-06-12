package wmii.jwzp.flashcards.model.internal;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public class UserAchievementModelId implements Serializable {
  @Id
  public String name;

  @Id
  public String group_id;

  @Id
  public String user_id;

  @Override
  public int hashCode() {
    return (this.name + this.group_id + this.user_id).hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserAchievementModelId that = (UserAchievementModelId) o;
    return this.name.equals(that.name) && this.group_id.equals(that.group_id) && this.user_id.equals(that.user_id);
  }

}