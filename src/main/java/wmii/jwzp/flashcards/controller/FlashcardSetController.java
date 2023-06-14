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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wmii.jwzp.flashcards.model.api.input.AnswerInput;
import wmii.jwzp.flashcards.model.api.input.CardInput;
import wmii.jwzp.flashcards.model.api.input.SetCreationInput;
import wmii.jwzp.flashcards.model.api.input.SetUpdateInput;
import wmii.jwzp.flashcards.model.api.output.CardResponse;
import wmii.jwzp.flashcards.model.api.output.FlashcardSetResponse;
import wmii.jwzp.flashcards.model.api.output.UserAchievementResponse;
import wmii.jwzp.flashcards.service.AchievementService;
import wmii.jwzp.flashcards.service.AnswerEntryService;
import wmii.jwzp.flashcards.service.CardService;
import wmii.jwzp.flashcards.service.FlashcardSetService;
import wmii.jwzp.flashcards.service.StudyGroupService;
import wmii.jwzp.flashcards.service.UserGroupLinkService;
import wmii.jwzp.flashcards.service.UserService;
import wmii.jwzp.flashcards.utils.AccessLevels;

@RestController
@RequestMapping("/flashcard_sets")
public class FlashcardSetController {

  Logger logger = LoggerFactory.getLogger(FlashcardSetController.class);

  @Autowired()
  private StudyGroupService groupService;

  @Autowired()
  private UserService userService;

  @Autowired()
  private UserGroupLinkService userGroupLinkService;

  @Autowired()
  private FlashcardSetService setService;

  @Autowired()
  private CardService cardService;

  @Autowired()
  private AnswerEntryService answerService;

  @Autowired()
  private AchievementService achievementsService;

  @GetMapping()
  public ResponseEntity<List<FlashcardSetResponse>> getSets() {
    var sets = setService.findSets();
    var response = sets.stream().map(e -> new FlashcardSetResponse(e)).collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @GetMapping()
  public ResponseEntity<List<FlashcardSetResponse>> getSets(@CookieValue("sid") String authToken) {
    var user = userService.getUserBySessionToken(authToken);
    var sets = setService.findSets();
    var userSets = setService.findSets(user);
    sets.addAll(userSets);
    var response = sets.stream().map(e -> new FlashcardSetResponse(e)).collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @PostMapping()
  public ResponseEntity<FlashcardSetResponse> createSet(@CookieValue("sid") String authToken,
      @RequestBody SetCreationInput setInput) {
    var user = userService.getUserBySessionToken(authToken);
    if (setInput.group_id != null) {
      var group = groupService.getGroupById(setInput.group_id);
      userGroupLinkService.verifyUserAction(user, group, AccessLevels.GUEST);
    }
    var newSet = setService.createSet(setInput, user);

    var response = new FlashcardSetResponse(newSet);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{set_id}")
  public ResponseEntity<FlashcardSetResponse> getSet(@CookieValue(name = "sid", required = false) String authToken,
      @PathVariable("set_id") String setId) {
    var user = authToken != null ? userService.getUserBySessionToken(authToken) : null;
    var flashcardSet = setService.getSet(setId);

    setService.verifyUserAction(user, flashcardSet, AccessLevels.GUEST);

    var response = new FlashcardSetResponse(flashcardSet);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{set_id}")
  public ResponseEntity<FlashcardSetResponse> updateSet(@CookieValue("sid") String authToken,
      @PathVariable("set_id") String setId,
      @RequestBody SetUpdateInput input) {
    var user = userService.getUserBySessionToken(authToken);
    var flashcardSet = setService.getSet(setId);

    setService.verifyUserAction(user, flashcardSet, AccessLevels.ADMIN);

    flashcardSet = setService.updateSet(flashcardSet, input);

    var response = new FlashcardSetResponse(flashcardSet);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{set_id}")
  public ResponseEntity<FlashcardSetResponse> deleteSet(@CookieValue("sid") String authToken,
      @PathVariable("set_id") String setId) {
    var user = userService.getUserBySessionToken(authToken);
    var flashcardSet = setService.getSet(setId);
    setService.verifyUserAction(user, flashcardSet, AccessLevels.ADMIN);

    flashcardSet = setService.removeSet(flashcardSet);

    var response = new FlashcardSetResponse(flashcardSet);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{set_id}/cards")
  public ResponseEntity<List<CardResponse>> getCards(@CookieValue(name = "sid", required = false) String authToken,
      @PathVariable("set_id") String setId, @RequestParam(name = "start_entry", required = false) String startEntry) {
    var user = authToken != null ? userService.getUserBySessionToken(authToken) : null;
    var flashcardSet = setService.getSet(setId);
    setService.verifyUserAction(user, flashcardSet, AccessLevels.GUEST);

    var cards = cardService.getCards(flashcardSet);

    if ("true".equals(startEntry)) {
      answerService.startEntry(user, flashcardSet);
    }

    var response = cards.stream().map(e -> new CardResponse(e)).collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{set_id}/cards")
  public ResponseEntity<CardResponse> addCard(@CookieValue("sid") String authToken,
      @PathVariable("set_id") String setId, @RequestBody CardInput cardInput) {
    var user = userService.getUserBySessionToken(authToken);
    var flashcardSet = setService.getSet(setId);
    setService.verifyUserAction(user, flashcardSet, AccessLevels.ADMIN);

    var card = cardService.createCard(cardInput, flashcardSet);

    var response = new CardResponse(card);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{set_id}/cards/{card_id}")
  public ResponseEntity<CardResponse> updateCard(@CookieValue("sid") String authToken,
      @PathVariable("card_id") String cardId, @RequestBody CardInput cardInput) {
    var user = userService.getUserBySessionToken(authToken);
    var flashcardSet = setService.getSet(cardId);
    setService.verifyUserAction(user, flashcardSet, AccessLevels.ADMIN);

    var card = cardService.getCardById(cardId);
    card = cardService.updateCard(card, cardInput);

    var response = new CardResponse(card);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{set_id}/cards/{card_id}")
  public ResponseEntity<CardResponse> deleteCard(@CookieValue("sid") String authToken,
      @PathVariable("card_id") String cardId) {
    var user = userService.getUserBySessionToken(authToken);
    var flashcardSet = setService.getSet(cardId);
    setService.verifyUserAction(user, flashcardSet, AccessLevels.ADMIN);

    var card = cardService.getCardById(cardId);
    card = cardService.removeCard(card);

    var response = new CardResponse(card);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{set_id}/answers")
  public ResponseEntity<List<UserAchievementResponse>> submitAnswers(@CookieValue("sid") String authToken,
      @PathVariable("set_id") String setId, @RequestBody AnswerInput input) {
    var user = userService.getUserBySessionToken(authToken);
    var flashcardSet = setService.getSet(setId);
    setService.verifyUserAction(user, flashcardSet, AccessLevels.GUEST);

    var answerEntry = answerService.checkAnswer(user, flashcardSet, input.cards);
    var achievements = achievementsService.grantAchievements(answerEntry);

    var response = achievements.stream().map(e -> new UserAchievementResponse(e)).collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

}
