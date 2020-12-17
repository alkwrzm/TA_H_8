package apap.sipayroll.security;


import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/gaji/add").hasAnyAuthority("Staff Payroll","Kepala Departemen HR")
                .antMatchers("/gaji/update/**").hasAnyAuthority("Staff Payroll","Kepala Departemen HR")
                .antMatchers("/gaji/delete/**").hasAnyAuthority("Staff Payroll","Kepala Departemen HR")
                .antMatchers("/gaji/status/**").hasAnyAuthority("Kepala Departemen HR")
                .antMatchers("/lembur/add/**").hasAnyAuthority("Karyawan")
                .antMatchers("/lembur/change/**").hasAnyAuthority("Staff Payroll","Kepala Departemen HR", "Karyawan")
                .antMatchers("/lembur/delete/**").hasAnyAuthority("Staff Payroll","Kepala Departemen HR", "Karyawan")
                .antMatchers("/lembur/view/**").hasAnyAuthority("Staff Payroll","Kepala Departemen HR", "Karyawan")
                .antMatchers("/bonus").hasAnyAuthority("Kepala Bagian Pelatihan", "Kepala Departemen HR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll()
                .and()
                .cors().and().csrf().disable()
        ;
    }

    @Bean
    public BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}

    @Autowired
    public void cofigureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder())
                .withUser("user1").password(encoder().encode("user1"))

                .roles("Karyawan");
    }
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws  Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }


}
