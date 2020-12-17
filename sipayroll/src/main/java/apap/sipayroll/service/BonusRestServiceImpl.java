package apap.sipayroll.service;

import apap.sipayroll.model.BonusModel;
import apap.sipayroll.model.GajiModel;
import apap.sipayroll.respository.BonusDb;
import apap.sipayroll.respository.JenisBonusDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BonusRestServiceImpl implements BonusRestService {
    @Autowired
    BonusDb bonusDb;

    @Override
    public BonusModel save(BonusModel bonus){
        return bonusDb.save(bonus);
    }
}
