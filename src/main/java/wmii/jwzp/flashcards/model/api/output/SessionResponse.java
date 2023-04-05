package wmii.jwzp.flashcards.model.api.output;

import wmii.jwzp.flashcards.model.db.SessionModel;

public class SessionResponse {

  private String id;
  private String userId;
  private String expiresAt;

  public SessionResponse(SessionModel sessionModel) {
    this.id = sessionModel.getId();
    this.userId = sessionModel.getUserId();
    this.expiresAt = sessionModel.getExpiresAt().toString();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(String expiresAt) {
    this.expiresAt = expiresAt;
  }

}
