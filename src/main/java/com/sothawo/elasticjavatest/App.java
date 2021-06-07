/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.elasticjavatest;

import co.elastic.clients.base.RestClientTransport;
import co.elastic.clients.base.Transport;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._global.SearchRequest;
import co.elastic.clients.elasticsearch._global.SearchResponse;
import co.elastic.clients.elasticsearch._global.search.Hit;
import co.elastic.clients.json.jsonb.JsonbJsonpMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class App {

    public static void main(String[] args) throws IOException {

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

            // Search all items in an index that contains documents of type AppData
            SearchResponse<StringObjectMap> search = client.search(s -> s
                    .index("appdata-index"),
                StringObjectMap.class
            );

            var builder = new SearchRequest.Builder();
            builder.in
            if (search.hits().hits().isEmpty()) {
                System.out.println("No match");
            } else {
                for (Hit<StringObjectMap> hit : search.hits().hits()) {
                    processAppData(hit._source());
                }
            }
        }
    }

    private static void processAppData(StringObjectMap appData) {
        if (appData != null) {
            System.out.println(appData.toString());
        }
    }
}
