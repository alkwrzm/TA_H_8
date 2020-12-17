package apap.sipayroll.service;

import apap.sipayroll.rest.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@Transactional
public class DetailGajiRestServiceImpl implements DetailGajiRestService{
    private final WebClient webClient;

    @Autowired
    private DetailGajiRestService detailGajiRestService;

    public DetailGajiRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("https://4a83a98a-8cec-46f5-aa5c-e38965f3efcd.mock.pstmn.io").build();
    }

    @Override
    public Mono<BaseResponse> getPelatihan(String username) {
        return this.webClient.get().uri("/rest/si-pelatihan/peserta/" + username).retrieve().bodyToMono(BaseResponse
        .class);
    }
}
