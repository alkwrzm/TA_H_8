package apap.sipayroll.service;

import apap.sipayroll.model.RoleModel;
import apap.sipayroll.respository.RoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDb roleDb;

    @Override
    public List<RoleModel> findAll() {
        return roleDb.findAll();
    }
}
