package test;

import com.accenture.flowershop.be.entity.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserIntegrationTest {

    private RestTemplate restTemplate;
    private static final String URL_USER_LOGIN = "http://localhost:1111/disp/user/login?username={username}&password={password}";


    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void loginTest() {
        String userMike = "Mike";
        String passMike = "qwerty";

        Boolean booleanResponse1 = restTemplate.getForObject(URL_USER_LOGIN, Boolean.class, userMike, passMike);
        assertThat(booleanResponse1, is(false));

        String userKristi = "kristi";
        String passKristi = "pass";

        Boolean booleanResponse2 = restTemplate.getForObject(URL_USER_LOGIN, Boolean.class, userKristi, passKristi);
        assertThat(booleanResponse2, is(true));

        Boolean booleanResponse3 = restTemplate.getForObject(URL_USER_LOGIN, Boolean.class, userKristi, passMike);
        assertThat(booleanResponse3, is(false));

    }
}
