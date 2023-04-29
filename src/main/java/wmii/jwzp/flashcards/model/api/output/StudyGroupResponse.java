package wmii.jwzp.flashcards.model.api.output;

import jakarta.persistence.OneToMany;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;

public class StudyGroupResponse {
  private String id;
  private String name;

  @OneToMany(mappedBy = "user")
  private UserGroupLinkModel userGroupLink;

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
