package apap.sipayroll.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LowonganDetail {
    private String status;

    @JsonProperty("divisi")
    private String divisi;

    @JsonProperty("posisi")
    private String posisi;

    @JsonProperty("jumlah")
    private Integer jumlah;

    @JsonProperty("username")
    private String username;

    @JsonProperty("jenisLowongan")
    private Integer jenisLowongan;

    public Integer getJenisLowongan() {
        return jenisLowongan;
    }

    public void setJenisLowongan(Integer jenisLowongan) {
        this.jenisLowongan = jenisLowongan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
