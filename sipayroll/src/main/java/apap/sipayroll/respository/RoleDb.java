package apap.sipayroll.respository;

import apap.sipayroll.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDb extends JpaRepository<RoleModel, Long>  {

    List<RoleModel> findAll();
}
