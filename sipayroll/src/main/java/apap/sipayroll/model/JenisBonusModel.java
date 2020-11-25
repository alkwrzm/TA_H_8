package apap.sipayroll.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "jenis_bonus")
public class JenisBonusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 200)
    @Column(name = "gaji_pokok", nullable = false)
    private String nama;

    @OneToMany(mappedBy = "jenisBonusModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BonusModel> listBonus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<BonusModel> getListBonus() {
        return listBonus;
    }

    public void setListBonus(List<BonusModel> listBonus) {
        this.listBonus = listBonus;
    }
}
