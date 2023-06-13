package wmii.jwzp.flashcards.model.achievements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import wmii.jwzp.flashcards.utils.errors.BadRequest;

@Component
@Scope("singleton")
public class AchievementFactory {

  @Autowired
  private OneSetAchievement oneSetAchievement;

  public Boolean verifyAchievement(String name) {
    switch (name) {
      case "oneset": {
        return true;
      }
      default: {
        return false;
      }
    }
  }

  public Achievement getAchievementClass(String name) {
    switch (name) {
      case "oneset": {
        return oneSetAchievement;
      }
      default: {
        throw new BadRequest("Achievement does not exist.");
      }
    }
  }
}
