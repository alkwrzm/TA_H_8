package apap.sipayroll.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lembur")
public class LemburModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="waktu_mulai", nullable = false)
    @DateTimeFormat(iso=ISO.DATE)
    private Date waktu_mulai;

    @NotNull
    @Column(name="waktu_selesai", nullable = false)
    @DateTimeFormat(iso=ISO.DATE)
    private Date waktu_selesai;

    @NotNull
    @Column(name = "kompensasi_per_jam", nullable = false)
    private Long kompensasi_per_jam;

    @NotNull
    @Column(name = "status_persetujuan", nullable = false)
    private Long status_persetujuan;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_gaji", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private GajiModel gajiModel;
}