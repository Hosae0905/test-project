package com.example.miniprojecttest.member.model.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellerIdx;
    @Column(nullable = false)
    private String sellerID;
    @Column(nullable = false)
    private String sellerPW;
    @Column(nullable = false)
    private String sellerName;
    @Column(nullable = false)
    private String sellerAddr;
    @Column(nullable = false)
    private String sellerPhoneNum;
    @Column(nullable = false)
    private String sellerBusinessNumber;
    private String authority;
    private Boolean status;         // email 인증 여부

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    @Override
    public String getPassword() {
        return sellerPW;
    }

    @Override
    public String getUsername() {
        return sellerID;
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
