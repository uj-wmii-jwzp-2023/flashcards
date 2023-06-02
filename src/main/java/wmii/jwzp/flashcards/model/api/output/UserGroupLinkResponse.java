package wmii.jwzp.flashcards.model.api.output;

import java.time.LocalDateTime;

import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;

public class UserGroupLinkResponse {

  public String user_id;

  public String group_id;

  public LocalDateTime created_at;

  public int access_level;

  public UserGroupLinkResponse(UserGroupLinkModel link) {
    this.user_id = link.getUserId();
    this.group_id = link.getGroupId();
    this.created_at = link.getCreatedAt();
    this.access_level = link.getAccessLevel();
  }

}
