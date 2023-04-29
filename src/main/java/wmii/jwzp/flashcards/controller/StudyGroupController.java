package wmii.jwzp.flashcards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wmii.jwzp.flashcards.model.api.input.StudyGroupCreationInput;
import wmii.jwzp.flashcards.model.api.output.StudyGroupResponse;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.service.StudyGroupService;
import wmii.jwzp.flashcards.service.UserService;

@RestController
@RequestMapping("/study_groups")
public class StudyGroupController {

  @Autowired()
  private StudyGroupService groupService;

  @Autowired()
  private UserService userService;

  // TODO: this is here for debugging, remove it later
  @GetMapping("/admin")
  public ResponseEntity<List<StudyGroupModel>> getAllGroups() {
    var groups = groupService.getAllGroups();
    return ResponseEntity.ok(groups);
  }

  @PostMapping()
  public ResponseEntity<StudyGroupResponse> createGroup(@RequestBody StudyGroupCreationInput groupInput,
      @CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var studyGroup = groupService.createGroup(groupInput, user);
    var response = new StudyGroupResponse(studyGroup);
    return ResponseEntity.ok(response);
  }

}
