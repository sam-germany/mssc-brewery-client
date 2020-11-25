package com.springframeworkguru.msscbreweryclient.web.client;


import com.springframeworkguru.msscbreweryclient.web.model.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class BreweryClient {

    public final String BEER_PATH_V1 = "/api/v1/beer/";

    private String apihost;
    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDto getBeerById(UUID uuid) {
        return restTemplate.getForObject(apihost + BEER_PATH_V1 + uuid.toString(), BeerDto.class);
    }


    public void setApihost(String apihost) {
        this.apihost = apihost;
    }

}

/*
ignoreInvalidFields

Flag to indicate that when binding to this object invalid fields should be ignored.
Invalid means invalid according to the binder that is used, and usually this means
fields of the wrong type (or that cannot be coerced into the correct type).


 */
