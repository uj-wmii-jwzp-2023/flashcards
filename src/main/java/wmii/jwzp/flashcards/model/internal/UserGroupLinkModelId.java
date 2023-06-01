package wmii.jwzp.flashcards.model.internal;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public class UserGroupLinkModelId implements Serializable {
  @Id
  public String user_id;

  @Id
  public String group_id;

  @Override
  public int hashCode() {
    return (this.user_id + this.group_id).hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserGroupLinkModelId that = (UserGroupLinkModelId) o;
    return this.user_id.equals(that.user_id) && this.group_id.equals(that.group_id);
  }

}