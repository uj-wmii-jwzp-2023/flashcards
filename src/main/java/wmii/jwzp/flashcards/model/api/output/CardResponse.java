package wmii.jwzp.flashcards.model.api.output;

import wmii.jwzp.flashcards.model.db.CardModel;

public class CardResponse {
  public String id;
  public String question;
  public String answer;

  public CardResponse(CardModel cardModel) {
    this.id = cardModel.getId();
    this.question = cardModel.getQuestion();
    this.answer = cardModel.getAnswer();
  }
}
