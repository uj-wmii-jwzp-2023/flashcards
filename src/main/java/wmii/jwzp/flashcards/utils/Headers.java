package wmii.jwzp.flashcards.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;

public class Headers extends HttpHeaders {

  private List<String> exposedHeaders = Arrays.asList("Accept", "Access-Control-Allow-Headers",
      "Access-Control-Allow-Methods",
      "Access-Control-Allow-Origin", "Content-Type");

  public Headers() {
    this.add("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
    this.add("Access-Control-Allow-Methods", "*");
    this.add("Access-Control-Allow-Origin", "*");
    this.add("Content-Type", "application/json");
    this.add("Access-Control-Allow-Credentials", "true");
    this.setAccessControlExposeHeaders(this.exposedHeaders);
  }

  public Headers addCookie(HttpCookie cookie) {
    this.add("Set-Cookie", cookie.toString());
    return this;
  }

}
