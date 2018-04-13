package diana.softuni.bg.andoriddesignpatternshomework.api;

import diana.softuni.bg.andoriddesignpatternshomework.api.Main;
import diana.softuni.bg.andoriddesignpatternshomework.api.Weather;

public class CurrentWeather {

    private Weather[] weather;
    private Main main;


    public String getDescription(){
        return weather[0].getDescription();
    }

    public Double getTemp(){
        return main.temp;
    }

    public Double getPressure(){
        return main.pressure;
    }

    @Override
    public String toString(){
        return "Curernt weathe " + getDescription() + " temp " + getTemp();
    }
}
