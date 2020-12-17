package apap.sipayroll.respository;

import apap.sipayroll.model.BonusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BonusDb extends JpaRepository<BonusModel, Long> {
}
