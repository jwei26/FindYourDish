package jwei26.filter;

import io.jsonwebtoken.Claims;
import jwei26.model.User;
import jwei26.service.JWTService;
import jwei26.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    private static final Set<String> ALLOWED_PATH = new HashSet<>(Arrays.asList("", "/login", "logout", "register"));
    private static final Set<String> IGNORED_PATH = new HashSet<>(Arrays.asList("/auth"));
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Start to do authorization");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        int statusCode = authorization(req);
        if(statusCode == HttpServletResponse.SC_ACCEPTED) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse)servletResponse).sendError(statusCode);
        }
    }

    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        if (IGNORED_PATH.contains(uri)) {
            return HttpServletResponse.SC_ACCEPTED;
        }

        try {
            String token = req.getHeader("Authorization").replaceAll("^(.*?) ", "");
            if (token == null || token.isEmpty())
                return statusCode;

            Claims claims = jwtService.decryptToken(token);
            logger.info("===== after parsing JWT token, claims.getId()={}", claims.getId());
            if (claims.getId() != null) {
                User u = userService.getUserById(Long.valueOf(claims.getId()));
                if (u != null) {
                    return HttpServletResponse.SC_ACCEPTED;
                }
            }
        } catch (Exception e) {
            logger.info("Cannot get token");
        }
        return statusCode;
    }
}
