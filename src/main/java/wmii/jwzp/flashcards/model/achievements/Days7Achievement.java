package wmii.jwzp.flashcards.model.achievements;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import wmii.jwzp.flashcards.model.db.AnswerEntryModel;
import wmii.jwzp.flashcards.repository.AnswerEntryRepository;
import wmii.jwzp.flashcards.repository.UserAchievementRepository;

@Component
@Scope("singleton")
public class Days7Achievement extends Achievement {

  @Autowired
  private UserAchievementRepository userAchievementRepository;

  @Autowired
  private AnswerEntryRepository answerRepository;

  public String getName() {
    return "days7";
  }

  public Boolean isEligible(AnswerEntryModel entry) {
    var achievements = userAchievementRepository.findAllByUserId(entry.getUserId());
    if (achievements.stream().filter(e -> this.getName().equals(e.getName())).collect(Collectors.toList())
        .size() != 0) {
      return false;
    }

    var answers = answerRepository.findAllForUserByGroup(entry.getUserId(), entry.getSet().getGroupId());

    var today = LocalDate.now().atStartOfDay();
    // we know that today has an answer, so just check previous 6 days
    for (int i = 1; i <= 6; i++) {
      var startOfDay = today.minusDays(i);
      var endOfDay = startOfDay.plusDays(1);
      var matchedEntry = answers.stream()
          .filter(
              e -> e.getEndedAt() != null && e.getEndedAt().isAfter(startOfDay) && e.getEndedAt().isBefore(endOfDay))
          .findAny();
      if (!matchedEntry.isPresent()) {
        return false;
      }
    }

    return true;
  }

}
