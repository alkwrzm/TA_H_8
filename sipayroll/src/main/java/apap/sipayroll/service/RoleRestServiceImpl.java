package apap.sipayroll.service;

import apap.sipayroll.model.RoleModel;
import apap.sipayroll.respository.RoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RoleRestServiceImpl implements RoleRestService{

    @Autowired
    private RoleDb roleDb;


    @Override
    public RoleModel findByNamaRole(String namaRole){
        Optional<RoleModel> role = roleDb.findByNamaRole(namaRole);
        if(role.isPresent()){
            return role.get();
        } else{
            throw new NoSuchElementException();
        }
    }
//
//    @Override
//    public ObatModel changeObat(Long idObat, ObatModel obatUpdate){
//        ObatModel obat = getObatByIDObat(idObat);
//        obat.setNama(obatUpdate.getNama());
//        obat.setKuantitas(obatUpdate.getKuantitas());
//        obat.setBentuk(obatUpdate.getBentuk());
//        return obatDb.save(obat);
//    }
//
//    @Override
//    public void deleteObat(Long idObat){
//        ObatModel obat = getObatByIDObat(idObat);
//        obatDb.delete(obat);
//    }
}
