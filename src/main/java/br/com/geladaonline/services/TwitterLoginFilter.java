package br.com.geladaonline.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glassfish.jersey.client.oauth1.AccessToken;

@WebFilter("/services/twitter/*")
public class TwitterLoginFilter implements Filter {

	public static final String OAUTH_VERIFIER = "oauth_verifier";
	public static final String OAUTH_TOKEN_FIELD = "oauth_token";
	public static final String ACCESS_TOKEN_KEY = "AccessToken";
	public static final String TOKEN_COOKIE = "TwitterAccessToken";
	public static final String TOKEN_COOKIE_SECRET = "TwitterAccessTokenSecret";
	public static final String EMPTY_COOKIE = "empty";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		HttpServletResponse httpServletResponse = (HttpServletResponse) resp;

		AccessToken accessToken = recuperaAccessTokenDosCookies(httpServletRequest);

		if (accessToken == null) {
			// Valores estarão na requisição se for um callback
			String verifier = req.getParameter(OAUTH_VERIFIER);
			String token = req.getParameter(OAUTH_TOKEN_FIELD);

			if (verifier != null && token != null) {
				accessToken = TwitterOAuthFlowService.verify(token, verifier, httpServletRequest);
				ajustaCookieNaResposta(httpServletResponse, accessToken);

			} else {
				// Redireciona o usuário para URL fornecida pelo Twitter
				String twitterUri = TwitterOAuthFlowService.init(httpServletRequest);
				httpServletResponse.sendRedirect(twitterUri);
				return;
			}
		}

		// Ajusta o access token na requisição para q possamos utilizar
		// posteriormente
		httpServletRequest.setAttribute(ACCESS_TOKEN_KEY, accessToken);
		filterChain.doFilter(httpServletRequest, httpServletResponse);

	}

	private void ajustaCookieNaResposta(HttpServletResponse resp, AccessToken accessToken) {
		Cookie accessTokenCookie = new Cookie(TOKEN_COOKIE, accessToken.getToken());
		accessTokenCookie.setPath("/");

		Cookie accessTokenCookieSecret = new Cookie(TOKEN_COOKIE_SECRET, accessToken.getAccessTokenSecret());
		accessTokenCookieSecret.setPath("/");

		resp.addCookie(accessTokenCookie);
		resp.addCookie(accessTokenCookieSecret);

	}

	protected AccessToken recuperaAccessTokenDosCookies(HttpServletRequest req) {
		String accessToken = null;
		String accessTokenSecret = null;
		
		Cookie[] cookies = req.getCookies();
		if(cookies != null)
		for (Cookie cookie : cookies) {
			if (!cookie.getValue().equals(EMPTY_COOKIE)) {
				if (cookie.getName().equals(TOKEN_COOKIE)) {
					accessToken = cookie.getValue();
				} else if (cookie.getName().equals(TOKEN_COOKIE_SECRET)) {
					accessTokenSecret = cookie.getValue();
				}
			}
		}

		if (accessToken != null & accessTokenSecret != null) {
			return new AccessToken(accessToken, accessTokenSecret);
		}

		return null;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
