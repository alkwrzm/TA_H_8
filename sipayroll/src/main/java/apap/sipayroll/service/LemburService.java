package apap.sipayroll.service;

import apap.sipayroll.model.LemburModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LemburService {
    LemburModel addLembur(LemburModel lembur);
    Optional<LemburModel> findById(Long idLembur);
    void changeLembur(LemburModel lembur);
    void deleteById(Long idLembur);
    List<LemburModel> findAll();
    List<LemburModel> findByIdGaji(Long idGaji);
    public List<Integer> getAllKompensasiList();
    public List<Integer> getKompensasiList(Long idGaji);
    Integer getKompensasi(LemburModel lembur);
    boolean dateValidator(String waktuMulai, String waktuSelesai);
}
