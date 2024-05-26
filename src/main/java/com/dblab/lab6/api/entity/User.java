package com.dblab.lab6.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;

    private String secondName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_user_roles_user_id",
                            foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE")),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_user_roles_role_id",
                            foreignKeyDefinition = "FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE"))
    )
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_permissions",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_user_permissions_user_id",
                            foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE")),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_user_permissions_permission_id",
                            foreignKeyDefinition = "FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE"))
    )
    private Set<Permission> permissions;
}
