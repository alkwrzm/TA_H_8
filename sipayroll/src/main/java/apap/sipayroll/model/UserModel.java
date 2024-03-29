package apap.sipayroll.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

import apap.sipayroll.model.GajiModel;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "pengguna")
public class UserModel implements Serializable {
    @Id
    @Size(max=200)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String uuid;

    @NotNull
    @Size(max = 50)
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Size(max = 200)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "lamaBerkerja")
    private Integer lamaBerkerja;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RoleModel roleModel;

    @OneToOne(mappedBy = "userModel", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GajiModel gajiModel;

    @OneToMany(mappedBy = "userModel", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<GajiModel> listGajiModel;


    public Integer getLamaBerkerja() {
        return lamaBerkerja;
    }

    public void setLamaBerkerja(Integer lamaBerkerja) {
        this.lamaBerkerja = lamaBerkerja;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }

    public GajiModel getGajiModel() {
        return gajiModel;
    }

    public void setGajiModel(GajiModel gajiModel) {
        this.gajiModel = gajiModel;
    }

    public List<GajiModel> getListGajiModel() {
        return listGajiModel;
    }

    public void setListGajiModel(List<GajiModel> listGajiModel) {
        this.listGajiModel = listGajiModel;
    }
}