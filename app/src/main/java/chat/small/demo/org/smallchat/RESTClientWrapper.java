package chat.small.demo.org.smallchat;

import android.util.Base64;

import chat.small.demo.org.smallchat.security.Oauth2API;
import chat.small.demo.org.smallchat.security.AccessToken;
import chat.small.demo.org.smallchat.security.User;
import chat.small.demo.org.smallchat.security.UserAPI;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTClientWrapper {

	public static final String REST_SERVICE_URI = "http://10.0.2.2:8080/a2r2-API-REST";

	public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=jude&password=123456";

	public static final String QPM_ACCESS_TOKEN = "?access_token=";


	/*
	 * Add HTTP Authorization header, using Basic-Authentication to send
	 * client-credentials.
	 */

    private static final String  plainClientCredentialBase64 = "Basic " + Base64.encodeToString("my-trusted-client:secret".getBytes(), Base64.DEFAULT);

	private static Retrofit.Builder builder;

    private static OkHttpClient.Builder httpClient;

    /*
	 * Send a POST request [on /oauth/token] to get an access-token, which will
	 * then be send with each request.
	 */
	public static AccessToken sendTokenRequest(
            String username,
            String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REST_SERVICE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Oauth2API service = retrofit.create(Oauth2API.class);
        Call<AccessToken> call =  service.getAccessToken(plainClientCredentialBase64, username,password,"password");

        call.enqueue(new Callback<AccessToken>() {

            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
            }
        });



		/*if (map != null) {
			tokenInfo = new AuthTokenInfo();
			tokenInfo.setAccess_token((String) map.get("access_token"));
			tokenInfo.setToken_type((String) map.get("token_type"));
			tokenInfo.setRefresh_token((String) map.get("refresh_token"));
			tokenInfo.setExpires_in((int) map.get("expires_in"));
			tokenInfo.setScope((String) map.get("scope"));
			System.out.println(tokenInfo);
		} else {
			System.out.println("No user exist----------");
		}*/

/*
		return tokenInfo;

*/
        return null;
	}


	/*
	 * Send a GET request to get a specific user.
	 */
	private static void getUser(String username, String accessToken) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REST_SERVICE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserAPI service = retrofit.create(UserAPI.class);
        Call<User> call =  service.getUser(plainClientCredentialBase64,username,accessToken);

        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });


    }

	/*
	 * Send a POST request to create a new user.
	 */
	public static String createUser(String username, String password, String name, String surname) {

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(REST_SERVICE_URI)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		User user = new User();
		user.setUsername(username);
		user.setName(name);
		user.setSurname(surname);
		user.setPassword(password);

        UserAPI service = retrofit.create(UserAPI.class);
        Call<User> call =  service.createUser(user);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });


        
		return user.getUsername();
	}


}