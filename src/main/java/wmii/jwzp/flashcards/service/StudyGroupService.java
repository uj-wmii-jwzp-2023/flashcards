package wmii.jwzp.flashcards.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.api.input.StudyGroupCreationInput;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.StudyGroupRepository;
import wmii.jwzp.flashcards.repository.UserGroupLinkRepository;
import wmii.jwzp.flashcards.repository.UserRepository;
import wmii.jwzp.flashcards.utils.AccessLevels;
import wmii.jwzp.flashcards.utils.errors.BadRequest;
import wmii.jwzp.flashcards.utils.errors.NotFound;

@Service
public class StudyGroupService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private StudyGroupRepository groupRepository;

  @Autowired
  private UserGroupLinkRepository userGroupLinkRepository;

  public List<StudyGroupModel> getAllGroups() {
    return groupRepository.findAll();
  }

  public StudyGroupModel getGroupById(String id) {
    var studyGroup = this.groupRepository.findById(id);
    if (studyGroup.isPresent()) {
      return studyGroup.get();
    } else {
      throw new NotFound("Study group not found");
    }
  }

  public StudyGroupModel createGroup(StudyGroupCreationInput input) {
    var studyGroup = new StudyGroupModel();
    String id = UUID.randomUUID().toString();
    studyGroup.setId(id);
    studyGroup.setCreatedAt();
    studyGroup.setName(input.getName());
    groupRepository.save(studyGroup);

    return studyGroup;
  }

  public void joinGroup(StudyGroupModel group, UserModel user, int accessLevel) {
    var userGroupLink = new UserGroupLinkModel();
    userGroupLink.setUserId(user.getId());
    userGroupLink.setGroupId(group.getId());
    userGroupLink.setCreatedAt();
    userGroupLink.setAccessLevel(accessLevel);
    userGroupLinkRepository.save(userGroupLink);
  }

  public List<StudyGroupModel> getGroupsByUser(UserModel user) {
    var groups = this.groupRepository.findAllByUser(user.getId());
    return groups;
  }

  public List<UserModel> getUsersInGroup(StudyGroupModel group) {
    var users = userRepository.findAllByGroup(group.getId());
    return users;
  }

}