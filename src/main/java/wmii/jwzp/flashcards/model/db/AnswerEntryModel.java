package wmii.jwzp.flashcards.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "answers")
public class AnswerEntryModel {

  @Id()
  private String id;

}
