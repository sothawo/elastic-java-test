package com.sothawo.elastic.reactiveclients.base;

import co.elastic.clients.base.Endpoint;
import co.elastic.clients.json.JsonpMapper;
import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import reactor.core.publisher.Mono;

public interface ReactiveTransport {
    <RequestT, ResponseT, ErrorT> Mono<ResponseT> performRequest(RequestT var1, Endpoint<RequestT, ResponseT, ErrorT> var2, RequestOptions var3) throws IOException;

    JsonpMapper jsonpMapper();
}
