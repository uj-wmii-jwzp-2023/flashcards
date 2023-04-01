package wmii.jwzp.flashcards.utils.errors;

import org.springframework.web.server.ResponseStatusException;

public class BadRequest extends ResponseStatusException {
  public BadRequest(String errorMessage) {
    super(org.springframework.http.HttpStatus.BAD_REQUEST, errorMessage);
  }
}
