package test;

import com.accenture.flowershop.be.entity.product.Product;
import com.accenture.flowershop.be.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FlowerIntegrationTest {

    private RestTemplate restTemplate;
    private static final String URL_FLOWERS = "http://localhost:1111/disp/flowers";

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }


    @Test
    public void getProductByIdTest() {
        int id = 4;
        ResponseEntity<Product> productResponseEntity =
                restTemplate.getForEntity(URL_FLOWERS + "/{id}", Product.class, id);
        assertThat(productResponseEntity.getBody().getName(), is("Ромашка"));
        assertThat(productResponseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void putProduct() {
        Product product = new Product();
        product.setName("Roza");
        product.setPrice(new BigDecimal(150));

        HttpEntity<Product> request = new HttpEntity<>(product);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<Product> userResponseEntity = restTemplate.exchange(URL_FLOWERS, HttpMethod.POST, request, Product.class);

        assertThat(userResponseEntity.getBody().getName(), is(product.getName()));

    }

    @Test
    public void getAllProducts() {
        ResponseEntity<List<Product>> listResponseEntity =
                restTemplate.exchange(URL_FLOWERS, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Product>>() {
                        });
        List<Product> products = listResponseEntity.getBody();

        assertThat(products.get(3).getName(), is("Ромашка"));
        assertThat(products.get(3).getId(), is(4));
        assertThat(products.get(3).getPrice().intValue(), is(20));

        assertThat(products.get(5).getName(), is("Орхидея"));
        assertThat(products.get(5).getId(), is(6));
        assertThat(products.get(5).getPrice().intValue(), is(70));

    }


    //получение бина
    //  Person person = restTemplate.getForObject("/persons/{id}", Person.class, id);
    //  assertThat(person.getName(), is("Joe"));

    //получение JSON
    // ResponseEntity<User> response = restTemplate.postForEntity("/persons", person, Person.class);
    //  assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
    //  assertThat(response.getBody().getId(), notNullValue());
    //  assertThat(response.getBody().getName(), is("Michail"));

    //HttpEntity<Person> entity = new HttpEntity<Person>(person);
    //ResponseEntity<Person> response = restTemplate.exchange("/persons/{id}", HttpMethod.PUT, entity, Person.class,
    //        id);


}
