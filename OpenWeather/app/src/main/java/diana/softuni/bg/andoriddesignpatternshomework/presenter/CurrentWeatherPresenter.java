package diana.softuni.bg.andoriddesignpatternshomework.presenter;


import android.util.Log;
import java.util.ArrayList;

import diana.softuni.bg.andoriddesignpatternshomework.R;
import diana.softuni.bg.andoriddesignpatternshomework.Utils;
import diana.softuni.bg.andoriddesignpatternshomework.api.APIService;
import diana.softuni.bg.andoriddesignpatternshomework.api.CurrentWeather;
import diana.softuni.bg.andoriddesignpatternshomework.api.ForecastWeather;
import diana.softuni.bg.andoriddesignpatternshomework.api.List;
import diana.softuni.bg.andoriddesignpatternshomework.ui.WeatherModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentWeatherPresenter {

    private CurrentWeatherViewListener viewListener;
    private APIService service;

    public void setViewListener(CurrentWeatherViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void setRetrofit(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.strURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIService.class);
    }



   public void getTodayAndTomorrowDataAsListIn(String lat, String lon){

        final ArrayList<WeatherModel> weatherList = new ArrayList<>();

        service.getFutures(lat,lon, APIService.API_KEY).enqueue(new Callback<ForecastWeather>() {
            @Override
            public void onResponse(Call<ForecastWeather> call, Response<ForecastWeather> response) {
                ForecastWeather forecastWeather = response.body();

                for (List list: forecastWeather.getLists()) {

                  if (Utils.isTodayOrTomorrow(list.getDt())){

                       String time = Utils.getTimeOfData(list.getDt());
                       String description = list.getWeather().getDescription();
                       String temp = String.valueOf(list.getMain().getTemp());
                       String rain = "0";
                        if(list.getRain()!=null)
                         rain = list.getRain().getH3();
                       int iconName = getCurrentIcon(list.getWeather().getIcon());

                      WeatherModel weatherModel = new WeatherModel(time,description,temp,rain,iconName);
                      weatherList.add(weatherModel);
                    }
                }

                if (!weatherList.isEmpty()) {
                    viewListener.showWeatherData(weatherList);
                }
                 else {
                    viewListener.showErrorMessage(R.string.error_no_data);
                }
            }

            @Override
            public void onFailure(Call<ForecastWeather> call, Throwable t) {
                Log.d("TAG_WEATHER", t.getLocalizedMessage()+" error");
                viewListener.showErrorMessage(R.string.error_no_responde);
            }
        });

    }

    private int getCurrentIcon (String icon){

        switch (icon){
            case "01d":
                return  R.string.wi_day_sunny;
            case "02d":
               return R.string.wi_cloudy_gusts;
            case "03d":
               return R.string.wi_cloud_down;
            case "04d":
               return R.string.wi_cloudy;
            case "04n":
               return R.string.wi_night_cloudy;
            case "10d":
               return R.string.wi_day_rain_mix;
            case "11d":
               return R.string.wi_day_thunderstorm;
            case "13d":
               return R.string.wi_day_snow;
            case "01n":
               return R.string.wi_night_clear;
            case "02n":
               return R.string.wi_night_cloudy;
            case "03n":
               return R.string.wi_night_cloudy_gusts;
            case "10n":
               return R.string.wi_night_cloudy_gusts;
            case "11n":
               return R.string.wi_night_rain;
            case "13n":
               return R.string.wi_night_snow;

            default: return R.string.wi_day_sunny;
        }
        
    }

    public void getCurrentWeatherFor(String city){
           service.getCurrentWeather(city , APIService.API_KEY).enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather currentWeather = response.body();
                Log.d("TAG_WEATHER", currentWeather.toString());
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

            }
        });

    }
    public interface CurrentWeatherViewListener {

         void showWeatherData(ArrayList<WeatherModel> weatherList);

        void showErrorMessage(int error);
    }
}

