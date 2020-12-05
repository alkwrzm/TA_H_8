package apap.sipayroll.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "bonus")
public class BonusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jumlah_bonus", nullable = false)
    private Integer jumlahBonus;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_gaji", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private GajiModel gajiModel;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tanggal_diberikan", nullable = false)
    private Date tanggalDiberikan;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_jenis_bonus", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JenisBonusModel jenisBonusModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getJumlahBonus() {
        return jumlahBonus;
    }

    public void setJumlahBonus(Integer jumlahBonus) {
        this.jumlahBonus = jumlahBonus;
    }

    public GajiModel getGajiModel() {
        return gajiModel;
    }

    public void setGajiModel(GajiModel gajiModel) {
        this.gajiModel = gajiModel;
    }

    public JenisBonusModel getJenisBonusModel() {
        return jenisBonusModel;
    }

    public void setJenisBonusModel(JenisBonusModel jenisBonusModel) {
        this.jenisBonusModel = jenisBonusModel;
    }

    public Date getTanggalDiberikan() {
        return tanggalDiberikan;
    }

    public void setTanggalDiberikan(Date tanggalDiberikan) {
        this.tanggalDiberikan = tanggalDiberikan;
    }
}
