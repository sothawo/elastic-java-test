package com.sothawo.elastic.reactiveclients.elasticsearch;

import co.elastic.clients.elasticsearch._global.SearchRequest;
import co.elastic.clients.elasticsearch._global.SearchResponse;
import com.sothawo.elastic.reactiveclients.base.ReactiveApiClient;
import com.sothawo.elastic.reactiveclients.base.ReactiveTransport;
import org.elasticsearch.client.RequestOptions;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class ReactiveElasticsearchClient extends ReactiveApiClient<ReactiveElasticsearchClient> {

    public ReactiveElasticsearchClient(ReactiveTransport transport) {
        super(transport, null);
    }

    public ReactiveElasticsearchClient(ReactiveTransport transport, RequestOptions options) {
        super(transport, options);
    }

    @Override
    public ReactiveElasticsearchClient withRequestOptions(RequestOptions options) {
        return new ReactiveElasticsearchClient(transport, options);
    }

    public <TDocument> Mono<SearchResponse<TDocument>> search(SearchRequest request, Class<TDocument> tDocumentClass) throws IOException {
        return this.transport.performRequest(request, SearchRequest.createSearchEndpoint(this.getDeserializer(tDocumentClass)), this.requestOptions);
    }

}
