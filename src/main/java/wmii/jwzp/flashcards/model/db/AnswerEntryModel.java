package wmii.jwzp.flashcards.model.db;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

  @Column(name = "ended_at")
  private LocalDate endedAt;

  public LocalDate getEndedAt() {
    return endedAt;
  }

  public void setEndedAt(LocalDate endedAt) {
    this.endedAt = endedAt;
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
