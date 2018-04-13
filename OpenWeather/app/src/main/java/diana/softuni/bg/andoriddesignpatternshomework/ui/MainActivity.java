package diana.softuni.bg.andoriddesignpatternshomework.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import diana.softuni.bg.andoriddesignpatternshomework.R;
import diana.softuni.bg.andoriddesignpatternshomework.api.List;
import diana.softuni.bg.andoriddesignpatternshomework.databinding.ActivityMainBinding;
import diana.softuni.bg.andoriddesignpatternshomework.presenter.CurrentWeatherPresenter;

public class MainActivity extends AppCompatActivity implements CurrentWeatherPresenter.CurrentWeatherViewListener {

    CurrentWeatherPresenter currentWeatherPresenter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        currentWeatherPresenter = new CurrentWeatherPresenter();
        currentWeatherPresenter.setViewListener(this);
        currentWeatherPresenter.setRetrofit();
        // TODO: get Current Location
        currentWeatherPresenter.getTodayAndTomorrowDataAsListIn("42.698334","23.319941"); // Sofia
    }

    @Override
    public void showWeatherData(ArrayList<WeatherModel> weatherList) {

        for (WeatherModel list:weatherList
             ) {
            Log.d("TAG", list.toString());

        }

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        WeatherAdapter adapter = new WeatherAdapter(weatherList);
        binding.recView.setAdapter(adapter);
    }

    @Override
    public void showErrorMessage(int error) {

    }
}