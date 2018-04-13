package diana.softuni.bg.andoriddesignpatternshomework.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.view.FocusFinder;
import android.view.View;

import diana.softuni.bg.andoriddesignpatternshomework.databinding.ForecastsItemBinding;

public class ForecastViewHolder extends BaseViewHolder<WeatherModel> {

    private final static String PATH_TO_WEATHER_FONT = "fonts/weather.ttf";
    ForecastsItemBinding binding;

    public ForecastViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void setData(WeatherModel item) {
        // description
        binding.txtDescription.setText(item.getDescription());
        // temp
        binding.txtTemp.setText("Temp: "+item.getTemp());
        // rain
        binding.txtRain.setText("Rain: "+item.getRain() + "mm");
        //icon

        Typeface weatherFont = Typeface.createFromAsset(itemView.getContext().getAssets(), PATH_TO_WEATHER_FONT);
         binding.weatherIconText.setTypeface(weatherFont);

        binding.weatherIconText.setText(item.getIconName());
        //time
        binding.txtTime.setText(item.getTime());
    }
}
