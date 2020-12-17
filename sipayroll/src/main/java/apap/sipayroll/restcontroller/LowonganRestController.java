package apap.sipayroll.restcontroller;

import apap.sipayroll.rest.LowonganDetail;
import apap.sipayroll.service.*;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class LowonganRestController {
    @Autowired
    LowonganRestService lowonganRestService;

    @GetMapping(value = "/lowongan")
    private Mono<String> requestLowongan(@ModelAttribute LowonganDetail lowongan){
        return lowonganRestService.requestLowongan(lowongan);
    }
}