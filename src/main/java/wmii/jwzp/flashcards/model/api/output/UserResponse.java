package wmii.jwzp.flashcards.model.api.output;

import wmii.jwzp.flashcards.model.db.UserModel;

public class UserResponse {
  private String id;
  private String nick;

  public UserResponse(UserModel userModel) {
    this.id = userModel.getId();
    this.nick = userModel.getNick();
  }

  public UserResponse(String id, String nick) {
    this.id = id;
    this.nick = nick;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNick() {
    return nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

}
