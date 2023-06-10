package wmii.jwzp.flashcards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import wmii.jwzp.flashcards.model.db.CardModel;

public interface CardRepository extends JpaRepository<CardModel, String> {
  List<CardModel> findAllBySetId(String setId);

}