/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.elasticjavatest;

import co.elastic.clients.base.RestClientTransport;
import co.elastic.clients.base.Transport;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._global.SearchRequest;
import co.elastic.clients.elasticsearch._global.SearchResponse;
import co.elastic.clients.json.jsonb.JsonbJsonpMapper;
import com.sothawo.elastic.reactiveclients.base.WebClientTransport;
import com.sothawo.elastic.reactiveclients.elasticsearch.ReactiveElasticsearchClient;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.io.IOException;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class App {

    public static void main(String[] args) throws IOException {

        var searchRequest = new SearchRequest.Builder().index("appdata-index").build();

        imperative(searchRequest);
        reactive(searchRequest);
    }

    private static void imperative(SearchRequest searchRequest) throws IOException {
        // Create the low-level client
        var restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200))
            .setHttpClientConfigCallback(builder -> {
                builder.setProxy(new HttpHost("localhost", 8080));
                return builder;
            });

        // RestClient must be closed
        try (RestClient restClient = restClientBuilder.build()) {

            // Create the transport that provides JSON and http services to API clients
            Transport transport = new RestClientTransport(restClient, new JsonbJsonpMapper());

            // And create our API client
            ElasticsearchClient client = new ElasticsearchClient(transport);

            // we could use JsonObject instead of StringObjectMap, but we need a map for SDE mapping anyway
            SearchResponse<StringObjectMap> searchResponse = client.search(searchRequest, StringObjectMap.class);

            System.out.println(searchResponse.hits().total());

        }
    }

    private static void reactive(SearchRequest searchRequest) throws IOException {
        HttpClient httpClient = HttpClient.create().compress(true);
        httpClient = httpClient.proxy(proxyOptions ->
            proxyOptions.type(ProxyProvider.Proxy.HTTP).host("localhost")
                .port(8080));

        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:9200")
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

        var transport = new WebClientTransport(webClient, new JsonbJsonpMapper());

        var client = new ReactiveElasticsearchClient(transport);

        var searchResponseMono = client.search(searchRequest, StringObjectMap.class);

        searchResponseMono.subscribe(searchResponse -> System.out.println(searchResponse.hits().total()));
    }

}
