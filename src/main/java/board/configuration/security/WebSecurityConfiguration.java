package board.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import board.api.advice.ExceptionAdvice;
import board.api.advice.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
				.csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은
																							// 필요없으므로 생성안함.
				.and()
				.cors()
				.and().authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.antMatchers("/*/signin", "/*/signup", "/*/signout", "/*/tokenreissue", "/*/user/checkid/*").permitAll() // 가입 및 인증 주소는 누구나 접근가능
				.antMatchers(HttpMethod.POST, "/*/tokenreissue").permitAll() // 가입 및 인증 주소는 누구나 접근가능
				.antMatchers(HttpMethod.GET, "/*/board/**").permitAll() // hellowworld로 시작하는 GET요청 리소스는 누구나 접근가능
//				.antMatchers(HttpMethod.PUT, "/*/board/**").permitAll() // hellowworld로 시작하는 GET요청 리소스는 누구나 접근가능
//				.antMatchers(HttpMethod.POST, "/*/board/**").permitAll() // hellowworld로 시작하는 GET요청 리소스는 누구나 접근가능
				.anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
//				.anyRequest().permitAll()
				.and().addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),  // jwt token 필터를 id/password 인증 필터 전에 넣는다
						UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
//				.accessDeniedHandler((request, response, accessDeniedException) -> {
//	                AccessDeniedHandler defaultAccessDeniedHandler = new board.api.advice.exception.AccessDeniedHandlerImpl();
//	                defaultAccessDeniedHandler.handle(request, response, accessDeniedException); // handle the custom acessDenied class here
//	            });
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				.accessDeniedHandler(new board.api.advice.exception.AccessDeniedHandlerImpl());


	}

	@Override // ignore check swagger resource
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**",
				"/swagger/**");

	}
	
}