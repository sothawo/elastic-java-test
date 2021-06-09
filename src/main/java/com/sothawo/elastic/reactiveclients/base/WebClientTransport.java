/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.elastic.reactiveclients.base;

import co.elastic.clients.base.Endpoint;
import co.elastic.clients.json.JsonpMapper;
import org.elasticsearch.client.RequestOptions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class WebClientTransport implements ReactiveTransport {

    private final WebClient webClient;
    private final JsonpMapper mapper;

    public WebClientTransport(WebClient webClient, JsonpMapper mapper) {
        this.webClient = webClient;
        this.mapper = mapper;
    }

    @Override
    public JsonpMapper jsonpMapper() {
        return mapper;
    }

    @Override
    public <RequestT, ResponseT, ErrorT> Mono<ResponseT> performRequest(RequestT var1,
                                                                        Endpoint<RequestT, ResponseT, ErrorT> var2,
                                                                        RequestOptions var3) throws IOException {
        throw new UnsupportedOperationException("not implemented");
    }
}
