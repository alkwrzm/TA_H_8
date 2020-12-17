package apap.sipayroll.service;

import apap.sipayroll.model.BonusModel;
import apap.sipayroll.model.JenisBonusModel;
import apap.sipayroll.respository.JenisBonusDb;
import apap.sipayroll.respository.LemburDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class JenisBonusRestServiceImpl implements JenisBonusRestService {
    @Autowired
    JenisBonusDb jenisBonusDb;

    @Override
    public List<JenisBonusModel> findAll(){
        return jenisBonusDb.findAll();
    }

    @Override
    public boolean validatorAndSetter(Long jenisBonusId, BonusModel bonus, Integer gapokKaryawan){
        Optional<JenisBonusModel> jenisBonusOpt = jenisBonusDb.findById(jenisBonusId);
        boolean ret = false;
        if(jenisBonusOpt.isPresent()){
            JenisBonusModel jenisBonus = jenisBonusOpt.get();
            //Training: Id 1, add 150.000
            if(jenisBonus.getId()==1){
                //Set jumlah bonus di BonusModel
                bonus.setJumlahBonus(bonus.getJumlahBonus()+150000);
                ret=true;
            //THR (satu kali)
            }else if(jenisBonus.getId()==2){
                //Kalau sudah pernah dapat THR
                if(jenisBonus.getListBonus().size()>1){
                    ret = false;
                //Kalau belum
                }else{
                    //Set jumlahBonus
                    bonus.setJumlahBonus(gapokKaryawan);
                    ret=true;
                }
            }else if(jenisBonus.getId()==3) {
                //Kalau sudah pernah dapat Bonus Tahunan
                if (jenisBonus.getListBonus().size() > 1) {
                    ret = false;
                //Kalau belum
                } else {
                    //Set jumlahBonus
                    bonus.setJumlahBonus(2*gapokKaryawan);
                    ret=true;
                }
            }
        } else{
            throw new NoSuchElementException();
        }
        return ret;
    }
}
