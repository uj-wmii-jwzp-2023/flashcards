package wmii.jwzp.flashcards.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.api.input.AnswerCardInput;
import wmii.jwzp.flashcards.model.db.AnswerEntryModel;
import wmii.jwzp.flashcards.model.db.FlashcardSetModel;
import wmii.jwzp.flashcards.model.db.UserModel;
import wmii.jwzp.flashcards.repository.AnswerEntryRepository;
import wmii.jwzp.flashcards.utils.errors.BadRequest;
import wmii.jwzp.flashcards.utils.errors.ResourceConflict;

@Service
public class AnswerEntryService {

  Logger logger = LoggerFactory.getLogger(AnswerEntryService.class);

  @Autowired
  private AnswerEntryRepository answerRepository;

  public AnswerEntryModel startEntry(UserModel user, FlashcardSetModel flashcardSet) {
    var answer = new AnswerEntryModel();
    String id = UUID.randomUUID().toString();
    answer.setId(id);
    answer.setUserId(user.getId());
    answer.setSetId(flashcardSet.getId());

    try {
      answerRepository.insert(answer.getId(), answer.getUserId(), answer.getSetId());
    } catch (DataIntegrityViolationException e) {
      throw new ResourceConflict("User can solve only one set at a time.");
    }
    return answer;
  }

  public AnswerEntryModel endEntry(UserModel user, FlashcardSetModel flashcardSet) {
    var entry = answerRepository.findExistingForUserBySet(user.getId(), flashcardSet.getId());
    if (!entry.isPresent()) {
      throw new BadRequest("Cannot submit answers before starting the set.");
    }
    entry.get().setEndedAt();
    answerRepository.save(entry.get());
    return entry.get();
  }

  public AnswerEntryModel checkAnswer(UserModel user, FlashcardSetModel flashcardSet, List<AnswerCardInput> input) {
    var cards = flashcardSet.getCards();
    for (var card : cards) {
      var inputCard = input.stream()
          .filter(e -> card.getId().equals(e.card_id))
          .findAny()
          .orElse(null);
      if (inputCard == null) {
        throw new BadRequest("Missing card answer.");
      }
      if (!card.getAnswer().equals(inputCard.answer)) {
        throw new ResourceConflict("Incorrect answer");
      }
    }
    return this.endEntry(user, flashcardSet);
  }

  public List<AnswerEntryModel> getAnswers(UserModel user, FlashcardSetModel flashcardSet) {
    var answers = answerRepository.findAllForUserBySet(user.getId(), flashcardSet.getId());
    return answers;
  }

}