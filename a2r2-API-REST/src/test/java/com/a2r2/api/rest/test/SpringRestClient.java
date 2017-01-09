package com.a2r2.api.rest.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.a2r2.api.rest.model.User;


public class SpringRestClient {

	public static final String REST_SERVICE_URI = "http://localhost:8080/a2r2-API-REST";

	public static final String AUTH_SERVER_URI = "http://localhost:8080/a2r2-API-REST/oauth/token";

	public static final String QPM_ACCESS_TOKEN = "?access_token=";

	/*
	 * Prepare HTTP Headers.
	 */
	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	/*
	 * Add HTTP Authorization header, using Basic-Authentication to send
	 * client-credentials.
	 */
	private static HttpHeaders getHeadersWithClientCredentials() {
		String plainClientCredentials = "my-trusted-client:secret";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

		HttpHeaders headers = getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}

	/*
	 * Send a POST request [on /oauth/token] to get an access-token, which will
	 * then be send with each request.
	 */
	@SuppressWarnings({ "unchecked" })
	private static AuthTokenInfo sendTokenRequest(String username, String password) {
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);

		final String QPM_PASSWORD_GRANT =  String.format("?grant_type=password&username=%s&password=%s",username, password);

		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI + QPM_PASSWORD_GRANT, HttpMethod.POST,
				request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		AuthTokenInfo tokenInfo = null;

		if (map != null) {
			tokenInfo = new AuthTokenInfo();
			tokenInfo.setAccess_token((String) map.get("access_token"));
			tokenInfo.setToken_type((String) map.get("token_type"));
			tokenInfo.setRefresh_token((String) map.get("refresh_token"));
			tokenInfo.setExpires_in((int) map.get("expires_in"));
			tokenInfo.setScope((String) map.get("scope"));
			System.out.println(tokenInfo);
			// System.out.println("access_token ="+map.get("access_token")+",
			// token_type="+map.get("token_type")+",
			// refresh_token="+map.get("refresh_token")
			// +", expires_in="+map.get("expires_in")+",
			// scope="+map.get("scope"));;
		} else {
			System.out.println("No user exist----------");

		}
		return tokenInfo;
	}

	/*
	 * Send a GET request to get list of all users.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void listAllUsers(AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");

		System.out.println("\nTesting listAllUsers API-----------");
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		ResponseEntity<List> response = restTemplate.exchange(
				REST_SERVICE_URI + "/user/" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), HttpMethod.GET, request,
				List.class);
		List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>) response.getBody();

		if (usersMap != null) {
			for (LinkedHashMap<String, Object> map : usersMap) {
				System.out.println("User : id=" + map.get("id") + ", Name=" + map.get("name") + ", Age="
						+ map.get("age") + ", Salary=" + map.get("salary"));
				;
			}
		} else {
			System.out.println("No user exist----------");
		}
	}

	/*
	 * Send a GET request to get a specific user.
	 */
	private static void getUser(AuthTokenInfo tokenInfo, User user) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		System.out.println("\nTesting getUser API----------");
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		ResponseEntity<User> response = restTemplate.exchange(
				REST_SERVICE_URI + "/user/" + user.getUsername() + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), HttpMethod.GET,
				request, User.class);
		User responseUser = response.getBody();
		System.out.println(responseUser);
	}

	/*
	 * Send a POST request to create a new user.
	 */
	private static User createUser() {
		
		System.out.println("\nTesting create User API----------");
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		User user = UserBuilder.create();
		HttpEntity<Object> request = new HttpEntity<Object>(user, getHeadersWithClientCredentials());
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/user", request, User.class);
		System.out.println("Location : " + uri.toASCIIString());
		return user;
	}

	/*
	 * Send a PUT request to update an existing user.
	 */
	private static void updateUser(String username, AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		System.out.println("\nTesting update User API----------");
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		User user = UserBuilder.create(username);
		HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
		ResponseEntity<User> response = restTemplate.exchange(
				REST_SERVICE_URI + "/user/" + username + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), HttpMethod.PUT,
				request, User.class);
		System.out.println(response.getBody());
	}

	/*
	 * Send a DELETE request to delete a specific user.
	 */
	private static void deleteUser(String username, AuthTokenInfo tokenInfo) {
		Assert.notNull(tokenInfo, "Authenticate first please......");
		System.out.println("\nTesting delete User API----------");
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new LoggingRequestInterceptor());
		restTemplate.setInterceptors(interceptors);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		restTemplate.exchange(REST_SERVICE_URI + "/user/" + username + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
				HttpMethod.DELETE, request, User.class);
	}

	public static void main(String args[]) {
		User user = createUser();
	
		AuthTokenInfo tokenInfo = sendTokenRequest(user.getUsername(), user.getPassword());
		listAllUsers(tokenInfo);

		getUser(tokenInfo, user);

		updateUser(user.getUsername(), tokenInfo);
		listAllUsers(tokenInfo);

		deleteUser(user.getUsername(), tokenInfo);
		listAllUsers(tokenInfo);

	}
}