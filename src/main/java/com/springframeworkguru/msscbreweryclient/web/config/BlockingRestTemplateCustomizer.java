package com.springframeworkguru.msscbreweryclient.web.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer maxTotalConnections;
    private final Integer defaultMaxTotalConnections;
    private final Integer connectionRequestTimeout;
    private final Integer socketTimeout;

    public BlockingRestTemplateCustomizer(@Value("${sfg.maxtotalconnections}") Integer maxTotalConnections,
                                          @Value("${sfg.defaultmaxtotalconnections}") Integer defaultMaxTotalConnections,
                                          @Value("${sfg.connectionrequesttiomeout}") Integer connectionRequestTimeout,
                                          @Value("${sfg.sockettimeout}") Integer socketTimeout) {
        this.maxTotalConnections = maxTotalConnections;
        this.defaultMaxTotalConnections = defaultMaxTotalConnections;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.socketTimeout = socketTimeout;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(20);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .build();

        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }


    @Override
    public void customize(RestTemplate restTemplate) {
       restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
