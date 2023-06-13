package wmii.jwzp.flashcards.model.db;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "answers")
public class AnswerEntryModel {

  @Id()
  private String id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "set_id")
  private String setId;

  @Column(name = "started_at")
  private LocalDateTime startedAt;

  @Column(name = "end_at")
  private LocalDateTime endedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
  private UserModel user;

  public UserModel getUser() {
    return user;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "set_id", nullable = false, insertable = false, updatable = false)
  private FlashcardSetModel flashcardSet;

  public FlashcardSetModel getSet() {
    return flashcardSet;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LocalDateTime getEndedAt() {
    return endedAt;
  }

  public void setEndedAt() {
    this.endedAt = LocalDateTime.now();
  }

  public LocalDateTime getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(LocalDateTime startedAt) {
    this.startedAt = startedAt;
  }

  public String getSetId() {

    return setId;
  }

  public void setSetId(String setId) {
    this.setId = setId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }

}
