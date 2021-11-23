package uz.azizbek.config.jwt;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.azizbek.model.Users;
import uz.azizbek.service.impl.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthService authService;


    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
            return bearer.substring(7);
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token != null && jwtProvider.validateToken(token)) {
            String username = jwtProvider.getLoginFromToken(token);
            Users user = authService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);

    }
}
