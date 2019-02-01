package org.greenwave.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.csrf().disable()
         .authorizeRequests()
				.antMatchers("/", 
							 "/Form",
							 "/Auth",
							 "/MAP",
						     "/index", 
						     "/doc",
						     "/wiki",
						     "/contact", 
						     "/doc",
						     "/publicArea").permitAll()
				.antMatchers("/admin/*",
							 "/admin/admins/*",
							 "/admin/charts/*",
							 "/admin/data/*",
							 "/admin/map/*",
							 "/admin/users/*"
							).hasAnyRole("ADMIN")
				.anyRequest().authenticated()
         .and()
         .formLogin()
				.loginPage("/login")
				.failureUrl("/login/error")
				.permitAll()
				.defaultSuccessUrl("/dashboard")
				.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
				.permitAll();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, DataSource datasource) throws Exception {

    	auth.jdbcAuthentication()
		.dataSource(datasource)
		.passwordEncoder(passwordEncoder())
		.usersByUsernameQuery("select login as principal, password as credentials, true from account where login = ?")
		.authoritiesByUsernameQuery("select account_id_account as principal, roles_role as role from account_roles a_r join account a on a_r.account_id_account = a.id_account where a.login = ?")
		.rolePrefix("ROLE_");
    }
    
    @Bean
	public static PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
