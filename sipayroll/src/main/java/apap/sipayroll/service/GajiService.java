package apap.sipayroll.service;

import apap.sipayroll.model.GajiModel;
import java.util.List;

public interface GajiService {
    GajiModel getGajiById(Long id);

    void addGaji(GajiModel gaji);

    void deleteGaji(GajiModel gaji);

    List<GajiModel> getListGaji();

    GajiModel updateGaji(GajiModel gajiModel);
}
