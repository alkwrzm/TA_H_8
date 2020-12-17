package apap.sipayroll.service;

import apap.sipayroll.rest.LowonganDetail;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LowonganRestService {
    Mono<String> requestLowongan(LowonganDetail lowongan);
}
