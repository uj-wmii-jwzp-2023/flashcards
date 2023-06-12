package wmii.jwzp.flashcards.model.achievements;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import wmii.jwzp.flashcards.utils.errors.BadRequest;

@Component
@Scope("singleton")
public class AchievementFactory {

  public boolean verifyAchievement(String name) {
    switch (name) {
      case "oneset": {
        return true;
      }
      default: {
        return false;
      }
    }
  }

  public IAchievement getAchievementClass(String name) {
    switch (name) {
      case "oneset": {
        return new OneSetAchievement();
      }
      default: {
        throw new BadRequest("Achievement does not exist.");
      }
    }
  }
}
