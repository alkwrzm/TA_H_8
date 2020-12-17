package apap.sipayroll.service;
import apap.sipayroll.rest.LowonganDetail;
import apap.sipayroll.rest.Setting;
import apap.sipayroll.service.LowonganRestService;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;

@Service
@Transactional
public class LowonganRestServiceImpl implements LowonganRestService {
    private final WebClient webClient;

    public LowonganRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.lowonganUrl).build();
    }

    @Override
    public  Mono<String> requestLowongan(LowonganDetail lowongan){
        return this.webClient
                .post()
                .uri("api/v1/lowongan/add")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(lowongan)
                .retrieve()
                .bodyToMono(String.class);
        // .block();
    }
}