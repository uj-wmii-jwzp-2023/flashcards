package wmii.jwzp.flashcards.model.api.output;

import wmii.jwzp.flashcards.model.db.UserModel;

public class UserResponse {
  public String id;
  public String nick;

  public UserResponse(UserModel userModel) {
    this.id = userModel.getId();
    this.nick = userModel.getNick();
  }

  public UserResponse(String id, String nick) {
    this.id = id;
    this.nick = nick;
  }

}
