package apap.sipayroll.service;

import apap.sipayroll.model.RoleModel;
import apap.sipayroll.respository.RoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
import org.springframework.transaction.annotation.Transactional;
>>>>>>> 23fdff1d8154799db8f8b395d6a71abbfd36b1a4

import java.util.List;

@Service
<<<<<<< HEAD
=======
@Transactional
>>>>>>> 23fdff1d8154799db8f8b395d6a71abbfd36b1a4
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDb roleDb;

    @Override
    public List<RoleModel> findAll() {
        return roleDb.findAll();
    }
}
