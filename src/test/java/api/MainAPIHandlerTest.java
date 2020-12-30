package test.java.api;

import main.java.api.MainApiHandler;
import org.junit.Test;

import java.net.URL;
import static org.junit.Assert.*;

public class MainAPIHandlerTest {
    private static final String LISTING_URL = "https://my.api.mockaroo.com/listing?key=63304c70";
    private static final String LOCATION_URL = "https://my.api.mockaroo.com/location?key=63304c70";
    private static final String LISTING_STATUS_URL = "https://my.api.mockaroo.com/listingStatus?key=63304c70";
    private static final String MARKETPLACE_URL = "https://my.api.mockaroo.com/marketplace?key=63304c70";

    @Test
    public static void Test() throws Exception {
        /**
         * Check the URL-s response. If it is not null the API request was success
         */
        URL url = new URL(LISTING_URL);
        String resultString = null;
        resultString = MainApiHandler.apiGetRequest(url);
        assertNotNull(resultString);

        url = new URL(LOCATION_URL);
        resultString = null;
        resultString = MainApiHandler.apiGetRequest(url);
        assertNotNull(resultString);

        url = new URL(LISTING_STATUS_URL);
        resultString = null;
        resultString = MainApiHandler.apiGetRequest(url);
        assertNotNull(resultString);

        url = new URL(MARKETPLACE_URL);
        resultString = null;
        resultString = MainApiHandler.apiGetRequest(url);
        assertNotNull(resultString);
    }
}
