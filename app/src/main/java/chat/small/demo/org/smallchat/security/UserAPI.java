package chat.small.demo.org.smallchat.security;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPI {

  @Headers( {"Content-Type: application/json", "Accept: application/json" } )
  @POST("/{app}/user")
  Call<Void> createUser(@Path("app") String app,
                        @Body User user);


  @GET("/{app}/user/{username}")
  Call<User> getUser(@Header("Authorization") String authorization,
                     @Header("Accept") String acceptType,
                     @Path("app") String app,
                     @Path("username") String username,
                     @Query("access_token") String access_token);

}