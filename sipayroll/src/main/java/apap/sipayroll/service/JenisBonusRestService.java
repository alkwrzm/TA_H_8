package apap.sipayroll.service;

import apap.sipayroll.model.BonusModel;
import apap.sipayroll.model.JenisBonusModel;

import java.util.List;

public interface JenisBonusRestService {
    List<JenisBonusModel> findAll();
    boolean validatorAndSetter(Long jenisBonus, BonusModel bonus, Integer gapokKaryawan);
}
