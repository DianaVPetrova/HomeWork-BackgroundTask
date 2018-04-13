package diana.softuni.bg.andoriddesignpatternshomework.api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    public static final String strURL = "http://api.openweathermap.org/";

    @GET("/data/2.5/weather")
    Call<CurrentWeather> getCurrentWeather(@Query("q") String q, @Query("appid") String key);

    String API_KEY = "7694df772bdf5377ebbbe59109b6bdb2";
    @GET("/data/2.5/forecast")
    Call<ForecastWeather> getFutures(@Query("lat") String one, @Query("lon") String two,
                                     @Query("appid") String key);
}
