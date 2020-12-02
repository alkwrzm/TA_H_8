package apap.sipayroll.service;

import apap.sipayroll.model.LemburModel;
import apap.sipayroll.respository.LemburDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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


}
