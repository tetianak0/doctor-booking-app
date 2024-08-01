package doctorBookingApp.security.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                String managerName = jwtTokenProvider.getManagerNameFromJwt(jwt);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(managerName);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            response.sendError(401, "Error : " + e.getMessage());
            logger.error("could not set user authentication in the security context", e);
        }

        filterChain.doFilter(request,response);
    }


    private String getJwtFromRequest(HttpServletRequest request){
        String headerAuthorization = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuthorization) && headerAuthorization.startsWith("Bearer ")){
            return headerAuthorization.substring(7);
        }
        return null;
    }
}
