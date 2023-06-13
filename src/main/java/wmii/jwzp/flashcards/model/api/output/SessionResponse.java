package wmii.jwzp.flashcards.model.api.output;

import wmii.jwzp.flashcards.model.db.SessionModel;

public class SessionResponse {

  public String id;
  public String user_id;
  public String expires_at;

  public SessionResponse(SessionModel sessionModel) {
    this.id = sessionModel.getId();
    this.user_id = sessionModel.getUserId();
    this.expires_at = sessionModel.getExpiresAt().toString();
  }

}
