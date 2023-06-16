package wmii.jwzp.flashcards.model.achievements;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import wmii.jwzp.flashcards.utils.errors.BadRequest;

@Component
@Scope("singleton")
public class AchievementFactory {

  @Autowired
  private FirstSetAchievement oneSetAchievement;

  @Autowired
  private Minutes10Achievement minutes10Achievement;

  @Autowired
  private Days7Achievement days7Achievement;

  private List<String> achievements = List.of("firstset", "minutes10", "days7");

  public List<String> getAllAchievements() {
    return this.achievements;
  }

  public Boolean verifyAchievement(String name) {
    return this.achievements.contains(name);
  }

  public Achievement getAchievementClass(String name) {
    switch (name) {
      case "firstset": {
        return oneSetAchievement;
      }
      case "minutes10": {
        return minutes10Achievement;
      }
      case "days7": {
        return days7Achievement;
      }
      default: {
        throw new BadRequest("Achievement does not exist.");
      }
    }
  }
}
