package wmii.jwzp.flashcards.model.achievements;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import wmii.jwzp.flashcards.model.db.AnswerEntryModel;
import wmii.jwzp.flashcards.repository.AnswerEntryRepository;
import wmii.jwzp.flashcards.repository.UserAchievementRepository;

@Component
@Scope("singleton")
public class OneSetAchievement extends Achievement {

  @Autowired
  private UserAchievementRepository userAchievementRepository;

  @Autowired
  private AnswerEntryRepository answerRepository;

  public String getName() {
    return "firstset";
  }

  public Boolean isEligible(AnswerEntryModel entry) {
    var achievements = userAchievementRepository.findAllByUserId(entry.getUserId());
    if (achievements.stream().filter(e -> this.getName().equals(e.getName())).collect(Collectors.toList())
        .size() != 0) {
      return false;
    }
    var answers = answerRepository.findAllForUserByGroup(entry.getUserId(), entry.getSet().getId());
    if (answers.size() < 1) {
      return false;
    }
    return true;
  }

}
