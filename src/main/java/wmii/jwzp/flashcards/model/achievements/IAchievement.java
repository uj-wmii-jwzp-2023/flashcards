package wmii.jwzp.flashcards.model.achievements;

import wmii.jwzp.flashcards.model.db.AnswerEntryModel;

public interface IAchievement {
  public String getName();

  public Boolean isEligible(AnswerEntryModel entry);
}
