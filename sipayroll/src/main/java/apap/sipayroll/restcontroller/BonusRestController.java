package apap.sipayroll.restcontroller;
import apap.sipayroll.model.BonusModel;
import apap.sipayroll.service.BonusRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class BonusRestController {

    @Autowired
    private BonusRestService bonusRestService;

    @PostMapping(value="/bonus")
    private BonusModel addBonus(@Valid
                                    @RequestBody BonusModel bonus, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            ResponseEntity.ok("Add Bonus Success");
            return bonusRestService.save(bonus);
        }
    }


}
