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

  @Headers( "Content-Type: application/json" )
  @POST("/user")
  Call<User> createUser(@Body User user);


  @GET("/user/{username}")
  Call<User> getUser(@Header("Authorization") String authorization, @Path("username") String username,
                     @Query("access_token") String access_token);

}