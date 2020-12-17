package apap.sipayroll.service;

import apap.sipayroll.rest.BaseResponse;
import reactor.core.publisher.Mono;

public interface DetailGajiRestService {

    Mono<BaseResponse> getPelatihan(String username);
}
