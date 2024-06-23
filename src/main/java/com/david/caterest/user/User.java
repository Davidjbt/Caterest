package com.david.caterest.user;

import com.david.caterest.picture.Picture;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@Entity
@Table(name = "_user")
@AllArgsConstructor
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(length = 31_457_280) // 30MB
    private Byte[] profilePicture;

//    @NotBlank
    private String displayName;

//    @NotBlank
    private String firstName;

//    @NotBlank
    private String lastName;

//    @NotBlank
    @Column(unique = true)
    private String email;

//    @NotBlank
    private String password;

    private String biography;
    private LocalDate dateOfBirth;
    private String telephoneNumber;
    private String city;
    private String country;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Picture> pictures = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

