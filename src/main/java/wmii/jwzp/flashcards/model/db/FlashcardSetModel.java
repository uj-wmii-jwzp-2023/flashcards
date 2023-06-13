package wmii.jwzp.flashcards.model.db;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sets")
public class FlashcardSetModel {
  @Id
  private String id;

  @Column(name = "created_at")
  private @CreatedDate LocalDateTime createdAt;

  @Column(name = "group_id")
  private String groupId;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "is_public")
  private Boolean isPublic;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "flashcardSet", fetch = FetchType.LAZY)
  private List<CardModel> cards;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id", nullable = true, insertable = false, updatable = false)
  private StudyGroupModel group;

  public StudyGroupModel getGroup() {
    return group;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = true, insertable = false, updatable = false)
  private UserModel user;

  public UserModel getUser() {
    return user;
  }

  public List<CardModel> getCards() {
    return cards;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.groupId = null;
    this.userId = userId;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.userId = null;
    this.groupId = groupId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt() {
    this.createdAt = LocalDateTime.now();
  }

  public void setCreatedAt(LocalDateTime createdDate) {
    this.createdAt = createdDate;
  }

  public Boolean isPublic() {
    return this.isPublic;
  }

  public void setIsPublic(Boolean isPublic) {
    this.isPublic = isPublic;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}