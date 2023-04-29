package wmii.jwzp.flashcards.model.internal;

import java.io.Serializable;

public class UserGroupLinkModelId implements Serializable {
  public String user_id;

  public String group_id;

  public UserGroupLinkModelId(String user_id, String group_id) {
    this.user_id = user_id;
    this.group_id = group_id;
  }

  // https://stackoverflow.com/questions/52648330/spring-data-jpa-manytomany-relationship-with-extra-column
  // TODO: do we need that?
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