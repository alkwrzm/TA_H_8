package apap.sipayroll.service;

import apap.sipayroll.model.GajiModel;
import apap.sipayroll.respository.GajiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GajiServiceImpl implements GajiService{
    @Autowired
    GajiDb gajiDb;
  
    @Override
    public GajiModel getGajiById(Long id){
        return gajiDb.findGajiModelById(id);
    }


    @Override
    public void addGaji(GajiModel gaji){
        gajiDb.save(gaji);
    }

    @Override
    public void deleteGaji(GajiModel gaji){
        gajiDb.delete(gaji);
    }

    @Override
    public List<GajiModel> getListGaji(){
        return gajiDb.findAll();
    }

    @Override
    public GajiModel updateGaji(GajiModel gajiModel){
        gajiDb.save(gajiModel);

        return gajiModel;
    }

}
