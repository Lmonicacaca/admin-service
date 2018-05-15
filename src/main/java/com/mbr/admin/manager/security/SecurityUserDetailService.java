package com.mbr.admin.manager.security;

import com.mbr.admin.domain.system.vo.SysUsersVo;
import com.mbr.admin.manager.system.SysUsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class SecurityUserDetailService implements UserDetailsService{

    @Autowired
    private SysUsersManager sysUsersManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUsersVo sysUsersVo = sysUsersManager.loadByUsername(username);
        if(sysUsersVo==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authoritie = new SimpleGrantedAuthority(sysUsersVo.getRoles());
        authorities.add(authoritie);
        SecurityUserDetails securityUserDetails = new SecurityUserDetails(authorities, sysUsersVo.getPassword(), sysUsersVo.getUsername(), sysUsersVo.getAccountNonExpired(), sysUsersVo.getAccountNonLocked(),  sysUsersVo.getCredentialsNonExpired(), sysUsersVo.getEnabled(),sysUsersVo.getId());
        return securityUserDetails;
    }
}
