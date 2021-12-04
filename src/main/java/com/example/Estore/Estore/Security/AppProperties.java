package com.example.Estore.Estore.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * This class fetches value stored in application properties.
 */
@Component
public class AppProperties {

    /**
     * Inject environment variable dependency.
     */
    @Autowired
    private Environment env;

    /**
     * Method is used to fetch token secret value from application properties.
     * @return String.
     */
    public String getTokenSecret(){
        return env.getProperty("tokenSecret");
    }
}
