package wmii.jwzp.flashcards.model.api.output;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import wmii.jwzp.flashcards.model.db.CardModel;
import wmii.jwzp.flashcards.model.db.FlashcardSetModel;

public class FlashcardSetResponse {
  public String id;
  public String group_id;
  public String user_id;
  public Boolean is_public;
  public String name;
  public LocalDateTime created_at;
  public List<CardResponse> cards;

  public FlashcardSetResponse(FlashcardSetModel setModel) {
    this.id = setModel.getId();
    this.group_id = setModel.getGroupId();
    this.user_id = setModel.getUserId();
    this.created_at = setModel.getCreatedAt();
    this.is_public = setModel.isPublic();
    this.name = setModel.getName();
  }

  public FlashcardSetResponse(FlashcardSetModel setModel, List<CardModel> cards) {
    this.id = setModel.getId();
    this.group_id = setModel.getGroupId();
    this.user_id = setModel.getUserId();
    this.created_at = setModel.getCreatedAt();
    this.is_public = setModel.isPublic();
    this.name = setModel.getName();
    this.cards = cards.stream().map(e -> new CardResponse(e)).collect(Collectors.toList());
  }
}
