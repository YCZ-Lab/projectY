package com.ysh.projectY.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(name = "role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", insertable = false, length = 32, nullable = false)
    private int id;

    @Column(name = "`role_name`", columnDefinition = "varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '角色名称'")
    private String roleName;

    @Column(name = "`role_descript`", columnDefinition = "varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '角色描述'")
    private String roleDescript;

    @ColumnDefault("1")
    @Column(name = "`role_enabled`", columnDefinition = "tinyint(1) NOT NULL comment '角色是否启用'")
    private boolean enabled;

    // 应该使用@OneToMany ????
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Authorization> auths;

    // 应该使用@OneToMany ????
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Menu> menus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescript() {
        return roleDescript;
    }

    public void setRoleDescript(String roleDescript) {
        this.roleDescript = roleDescript;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authorization> getAuths() {
        return auths;
    }

    public void setAuths(List<Authorization> auths) {
        this.auths = auths;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return id == role.id && roleName.equals(role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleDescript='" + roleDescript + '\'' +
                ", enabled=" + enabled +
                ", auths=" + auths +
                ", menus=" + menus +
                '}';
    }
}
