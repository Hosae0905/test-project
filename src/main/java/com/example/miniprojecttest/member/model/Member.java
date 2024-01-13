package com.example.miniprojecttest.member.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer consumerIdx;
    @Column(nullable = false)
    private String consumerID;
    @Column(nullable = false)
    private String consumerPW;
    @Column(nullable = false)
    private String consumerName;
    @Column(nullable = false)
    private String consumerAddr;
    @Column(nullable = false)
    private String consunmerPhoneNum;
    private String authority;
    private Boolean socialLogin;
    private Boolean status;         // email 인증 여부

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }


    @Override
    public String getPassword() {
        return consumerPW;
    }

    @Override
    public String getUsername() {
        return consumerID;
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
