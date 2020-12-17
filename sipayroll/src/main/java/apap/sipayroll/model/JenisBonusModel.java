package apap.sipayroll.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "jenis_bonus")
public class JenisBonusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "nama", nullable = false)
    private String nama;

    @OneToMany(mappedBy = "jenisBonusModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<BonusModel> listBonus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
