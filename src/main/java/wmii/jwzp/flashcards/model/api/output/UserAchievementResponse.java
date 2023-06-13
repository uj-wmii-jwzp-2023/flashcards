package wmii.jwzp.flashcards.model.api.output;

import java.time.LocalDateTime;

import wmii.jwzp.flashcards.model.db.UserAchievementModel;

public class UserAchievementResponse {
  public String name;
  public LocalDateTime created_at;
  public String group_id;

  public UserAchievementResponse(UserAchievementModel achievement) {
    this.name = achievement.getName();
    this.created_at = achievement.getCreatedAt();
    this.group_id = achievement.getGroupId();
  }
}
