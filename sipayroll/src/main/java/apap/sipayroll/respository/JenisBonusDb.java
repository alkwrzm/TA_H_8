package apap.sipayroll.respository;

import apap.sipayroll.model.JenisBonusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JenisBonusDb extends JpaRepository<JenisBonusModel, Long> {
    List<JenisBonusModel> findAll();
}
