package com.asein.workshop.security;

import com.asein.workshop.user.User;
import com.asein.workshop.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PermissionCache permissionCache;

    public CustomUserDetailsService(UserRepository userRepository, PermissionCache permissionCache) {
        this.userRepository = userRepository;
        this.permissionCache = permissionCache;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Invalid credential"));

//        List<String> roles = userRoleRepository.findRoleCodesByUserId(user.getId());
        List<String> roles = new ArrayList<>();
        roles.add(user.getRole());
//        List<String> perms = roleFeatureRepository.findPermissionByUserId(user.getId());

        CustomUserDetails userDetails =
                new CustomUserDetails(
                        user.getId(), user.getUsername(), user.getPassword(),
                        roles.stream().map(SimpleGrantedAuthority::new).toList(),
                        null
//                        perms.stream().map(SimpleGrantedAuthority::new).toList()
                );

//        permissionCache.setPermission(
//                user.getId().toString(),
//                new HashSet<>(null)
//        );

        return userDetails;
    }
}
