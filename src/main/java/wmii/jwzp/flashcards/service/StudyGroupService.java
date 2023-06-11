package wmii.jwzp.flashcards.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.api.input.StudyGroupInput;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.model.db.UserGroupLinkModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.StudyGroupRepository;
import wmii.jwzp.flashcards.repository.UserGroupLinkRepository;
import wmii.jwzp.flashcards.repository.UserRepository;
import wmii.jwzp.flashcards.utils.errors.NotFound;

@Service
public class StudyGroupService {

  Logger logger = LoggerFactory.getLogger(StudyGroupService.class);

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
    if (!studyGroup.isPresent()) {
      throw new NotFound("Study group not found");
    }
    return studyGroup.get();
  }

  public StudyGroupModel createGroup(StudyGroupInput input) {
    var studyGroup = new StudyGroupModel();
    String id = UUID.randomUUID().toString();
    studyGroup.setId(id);
    studyGroup.setCreatedAt();
    studyGroup.setName(input.getName());
    groupRepository.save(studyGroup);

    return studyGroup;
  }

  public StudyGroupModel updateGroup(StudyGroupModel group, StudyGroupInput input) {
    if (input.getName() != null) {
      group.setName(input.getName());
    }
    groupRepository.save(group);
    return group;
  }

  public StudyGroupModel deleteGroup(StudyGroupModel group) {
    groupRepository.delete(group);
    return group;
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