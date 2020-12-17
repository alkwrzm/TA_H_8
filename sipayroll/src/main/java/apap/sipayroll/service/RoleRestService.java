package apap.sipayroll.service;

import apap.sipayroll.model.RoleModel;

public interface RoleRestService {
    RoleModel findByNamaRole(String namaRole);
}
