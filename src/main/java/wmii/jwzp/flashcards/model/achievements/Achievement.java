package wmii.jwzp.flashcards.model.achievements;

import wmii.jwzp.flashcards.model.db.AnswerEntryModel;
import wmii.jwzp.flashcards.model.db.UserAchievementModel;

public abstract class Achievement {

  abstract public String getName();

  abstract public Boolean isEligible(AnswerEntryModel entry);

  public UserAchievementModel create(String userId, String groupId) {
    var achievement = new UserAchievementModel();
    achievement.setName(this.getName());
    achievement.setUserId(userId);
    achievement.setGroupId(groupId);
    return achievement;
  }
}
