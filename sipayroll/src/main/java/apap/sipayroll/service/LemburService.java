package apap.sipayroll.service;

import apap.sipayroll.model.LemburModel;

import java.util.Optional;

public interface LemburService {
    LemburModel addLembur(LemburModel lembur);
    Optional<LemburModel> findById(Long idLembur);
    void changeLembur(LemburModel lembur);
    void deleteById(Long idLembur);
}
