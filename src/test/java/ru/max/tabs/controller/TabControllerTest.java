package ru.max.tabs.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.max.tabs.dto.ErrorItem;
import ru.max.tabs.dto.TabFilter;
import ru.max.tabs.dto.TabItem;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TabControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
    }

    @Test
    public void getCorrentAnswerTest() {
        TabItem tabItem = this.restTemplate.getForObject("/tabs/10", TabItem.class);
        assertThat(tabItem.getId(), is(BigInteger.TEN));
        assertThat(tabItem.getTitle(), is("title"));
        assertThat(tabItem.getUrl(), is("url"));
    }

    @Test
    public void getErrorAnswerTest() {
        ErrorItem body = this.restTemplate.getForObject("/tabs/0", ErrorItem.class);
        assertThat(body.getCode(), is("code"));
        assertThat(body.getMessage(), is("message"));
    }

    @Test
    public void getTabsByFilterCorrectAnswerTest() throws URISyntaxException {
        TabFilter tabFilter = TabFilter.builder().title("title").url("www.vermillion.com").build();

        ParameterizedTypeReference<List<TabItem>> typeRef = new ParameterizedTypeReference<List<TabItem>>() {
        };

        List<TabItem> result = restTemplate.exchange(new URI("/tabs/filter"), HttpMethod.POST, new HttpEntity<>(tabFilter), typeRef).getBody();

        assertThat(result.size(), is(2));

        assertThat(result.get(0).getUrl(), is("www.vermillion.com"));
        assertThat(result.get(0).getTitle(), is("title"));

        assertThat(result.get(1).getUrl(), is("www.vermillion.com"));
        assertThat(result.get(1).getTitle(), is("title"));
    }


    @Test
    public void getTabsByFilterErrorAnswerTest() throws URISyntaxException {
        TabFilter filter = new TabFilter();

        ResponseEntity<ErrorItem> errorItem = restTemplate.postForEntity(new URI("/tabs/filter"), filter, ErrorItem.class);

        assertThat(errorItem.getBody().getMessage(), is("message"));
        assertThat(errorItem.getBody().getCode(), is("code"));
    }

    @Test
    public void updateTabCorrectAnswerTest() throws URISyntaxException {
        String newTitle = "new title";
        String newUrl = "www.new-url.com";
        BigInteger id = BigInteger.ONE;

        ResponseEntity<TabItem> result = restTemplate.exchange(new URI("/tabs/1"), HttpMethod.PUT, new HttpEntity<>(new TabItem(newTitle, newUrl, id)), TabItem.class);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(result.getBody().getTitle(), is(newTitle));
        assertThat(result.getBody().getUrl(), is(newUrl));
        assertThat(result.getBody().getId(), is(id));
    }

    @Test
    public void updateTabErrorAnswerTest() throws URISyntaxException {
        ResponseEntity<ErrorItem> result = restTemplate.exchange(new URI("/tabs/0"), HttpMethod.PUT, new HttpEntity<>(new TabItem()), ErrorItem.class);

        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(result.getBody().getCode(), is("code"));
        assertThat(result.getBody().getMessage(), is("message"));
    }

    @Test
    public void createTabCorrectAnswerTest() throws URISyntaxException {
        String newTitle = "new title";
        String newUrl = "www.new-url.com";

        ResponseEntity<TabItem> result = restTemplate.exchange(new URI("/tabs"), HttpMethod.POST, new HttpEntity<>(new TabItem(newTitle, newUrl, null)), TabItem.class);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(result.getBody().getId());
    }

  /*  @Test
    public void createTabErrorAnswerTest() throws URISyntaxException {
        TabItem tabItem = null;

        ResponseEntity<ErrorItem> result = restTemplate.exchange(new URI("/tabs"), HttpMethod.POST, new HttpEntity<>(new Tas), ErrorItem.class);

        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(result.getBody().getCode(), is("code"));
        assertThat(result.getBody().getMessage(), is("message"));
    }
*/

    @Test
    public void deleteTabCorrectAnswerTest() throws URISyntaxException {
        ResponseEntity<TabItem> result = restTemplate.exchange(new URI("/tabs/1"), HttpMethod.DELETE, HttpEntity.EMPTY, TabItem.class);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }
}
