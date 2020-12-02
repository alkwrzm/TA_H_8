package apap.sipayroll.respository;

import apap.sipayroll.model.LemburModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LemburDb extends JpaRepository<LemburModel, Long> {
    Optional<LemburModel> findById(Long idLembur);
    void deleteById(Long idLembur);
}
