package wmii.jwzp.flashcards.model.api.output;

import java.time.LocalDateTime;

import wmii.jwzp.flashcards.model.db.AnswerEntryModel;

public class AnswerResponse {
  public String id;
  public String user_id;
  public String set_id;
  public LocalDateTime started_at;
  public LocalDateTime ended_at;

  public AnswerResponse(AnswerEntryModel model) {
    this.id = model.getId();
    this.user_id = model.getUserId();
    this.set_id = model.getSetId();
    this.started_at = model.getStartedAt();
    this.ended_at = model.getEndedAt();
  }

}
