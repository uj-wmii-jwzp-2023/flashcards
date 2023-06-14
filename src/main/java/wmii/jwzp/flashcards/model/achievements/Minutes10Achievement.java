package wmii.jwzp.flashcards.model.achievements;

import java.time.Duration;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import wmii.jwzp.flashcards.model.db.AnswerEntryModel;
import wmii.jwzp.flashcards.repository.UserAchievementRepository;

@Component
@Scope("singleton")
public class Minutes10Achievement extends Achievement {

  @Autowired
  private UserAchievementRepository userAchievementRepository;

  public String getName() {
    return "minutes10";
  }

  public Boolean isEligible(AnswerEntryModel entry) {
    var achievements = userAchievementRepository.findAllByUserId(entry.getUserId());
    if (achievements.stream().filter(e -> this.getName().equals(e.getName())).collect(Collectors.toList())
        .size() != 0) {
      return false;
    }
    var diff = Duration.between(entry.getStartedAt(), entry.getEndedAt()).toSeconds();
    if (diff > 10 * 60) {
      return false;
    }
    return true;
  }

}
