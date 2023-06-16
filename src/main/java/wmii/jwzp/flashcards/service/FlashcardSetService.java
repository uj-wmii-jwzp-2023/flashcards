package wmii.jwzp.flashcards.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.api.input.CardInput;
import wmii.jwzp.flashcards.model.api.input.SetCreationInput;
import wmii.jwzp.flashcards.model.api.input.SetUpdateInput;
import wmii.jwzp.flashcards.model.db.FlashcardSetModel;
import wmii.jwzp.flashcards.model.db.StudyGroupModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.FlashcardSetRepository;
import wmii.jwzp.flashcards.utils.errors.NotFound;
import wmii.jwzp.flashcards.utils.errors.Unauthorized;

@Service
public class FlashcardSetService {

  Logger logger = LoggerFactory.getLogger(FlashcardSetService.class);

  @Autowired
  private FlashcardSetRepository setRepository;

  @Autowired
  private CardService cardService;

  @Autowired
  private StudyGroupService groupService;

  @Autowired
  private UserGroupLinkService userGroupLinkService;

  public void verifyUserAction(UserModel user, FlashcardSetModel flashcardSet, int requiredAccess) {
    if (flashcardSet.isPublic()) {
      return;
    }
    if (flashcardSet.getGroupId() != null) {
      var group = groupService.getGroupById(flashcardSet.getGroupId());
      userGroupLinkService.verifyUserAction(user, group, requiredAccess);
    } else {
      if (user == null || !user.getId().equals(flashcardSet.getUserId())) {
        throw new Unauthorized("User is not allowed to perform this action");
      }
    }
  }

  public void verifyUserAction(UserModel user, FlashcardSetModel flashcardSet, int requiredAccess, String authHeader) {
    logger.info("authHeader:" + authHeader + ".");
    if (flashcardSet.getAuthToken().equals(authHeader)) {
      return;
    } else {
      this.verifyUserAction(user, flashcardSet, requiredAccess);
    }
  }

  public FlashcardSetModel createSet(SetCreationInput input, UserModel user) {
    var newSet = new FlashcardSetModel();
    String id = UUID.randomUUID().toString();
    newSet.setId(id);
    newSet.setCreatedAt();
    if (input.group_id == null) {
      newSet.setUserId(user.getId());
    } else {
      newSet.setGroupId(input.group_id);
    }
    newSet.setName(input.name);
    newSet.setIsPublic(input.is_public != null ? input.is_public : false);
    newSet.setAuthToken(UUID.randomUUID().toString());

    setRepository.save(newSet);

    if (input.cards != null) {
      input.cards.forEach(card -> cardService.createCard(card, newSet));
    }

    return newSet;
  }

  public FlashcardSetModel getSet(String setId) {
    var flashcardSet = setRepository.findById(setId);
    if (!flashcardSet.isPresent()) {
      throw new NotFound("Flashcard set not found");
    } else {
      return flashcardSet.get();
    }
  }

  public FlashcardSetModel updateSet(FlashcardSetModel flashcardSet, SetUpdateInput input) {
    if (input.is_public != null) {
      flashcardSet.setIsPublic(input.is_public);
    }
    if (input.name != null) {
      flashcardSet.setName(input.name);
    }
    setRepository.save(flashcardSet);
    return flashcardSet;
  }

  public FlashcardSetModel removeSet(FlashcardSetModel flashcardSet) {
    setRepository.delete(flashcardSet);
    return flashcardSet;
  }

  public FlashcardSetModel cloneSet(FlashcardSetModel flashcardSet, UserModel user) {
    var newSet = new FlashcardSetModel();
    String id = UUID.randomUUID().toString();
    newSet.setId(id);
    newSet.setCreatedAt();
    newSet.setUserId(user.getId());
    newSet.setName(flashcardSet.getName());
    newSet.setIsPublic(flashcardSet.isPublic());

    setRepository.save(newSet);

    if (flashcardSet.getCards() != null && !flashcardSet.getCards().isEmpty()) {

      flashcardSet.getCards().forEach(card -> {
        var cardInput = new CardInput();
        cardInput.question = card.getQuestion();
        cardInput.answer = card.getAnswer();
        cardService.createCard(cardInput, newSet);
      });
    }

    setRepository.save(flashcardSet);
    return flashcardSet;
  }

  /*
   * Find all public sets
   */
  public List<FlashcardSetModel> findSets() {
    var res = setRepository.findAllPublic();
    return res;
  }

  public List<FlashcardSetModel> findSets(UserModel user) {
    var res = setRepository.findAllByUserId(user.getId());
    return res;
  }

  public List<FlashcardSetModel> findSets(StudyGroupModel group) {
    var res = setRepository.findAllByGroupId(group.getId());
    return res;
  }
}