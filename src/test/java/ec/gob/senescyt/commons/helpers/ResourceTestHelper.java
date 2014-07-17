package ec.gob.senescyt.commons.helpers;

import com.sun.jersey.api.client.ClientResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;

public class ResourceTestHelper {

    public static void assertErrorMessage(ClientResponse response, String expectedErrorMessage) {
        String errorMessage = response.getEntity(String.class);
        MatcherAssert.assertThat(errorMessage, CoreMatchers.containsString(expectedErrorMessage));
    }
}