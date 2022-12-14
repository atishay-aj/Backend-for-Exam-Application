package com.exam.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exam.service.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter  {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private jwtUtil jwtUtil;
	
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

//		1. get token
		String requestToken = request.getHeader("Authorization");

		System.out.println(requestToken);

		String username = null;

		String token = null;
		if (requestToken != null && requestToken.startsWith("Bearer")) {

			token = requestToken.substring(7);
			try {
				username = this.jwtUtil.extractUsername(token);

			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				System.out.println("Unable to get jwt token");
			} catch (ExpiredJwtException e) {
				// TODO: handle exception
				System.out.println("Jwt token expired");
			} catch (MalformedJwtException e) {
				// TODO: handle exception
				System.out.println("Invalid jwt");
			}
		} else {
			System.out.println("Jwt doesn't begin with bearer");
		}

//		once we get token, now validate
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (this.jwtUtil.validateToken(token, userDetails)) {
//				shi chal rha hai
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {

				System.out.println("Invalid jwt token");
			}

		} else {

			System.out.println("username is null or context is not null");
		}

		filterChain.doFilter(request, response);
	}


	
}
