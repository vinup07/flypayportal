package com.flypay.flypayportal.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCFilter extends OncePerRequestFilter {

	@Value("${service.name}")
	private String serviceName;

	@Value("${app.name}")
	private String appName;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// Set the MDC context here
			MDC.put("requestId", UUID.randomUUID().toString());
			MDC.put("traceId", UUID.randomUUID().toString());
			MDC.put("correlationId", "CORR-" + UUID.randomUUID().toString());
			MDC.put("serviceName", serviceName);
	        MDC.put("appName", appName);
	        log.info("MDC initialized with serviceName = {}  and appName = {}", serviceName, appName);
			filterChain.doFilter(request, response);
		} finally {
			// Clear the MDC context after the request is processed
			MDC.clear();
		}

	}
}
