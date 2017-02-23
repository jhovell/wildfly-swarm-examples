package org.wildfly.swarm.examples.jaxrs.cdi;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author Bob McWhirter
 */
@RunWith(Arquillian.class)
public class JAXRSApplicationIT extends AbstractIntegrationTest {

    private HttpClient httpClient = httpClient = HttpClientBuilder.create().build();

    @Drone
    WebDriver browser;

    @Test
    @Ignore
    public void testIt() {
        browser.navigate().to("http://localhost:8080/employees");
        assertThat(browser.getPageSource()).contains("{\"id\":1,\"name\":\"emp01\"}");
    }
    
    @Test
    public void testValid() throws IOException {
        final String url = "http://localhost:8080/employees";
        final HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity("{ \"id\": 1, \"name\": null}"));
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accepts", "application/json");
        final HttpResponse response = httpClient.execute(post);
        final String body = EntityUtils.toString(response.getEntity());

        // Assert that we actually did get a JSR-303 validation response        
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(400);
        assertThat(body).contains("may not be null");
    }
}
