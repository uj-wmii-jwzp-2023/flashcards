package wmii.jwzp.flashcards.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.api.input.StudyGroupCreationInput;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.StudyGroupRepository;
import wmii.jwzp.flashcards.repository.UserGroupLinkRepository;
import wmii.jwzp.flashcards.utils.AccessLevels;

@Service
public class StudyGroupService {

  @Autowired
  private StudyGroupRepository groupRepository;

  @Autowired
  private UserGroupLinkRepository userGroupLinkRepository;

  public List<StudyGroupModel> getAllGroups() {
    return groupRepository.findAll();
  }

  public StudyGroupModel createGroup(StudyGroupCreationInput input, UserModel user) {
    var studyGroup = new StudyGroupModel();
    String id = UUID.randomUUID().toString();
    studyGroup.setId(id);
    studyGroup.setCreatedAt();
    studyGroup.setName(input.getName());
    groupRepository.save(studyGroup);

    var userGroupLink = new UserGroupLinkModel();
    userGroupLink.setUserId(user.getId());
    userGroupLink.setGroupId(studyGroup.getId());
    userGroupLink.setCreatedAt();
    userGroupLink.setAccessLevel(AccessLevels.ADMIN);
    userGroupLinkRepository.save(userGroupLink);

    return studyGroup;
  }

}