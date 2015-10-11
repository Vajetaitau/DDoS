package ufc.config;

import javax.sql.DataSource;

import ufc.constants.Configuration_M;
import ufc.constants.Urls;
import ufc.spring.AuthenticationTokenProcessingFilter;
import ufc.spring.CustomUserDetailsService;
import ufc.spring.SuccessHandler;
import ufc.spring.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    private static final String USERS_BY_USERNAME_QUERY =
            "select username, password, true from users where username = ?";
    private static final String AUTHORITIES_BY_USERNAME_QUERY =
            "select username, authority from authorities where username = ?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth
                .userDetailsService(userDetailsService())
            .and()
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USERS_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
        // @formatter:on
    }

    //
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .addFilterBefore(authenticationTokenProcessingFilter(), BasicAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint())
            .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(Configuration_M.ACTION.concat(Urls.AUTHENTICATE)).permitAll()
                .antMatchers(Configuration_M.ACTION.concat(Urls.DDOS).concat("/**")).permitAll()
                .antMatchers("/*").permitAll()
                .antMatchers(Configuration_M.PUBLIC_HTML).permitAll()
                .antMatchers(Configuration_M.HTML_TEMPLATES).permitAll()
//                .antMatchers(Urls.AUTHENTICATE.concat("**")).permitAll()
                .antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin().disable()
//                .loginPage("/login.html").permitAll()
//                .loginProcessingUrl("/login").disable()
//                .successHandler(successHandler())
//            .and()
                .logout()
                .logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/")
                .deleteCookies(Configuration_M.AUTH_TOKEN);
        // @formatter:on
    }

    //
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public UnauthorizedEntryPoint unauthorizedEntryPoint() {
        return new UnauthorizedEntryPoint();
    }

    @Bean
    public AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter() {
        return new AuthenticationTokenProcessingFilter(userDetailsService());
    }

    @Bean
    public SuccessHandler successHandler() {
        return new SuccessHandler();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
