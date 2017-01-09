package chat.small.demo.org.smallchat;

import android.util.Base64;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chat.small.demo.org.smallchat.security.Oauth2API;
import chat.small.demo.org.smallchat.security.AccessToken;
import chat.small.demo.org.smallchat.security.User;
import chat.small.demo.org.smallchat.security.UserAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTClientWrapper {

	/*public static final String REST_SERVICE_URI = "http://10.0.2.2:8080/a2r2-API-REST/";*/

/*
	public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=jude&password=123456";
*/

	/*public static final String QPM_ACCESS_TOKEN = "?access_token=";*
	/
	 */
    private static final String ACCEPT_TYPE_JSON = "application/json";
    private static final String REST_APP_PATH = "a2r2-API-REST";
    private static final String DOMAIN = "http://10.0.2.2:8080/";


/*    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://10.0.2.2:8080/a2r2-API-REST/");*/

	/*
	 * Add HTTP Authorization header, using Basic-Authentication to send
	 * client-credentials.
	 */
    private static final String  plainClientCredentialBase64 = "Basic " + Base64.encodeToString("my-trusted-client:secret".getBytes(), Base64.NO_WRAP);

    private static OkHttpClient.Builder httpClient;

    /*
	 * Send a POST request [on /oauth/token] to get an access-token, which will
	 * then be send with each request.
	 */
	public static AccessToken sendTokenRequest(
            String username,
            String password) throws IOException {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit =
                new Retrofit.Builder()
                        .client(client)
                        .baseUrl(DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


        Oauth2API service = retrofit.create(Oauth2API.class);
        Call<AccessToken> call =  service.getAccessToken(plainClientCredentialBase64, ACCEPT_TYPE_JSON, REST_APP_PATH, username,password,"password");

        Response<AccessToken> execute = call.execute();
        if (execute.isSuccessful()){
            return execute.body();
        }

        return null;
	}


	/*
	 * Send a GET request to get a specific user.
	 */
	private static User getCurrentUser(String username, String accessToken) throws IOException {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserAPI service = retrofit.create(UserAPI.class);
        Call<User> call =  service.getUser(plainClientCredentialBase64,
                ACCEPT_TYPE_JSON,
                REST_APP_PATH,
                username,
                accessToken);

        User userResult = call.execute().body();

        return userResult;

    }

	/*
	 * Send a POST request to create a new user.
	 */
	public static String createUser(String username, String password, String name, String surname) throws IOException {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit =
                new Retrofit.Builder()
                        .client(client)
                        .baseUrl(DOMAIN)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

		User user = new User();
		user.setUsername(username);
		user.setName(name);
		user.setSurname(surname);
		user.setPassword(password);

        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        user.setAuthorities(authorities);

        UserAPI service = retrofit.create(UserAPI.class);
        Call<Void> call =  service.createUser(REST_APP_PATH,user);

        Response<Void> execute = call.execute();
        if (execute.isSuccessful()){
            return username;
        }

        return null;
	}


}