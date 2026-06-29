package com.asein.workshop.security;

import com.asein.workshop.common.UserDetailsDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

//    private final PermissionCache permissionCache;

//    private final RoleFeatureRepository roleFeatureRepository;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtService.validateToken(jwt)) {
                String username = jwtService.getUsernameFromJwt(jwt);
                UUID uuid = jwtService.getUserUuid(jwt);
                Long id = jwtService.getId(jwt);

                Collection<? extends GrantedAuthority> roles = jwtService.getRoles(jwt);

//                Set<String> permissions = permissionCache.getPermission(id.toString());


//                if (permissions.isEmpty()) {
//                    permissions = new HashSet<>(roleFeatureRepository.findPermissionByUserId(id));
//                    permissionCache.setPermission(id.toString(), permissions);
//                }

//                Collection<? extends GrantedAuthority> authorities = permissions.stream()
//                        .map(SimpleGrantedAuthority::new)
//                        .toList();

//                var userDetails = new UserDetailsDto(username, id, uuid, roles, authorities);
                var userDetails = new UserDetailsDto(username, id, uuid, roles, null);

//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                authorities
//                        );
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                null
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
