package apap.sipayroll.respository;

import apap.sipayroll.model.GajiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GajiDb extends JpaRepository<GajiModel, Long> {

}