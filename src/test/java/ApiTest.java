import hello.Quote;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import twitter.SearchResponse;
import twitter.TokenResponse;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class ApiTest {
    static String API_KEY = "Gb41p6cRBn2mrA6IZy2iyHMRC";
    static String API_SECRET = "D5ILZ4tVQkko54gBr7CZypCrMlxF03jHAcwcDmR34r11O0xg93";

    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void test() throws Exception {
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        assertEquals(quote.getType(), "success");
    }

    public String getToken() {
        String encodedKey = Base64.getEncoder().encodeToString((API_KEY + ":" + API_SECRET).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedKey);
        // headers.setAcceptCharset(asList(Charset.forName("UTF-8")));

        HttpEntity<String> entity = new HttpEntity<>("grant_type=client_credentials", headers);

        final ResponseEntity<TokenResponse> response = restTemplate.exchange("https://api.twitter.com/oauth2/token",
                HttpMethod.POST, entity, TokenResponse.class);
        // System.out.println(response.getBody().token_type);
        return response.getBody().access_token;
    }

    @Test
    public void testSearch() throws Exception {
        String token = "AAAAAAAAAAAAAAAAAAAAADCqwQAAAAAAsH6MMPSgStITNItJbUirCfxknAw%3DAaj0KDElddkzNrn4CFksYGqf5XSUnyibBnVGogakuuvX7ALbLx";
        String searchUrl = "https://api.twitter.com/1.1/search/tweets.json?q={query}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        final ResponseEntity<String> response = restTemplate.exchange(searchUrl,
                HttpMethod.GET, new HttpEntity<String>(headers), String.class, "#rationality");
        System.out.println(response.getBody());
    }
}