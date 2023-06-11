package wmii.jwzp.flashcards.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wmii.jwzp.flashcards.model.api.input.CardInput;
import wmii.jwzp.flashcards.model.db.CardModel;
import wmii.jwzp.flashcards.model.db.FlashcardSetModel;
import wmii.jwzp.flashcards.repository.CardRepository;
import wmii.jwzp.flashcards.utils.errors.NotFound;

@Service
public class CardService {

  Logger logger = LoggerFactory.getLogger(CardService.class);

  @Autowired
  private CardRepository cardRepository;

  public CardModel createCard(CardInput input, FlashcardSetModel flashcardSet) {
    var card = new CardModel();
    String id = UUID.randomUUID().toString();
    card.setId(id);
    card.setCreatedAt();
    card.setQuestion(input.question);
    card.setAnswer(input.answer);
    card.setSetId(flashcardSet.getId());

    cardRepository.save(card);
    return card;
  }

  public CardModel getCardById(String cardId) {
    var card = cardRepository.findById(cardId);
    if (!card.isPresent()) {
      throw new NotFound("Card not found.");
    }

    return card.get();
  }

  public List<CardModel> getCards(FlashcardSetModel setModel) {
    var cards = cardRepository.findAllBySetId(setModel.getId());
    return cards;
  }

  public CardModel updateCard(CardModel cardModel, CardInput input) {
    if (input.question != null) {
      cardModel.setQuestion(input.question);
    }
    if (input.answer != null) {
      cardModel.setAnswer(input.answer);
    }

    cardRepository.save(cardModel);
    return cardModel;
  }

}