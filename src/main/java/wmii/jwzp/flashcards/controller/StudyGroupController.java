package wmii.jwzp.flashcards.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wmii.jwzp.flashcards.model.api.input.LinkUpdateInput;
import wmii.jwzp.flashcards.model.api.input.StudyGroupInput;
import wmii.jwzp.flashcards.model.api.output.FlashcardSetResponse;
import wmii.jwzp.flashcards.model.api.output.GroupAchievementResponse;
import wmii.jwzp.flashcards.model.api.output.StudyGroupResponse;
import wmii.jwzp.flashcards.model.api.output.UserGroupLinkResponse;
import wmii.jwzp.flashcards.model.api.output.UserResponse;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.service.AchievementService;
import wmii.jwzp.flashcards.service.FlashcardSetService;
import wmii.jwzp.flashcards.service.StudyGroupService;
import wmii.jwzp.flashcards.service.UserGroupLinkService;
import wmii.jwzp.flashcards.service.UserService;
import wmii.jwzp.flashcards.utils.AccessLevels;

@RestController
@RequestMapping("/study_groups")
public class StudyGroupController {

  Logger logger = LoggerFactory.getLogger(StudyGroupController.class);

  @Autowired()
  private StudyGroupService groupService;

  @Autowired()
  private UserService userService;

  @Autowired()
  private UserGroupLinkService userGroupLinkService;

  @Autowired()
  private FlashcardSetService setService;

  @Autowired()
  private AchievementService achievementService;

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
  public ResponseEntity<StudyGroupResponse> createGroup(@RequestBody StudyGroupInput groupInput,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var studyGroup = groupService.createGroup(groupInput);

    userGroupLinkService.joinGroup(studyGroup, user, AccessLevels.ADMIN);

    var response = new StudyGroupResponse(studyGroup);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{group_id}")
  public ResponseEntity<StudyGroupResponse> getGroup(@PathVariable("group_id") String groupId,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);
    userGroupLinkService.verifyUserAction(user, group, AccessLevels.GUEST);

    var response = new StudyGroupResponse(group);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{group_id}")
  public ResponseEntity<StudyGroupResponse> updateGroup(@PathVariable("group_id") String groupId,
      @CookieValue("sid") String authToken, @RequestBody StudyGroupInput groupInput) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);
    userGroupLinkService.verifyUserAction(user, group, AccessLevels.GUEST);

    group = groupService.updateGroup(group, groupInput);

    var response = new StudyGroupResponse(group);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{group_id}")
  public ResponseEntity<StudyGroupResponse> deleteGroup(@PathVariable("group_id") String groupId,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);
    userGroupLinkService.verifyUserAction(user, group, AccessLevels.ADMIN);

    group = groupService.deleteGroup(group);

    var response = new StudyGroupResponse(group);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{group_id}/join")
  public ResponseEntity<UserGroupLinkResponse> joinGroup(@PathVariable("group_id") String groupId,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);

    var link = userGroupLinkService.joinGroup(group, user, AccessLevels.GUEST);

    var response = new UserGroupLinkResponse(link);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{group_id}/users")
  public ResponseEntity<List<UserResponse>> getUsersInGroup(@PathVariable("group_id") String groupId,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);
    userGroupLinkService.verifyUserAction(user, group, AccessLevels.GUEST);

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
    userGroupLinkService.verifyUserAction(user, group, AccessLevels.ADMIN);

    var updateUser = userService.getUserById(userId);
    var link = userGroupLinkService.updateAccessLevel(group, updateUser, linkInput.getAccessLevel());

    var response = new UserGroupLinkResponse(link);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{group_id}/users/{user_id}")
  public ResponseEntity<UserGroupLinkResponse> removeUser(@PathVariable("group_id") String groupId,
      @PathVariable("user_id") String userId, @CookieValue("sid") String authToken,
      @RequestBody LinkUpdateInput linkInput) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById("group_id");
    userGroupLinkService.verifyUserAction(user, group, AccessLevels.ADMIN);

    var link = userGroupLinkService.leaveGroup(group, user);

    var response = new UserGroupLinkResponse(link);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{group_id}/flashcard_sets")
  public ResponseEntity<List<FlashcardSetResponse>> getSets(@PathVariable("group_id") String groupId,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);
    userGroupLinkService.verifyUserAction(user, group, AccessLevels.GUEST);

    var flashcardSets = setService.findSets(group);

    var response = flashcardSets.stream().map(e -> new FlashcardSetResponse(e)).collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{group_id}/achievements")
  public ResponseEntity<GroupAchievementResponse> getAvailableAchievements(@PathVariable("group_id") String groupId,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);
    userGroupLinkService.verifyUserAction(user, group, AccessLevels.GUEST);

    var achievements = achievementService.getAchievements(group);

    var response = new GroupAchievementResponse(achievements);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{group_id}/achievements/{achievement_name}")
  public ResponseEntity<GroupAchievementResponse> enableAchievement(@PathVariable("group_id") String groupId,
      @PathVariable("achievement_name") String achievementName,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);
    userGroupLinkService.verifyUserAction(user, group, AccessLevels.ADMIN);

    achievementService.enableAchievement(group, achievementName);

    var achievements = achievementService.getAchievements(group);
    var response = new GroupAchievementResponse(achievements);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{group_id}/achievements/{achievement_name}")
  public ResponseEntity<GroupAchievementResponse> disableAchievement(@PathVariable("group_id") String groupId,
      @PathVariable("achievement_name") String achievementName,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var group = groupService.getGroupById(groupId);
    userGroupLinkService.verifyUserAction(user, group, AccessLevels.ADMIN);

    achievementService.disableAchievement(group, achievementName);

    var achievements = achievementService.getAchievements(group);
    var response = new GroupAchievementResponse(achievements);
    return ResponseEntity.ok(response);
  }

}
