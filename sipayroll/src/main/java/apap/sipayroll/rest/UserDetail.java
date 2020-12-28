package apap.sipayroll.rest;

import apap.sipayroll.model.RoleModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetail {

    @JsonProperty("idPegawai")
    private  String idPegawai;


    @JsonProperty("username")
    private  String username;


    @JsonProperty("nama")
    private  String nama;


    @JsonProperty("noTelepon")
    private  String noTelepon;


    @JsonProperty("tempatLahir")
    private  String tempatLahir;


    @JsonProperty("tanggalLahir")
    private  String tanggalLahir;


    @JsonProperty("alamat")
    private  String alamat;


    @JsonProperty("roleId")
    private Long roleId;

    public String getIdPegawai() {
        return idPegawai;
    }

    public void setIdPegawai(String idPegawai) {
        this.idPegawai = idPegawai;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }


    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }



    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public Long getIdRole() {
        return roleId;
    }

    public void setIdRole(Long idRole) {
        this.roleId = idRole;
    }
}

