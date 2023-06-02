package wmii.jwzp.flashcards.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wmii.jwzp.flashcards.model.api.input.LinkUpdateInput;
import wmii.jwzp.flashcards.model.api.input.StudyGroupCreationInput;
import wmii.jwzp.flashcards.model.api.output.StudyGroupResponse;
import wmii.jwzp.flashcards.model.api.output.UserGroupLinkResponse;
import wmii.jwzp.flashcards.model.api.output.UserResponse;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.service.StudyGroupService;
import wmii.jwzp.flashcards.service.UserGroupLinkService;
import wmii.jwzp.flashcards.service.UserService;
import wmii.jwzp.flashcards.utils.AccessLevels;

@RestController
@RequestMapping("/study_groups")
public class StudyGroupController {

  @Autowired()
  private StudyGroupService groupService;

  @Autowired()
  private UserService userService;

  @Autowired()
  private UserGroupLinkService userGroupLinkService;

  // TODO: this is here for debugging, remove it later
  @GetMapping("/admin")
  public ResponseEntity<List<StudyGroupModel>> getAllGroups(@CookieValue("sid") String authToken) {
    var groups = groupService.getAllGroups();
    return ResponseEntity.ok(groups);
  }

  @GetMapping()
  public ResponseEntity<List<StudyGroupResponse>> getGroups(@CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var groups = groupService.getGroupsByUser(user);
    var response = groups.stream().map(e -> new StudyGroupResponse(e)).collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @PostMapping()
  public ResponseEntity<StudyGroupResponse> createGroup(@RequestBody StudyGroupCreationInput groupInput,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var studyGroup = groupService.createGroup(groupInput);
    groupService.joinGroup(studyGroup, user, AccessLevels.ADMIN);
    var response = new StudyGroupResponse(studyGroup);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{group_id}/join")
  public ResponseEntity<StudyGroupResponse> joinGroup(@PathVariable("group_id") String groupId,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);
    groupService.joinGroup(group, user, 0);
    var response = new StudyGroupResponse(group);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{group_id}/users")
  public ResponseEntity<List<UserResponse>> getUsersInGroup(@PathVariable("group_id") String groupId,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);
    userGroupLinkService.verifyUserAction(user, group, 0);

    var usersInGroup = groupService.getUsersInGroup(group);
    var response = usersInGroup.stream().map(e -> new UserResponse(e)).collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{group_id}/users/{user_id}")
  public ResponseEntity<UserGroupLinkResponse> editUserRole(@PathVariable("group_id") String groupId,
      @PathVariable("user_id") String userId, @CookieValue("sid") String authToken,
      @RequestBody LinkUpdateInput linkInput) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById("group_id");
    userGroupLinkService.verifyUserAction(user, group, 2);

    var updateUser = userService.getUserById(userId);
    var link = userGroupLinkService.updateAccessLevel(group, updateUser, linkInput.getAccessLevel());
    var response = new UserGroupLinkResponse(link);
    return ResponseEntity.ok(response);
  }

}
