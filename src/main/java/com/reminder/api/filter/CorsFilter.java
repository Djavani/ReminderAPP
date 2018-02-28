package com.reminder.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Component
public class CorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = getHttpRequest(req);
		HttpServletResponse response = getHttpResponse(res);

		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Cache-Control, Pragma, Authorization");
		
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpStatus.OK.value());
			return;
		}
		
		chain.doFilter(req, res);
	}

	@SuppressFBWarnings(value="BC_UNCONFIRMED_CAST", justification="É garantido que esta requisição sempre será uma HttpRequest")
	private HttpServletRequest getHttpRequest(ServletRequest req) {
		return (HttpServletRequest) req;
	}
	
	@SuppressFBWarnings(value="BC_UNCONFIRMED_CAST", justification="É garantido que esta resposta sempre será uma HttpResponse")
	private HttpServletResponse getHttpResponse(ServletResponse res) {
		return (HttpServletResponse) res;
	}

	@Override
	public void init(FilterConfig filterConfig) {
		// No-op
	}

	@Override
	public void destroy() {
		// No-op
	}
}