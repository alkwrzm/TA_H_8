package apap.sipayroll.respository;

import apap.sipayroll.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDb extends JpaRepository<RoleModel, Long>  {

    List<RoleModel> findAll();

    Optional<RoleModel> findById(Long id);
}
