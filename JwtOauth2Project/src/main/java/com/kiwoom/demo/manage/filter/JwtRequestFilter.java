package com.kiwoom.demo.manage.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kiwoom.demo.manage.exception.base.CommonException;
import com.kiwoom.demo.manage.jwt.JwtComponent;
import com.kiwoom.demo.manage.jwt.JwtComponent.TOKEN_TYPE;
import com.kiwoom.demo.manage.security.service.CustomUserDetailService;


/*
 * JWT 처리 필터
 * */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired 
	CustomUserDetailService customUserService;
	
	@Autowired
	private JwtComponent jwtComponent;
	
	@Value("${jwt.get.access.token.url}")
	private String accessTokenUrl;
	
	/*
	 * accessToken, refreshToken 이 유효한지 체크
	 * 
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws CommonException
	 */
	private void run(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws CommonException {
		
		// 헤더에서 인증값 꺼냄
		final String authHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwt = null;
		
		// 기본값은 access token으로 세팅
		TOKEN_TYPE tokenType = TOKEN_TYPE.ACCESS_TOKEN;
		
		// 인증값이 있고, Bearer 헤더가 았을때 처리 
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			
			// 토큰값만 추출함
			jwt = authHeader.substring(7);
			
			// refresh token으로 access token 재발급인지 확인
			String requestURI = request.getRequestURI();
			if(this.accessTokenUrl.equalsIgnoreCase(requestURI)) {
				tokenType = TOKEN_TYPE.REFRESH_TOKEN;
			}
			
			// 토큰값과 타입으로 사용자아이디를 조회함
			username = this.jwtComponent.extractUsername(jwt, tokenType);
		}
		
		// 사용자아이디가 있고, Security에 인증값이 없을때 처리
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			// 사용자 아이디로 해당 정보가 있는지 확인함
			UserDetails userDetails = this.customUserService.loadUserByUsername(username);
			
			// 토큰의 유효성을 확인
			if(this.jwtComponent.validateToken(jwt, userDetails, tokenType)) {
				
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Security에 인증값 추가
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}
		
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			// filter를 태우기 전에 accessToken, refreshToken 이 유효한지 체크 
			this.run(request, response, filterChain);
		} catch (CommonException e) {
			e.printStackTrace();
		}
		
		filterChain.doFilter(request, response);
	}
	
}
