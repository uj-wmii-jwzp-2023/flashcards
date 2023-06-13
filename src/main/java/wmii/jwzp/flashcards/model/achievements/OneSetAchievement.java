package wmii.jwzp.flashcards.model.achievements;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import wmii.jwzp.flashcards.model.db.AnswerEntryModel;

@Component
@Scope("singleton")
public class OneSetAchievement implements IAchievement {
  private String name = "oneset";

  public String getName() {
    return this.name;
  }

  public Boolean isEligible(AnswerEntryModel entry) {
    throw new Error("Not implemented yet");
  }
}
