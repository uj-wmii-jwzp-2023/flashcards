package wmii.jwzp.flashcards.model.api.output;

import wmii.jwzp.flashcards.model.db.StudyGroupModel;

public class StudyGroupResponse {
  private String id;
  private String name;

  public StudyGroupResponse(StudyGroupModel group) {
    this.id = group.getId();
    this.name = group.getName();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
