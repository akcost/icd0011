package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private Boolean enabled;
    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "AUTHORITIES", joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"))
    private List<Authority> authorities = new ArrayList<>();

}
