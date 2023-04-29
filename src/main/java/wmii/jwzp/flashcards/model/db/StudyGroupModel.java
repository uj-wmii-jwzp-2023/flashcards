package wmii.jwzp.flashcards.model.db;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "groups")
public class StudyGroupModel {
  @Id
  private String id;

  private String name;

  @Column(name = "created_at")
  private @CreatedDate LocalDateTime createdAt;

  @OneToMany(mappedBy = "group")
  private List<UserGroupLinkModel> users;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt() {
    this.createdAt = LocalDateTime.now();
  }

}