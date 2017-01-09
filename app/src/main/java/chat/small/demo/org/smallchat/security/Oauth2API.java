package chat.small.demo.org.smallchat.security;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Oauth2API {

/*
    @FormUrlEncoded
    @POST("/oauth/token")
    Call<AccessToken> getNewAccessToken(
            @Field("code") String code,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("/oauth/token")
    Call<AccessToken> getRefreshAccessToken(
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("grant_type") String grantType);
*/

    @FormUrlEncoded
    @POST("{app}/oauth/token")
    Call<AccessToken> getAccessToken(@Header("Authorization") String authorization,
                                     @Header("Accept") String acceptType,
                                     @Path("app") String app,
                               @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grantType);


  }