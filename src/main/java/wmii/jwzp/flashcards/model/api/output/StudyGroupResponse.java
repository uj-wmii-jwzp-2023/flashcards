package wmii.jwzp.flashcards.model.api.output;

import wmii.jwzp.flashcards.model.db.StudyGroupModel;

public class StudyGroupResponse {
  public String id;
  public String name;

  public StudyGroupResponse(StudyGroupModel group) {
    this.id = group.getId();
    this.name = group.getName();
  }

}
