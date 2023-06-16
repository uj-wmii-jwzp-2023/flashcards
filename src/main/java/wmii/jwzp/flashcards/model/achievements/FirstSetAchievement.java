package wmii.jwzp.flashcards.model.achievements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import wmii.jwzp.flashcards.model.db.AnswerEntryModel;
import wmii.jwzp.flashcards.repository.AnswerEntryRepository;
import wmii.jwzp.flashcards.repository.UserAchievementRepository;

@Component
@Scope("singleton")
public class FirstSetAchievement extends Achievement {

  Logger logger = LoggerFactory.getLogger(Achievement.class);

  @Autowired
  private UserAchievementRepository userAchievementRepository;

  @Autowired
  private AnswerEntryRepository answerRepository;

  public String getName() {
    return "firstset";
  }

  public Boolean isEligible(AnswerEntryModel entry) {
    var achievements = userAchievementRepository.findByUserIdAndName(entry.getUserId(), this.getName());
    if (!achievements.isEmpty()) {
      return false;
    }

    var answers = answerRepository.findAllForUserByGroup(entry.getUserId(), entry.getSet().getGroupId());

    if (answers.size() < 1) {
      return false;
    }
    return true;
  }

}
