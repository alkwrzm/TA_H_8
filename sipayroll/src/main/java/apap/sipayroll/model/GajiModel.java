package apap.sipayroll.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "gaji")
public class GajiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "gaji_pokok", nullable = false)
    private Integer gajiPokok;

    @NotNull
    @Column(name = "status_persetujuan", nullable = false)
    private Integer statusPersetujuan;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tanggal_masuk", nullable = false)
    private Date tanggalMasuk;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid_penyetuju", referencedColumnName = "uuid", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel userPenyetujuModel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uuid_pengaju", referencedColumnName = "uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel userPengajuModel;

    @OneToOne(fetch = FetchType.EAGER,  optional = false)
    @JoinColumn(name = "id_user", referencedColumnName = "uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel userModel;


    @OneToMany(mappedBy = "gajiModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LemburModel> listLembur;

    @OneToMany(mappedBy = "gajiModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BonusModel> listBonus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(Integer gajiPokok) {
        this.gajiPokok = gajiPokok;
    }

    public Integer getStatusPersetujuan() {
        return statusPersetujuan;
    }

    public void setStatusPersetujuan(Integer statusPersetujuan) {
        this.statusPersetujuan = statusPersetujuan;
    }

    public Date getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(Date tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public UserModel getUserPenyetujuModel() {
        return userPenyetujuModel;
    }

    public void setUserPenyetujuModel(UserModel userPenyetujuModel) {
        this.userPenyetujuModel = userPenyetujuModel;
    }

    public UserModel getUserPengajuModel() {
        return userPengajuModel;
    }

    public void setUserPengajuModel(UserModel userPengajuModel) {
        this.userPengajuModel = userPengajuModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<LemburModel> getListLembur() {
        return listLembur;
    }

    public void setListLembur(List<LemburModel> listLembur) {
        this.listLembur = listLembur;
    }

    public List<BonusModel> getListBonus() {
        return listBonus;
    }

    public void setListBonus(List<BonusModel> listBonus) {
        this.listBonus = listBonus;
    }
}
