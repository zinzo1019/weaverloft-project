package com.example.choyoujin.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration // 이 클래스를 빈으로 등록하는데 스프링 설정으로 사용
@EnableWebSecurity // 스프링 시큐리티 기능 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public CustomAuthenticationFailureHandler authenticationFailureHandler; // 로그인 실패 시
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler; // 로그인 성공 시

    // 설정이 겹치면 뒤의 설정이 앞의 설정을 덮어씀
    // 앞 부분은 가장 널은 범위로 허용 범위 설정 -> 점점 범위를 좁힘
    @Override // 오버라이딩
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .antMatchers("/guest/**").permitAll() // guest -> 모두 접근 허용
                .antMatchers("/member/**").hasAnyRole("USER", "ADMIN") // member -> 사용자만 접근 허용
                .antMatchers("/admin/**").hasRole("ADMIN") // admin -> 관리자만 접근 허용
                .antMatchers("/ROLE_GUEST/**").permitAll() // guest 게시판 -> 모두 접근 허용
                .antMatchers("/ROLE_USER/**").hasAnyRole("USER", "ADMIN") // user 게시판 -> 사용자만 접근 허용
                .antMatchers("/ROLE_ADMIN/**").hasRole("ADMIN") // admin 게시판 -> 관리자만 접근 허용
                .antMatchers("/search").permitAll() // admin 게시판 -> 관리자만 접근 허용
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                        .accessDeniedHandler(accessDeniedHandler);

        http.formLogin()
                .loginPage("/loginForm") // 로그인 페이지의 디폴트 경로 설정
                .loginProcessingUrl("/j_spring_security_check") // 해당 url 요청이 들어오면 authProvider로 로그인 정보를 전달하여 로그인 로직이 수행될 수 있도록 설정
                .successHandler(authenticationSuccessHandler) // 커스텀 AuthenticationSuccessHandler 등록
                .failureHandler(authenticationFailureHandler)
                .usernameParameter("email") // "loginForm.jsp"에서 지정한 변수명으로 파라미터명 설정
                .passwordParameter("pw")
                .permitAll(); // 로그인 폼 url 모두 허용

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll(); // 로그아웃 폼 url 모두 허용

        // ssl을 사용하지 않으면 true로 사용
        // @EnableWebSecurity 어노테이션에 의해 CSRF 프로텍션이 활성화
        // 개발 중에는 불편하므로 꺼두자
        http.csrf().disable();
    }

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                // 데이터베이스에 접속해 사용자 조회
                .dataSource(dataSource)
                // 스프링 시큐리티가 사용자 인증에 사용하는 컬럼 이름은 userName과 password다.
                // 따라서 DB와 컬럼명이 다르더라도 as로 컬럼명을 맞추면 된다.
                .usersByUsernameQuery("select email as userName, pw as password, enabled from tb_user where email = ?")
                // 사용자가 있다면 구해온다
                .authoritiesByUsernameQuery("select email as username, role as authority from tb_user where email = ?")
                // 입력한 비밀번호를 암호화해서 데이터베이스의 암호화와 비교
                // 올바른 값인지 검증
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    // 스프링 시큐리티 암호화
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
