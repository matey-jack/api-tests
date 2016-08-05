import hello.Quote;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

public class ApiTest {
    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void test() throws Exception {
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        assertEquals(quote.getType(), "success");
    }
}