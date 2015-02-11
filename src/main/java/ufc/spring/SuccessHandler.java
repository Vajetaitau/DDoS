package ufc.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
//        HttpSession session = httpServletRequest.getSession();
        for (String headerName: httpServletResponse.getHeaderNames()) {
            String header = httpServletResponse.getHeader(headerName);
        }
        httpServletResponse.sendRedirect("index.html");
    }
}
