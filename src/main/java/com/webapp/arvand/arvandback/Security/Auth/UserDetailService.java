package com.webapp.arvand.arvandback.Security.Auth;

import com.webapp.arvand.arvandback.Entity.UserEntity;
import com.webapp.arvand.arvandback.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUserNameWithRoles(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        if(userEntity.getRoles() == null || userEntity.getRoles().isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> authorities =
                userEntity.getRoles().stream()
                        .map(role ->
                                role.getGroup().toUpperCase()
                                        + "_"
                                        + role.getRoleName().toUpperCase()
                        )
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        return new UserDetail(
                userEntity.getId(),
                userEntity.getUserName(),
                userEntity.getPass(),
                authorities
        );
    }


}
