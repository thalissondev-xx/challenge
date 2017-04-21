package br.com.challenge;

import org.junit.Test;

import br.com.challenge.networking.RedditService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by thalissonestrela on 4/21/17.
 */

public class RedditServiceTest {

    @Test
    public void endPointTest() {

        // Check the endpoint
        assertThat("https://www.reddit.com/r/Android/", is(RedditService.SERVICE_ENDPOINT));
    }

}
