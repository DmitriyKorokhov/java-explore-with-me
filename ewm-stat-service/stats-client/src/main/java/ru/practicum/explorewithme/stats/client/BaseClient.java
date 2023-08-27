package ru.practicum.explorewithme.stats.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.explorewithme.stats.dto.HitDto;
import ru.practicum.explorewithme.stats.dto.StatsDto;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
public abstract class BaseClient {
    protected static final String BASE_URL = "http://localhost:9090";
    WebClient client = WebClient.create(BASE_URL);

    protected List<StatsDto> get(String path, MultiValueMap<String, String> parameters) {
        Flux<StatsDto> flux = client.get().uri(uriBuilder ->
                uriBuilder.path(path).queryParams(parameters).build()).retrieve().bodyToFlux(StatsDto.class);
        return flux.collect(Collectors.toList()).share().block();
    }

    protected <T> void post(String path, T body) {
        final Mono<ClientResponse> postResponse = client.post().uri(path).body(Mono.just(body), HitDto.class).accept(APPLICATION_JSON).exchange();
        postResponse.map(ClientResponse::statusCode).subscribe(httpStatus ->
                log.info(httpStatus.toString()));
    }
}
