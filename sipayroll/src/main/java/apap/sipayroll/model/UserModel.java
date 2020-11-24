package apap.sipayroll.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class UserModel implements Serializable {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid")
    private String uuid;

    @NotNull
    @Size(max = 50)
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Size(max = 200)
    @Column(name = "password", nullable = false)
    private String password;


    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_gaji", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private GajiModel gajiModel;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RoleModel roleModel;

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

    public GajiModel getGajiModel() {
        return gajiModel;
    }

    public void setGajiModel(GajiModel gajiModel) {
        this.gajiModel = gajiModel;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }
}