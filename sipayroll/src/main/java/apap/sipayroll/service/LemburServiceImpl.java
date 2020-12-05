package apap.sipayroll.service;

import apap.sipayroll.model.LemburModel;
import apap.sipayroll.respository.LemburDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class LemburServiceImpl implements LemburService{
    @Autowired
    LemburDb lemburDb;

    @Override
    public LemburModel addLembur(LemburModel lembur){
        lembur.setStatusPersetujuan(0);
        return lemburDb.save(lembur);
    }

    @Override
    public Optional<LemburModel> findById(Long idLembur){
        return lemburDb.findById(idLembur);
    }

    @Override
    public void changeLembur(LemburModel lembur){
        lemburDb.save(lembur);
    }

    @Override
    public void deleteById(Long idLembur){
        lemburDb.deleteById(idLembur);
    }

    @Override
    public List<LemburModel> findAll(){
        return lemburDb.findAll();
    }

    @Override
    public List<LemburModel> findByIdGaji(Long idGaji){
        List<LemburModel> lemburs = findAll();
        List<LemburModel> listLembur = new ArrayList<>();
            for(LemburModel lembur : lemburs){
                Long id = (lembur.getGajiModel().getId());
                if(id.equals(idGaji)){
                    listLembur.add(lembur);
            }
        }
            return listLembur;
    }
    @Override
    public List<Integer> getAllKompensasiList(){
        List<LemburModel> lemburs = findAll();
        List<Integer> kompensasi = new ArrayList<>();
        for(LemburModel lembur:lemburs){
            kompensasi.add(getKompensasi(lembur));
        }
        return kompensasi;
    }
    @Override
    public List<Integer> getKompensasiList(Long idGaji){
        List<LemburModel> lemburs = findByIdGaji(idGaji);
        List<Integer> kompensasi = new ArrayList<>();
        for(LemburModel lembur:lemburs){
            kompensasi.add(getKompensasi(lembur));
        }
        return kompensasi;
    }

    @Override
    public Integer getKompensasi(LemburModel lembur){
        Calendar waktuMulai = Calendar.getInstance();
        waktuMulai.setTime(lembur.getWaktuMulai());
        Calendar waktuSelesai = Calendar.getInstance();
        waktuSelesai.setTime(lembur.getWaktuSelesai());
        return((waktuSelesai.get(Calendar.HOUR_OF_DAY)-waktuMulai.get(Calendar.HOUR_OF_DAY))*150000);
    }

    @Override
    public boolean dateValidator(String waktuMulai, String waktuSelesai){
        boolean ret = false;
        if(waktuMulai.substring(9,11).equals(waktuSelesai.substring(9,11))){
            ret = true;
        }
        return ret;
    }
}
