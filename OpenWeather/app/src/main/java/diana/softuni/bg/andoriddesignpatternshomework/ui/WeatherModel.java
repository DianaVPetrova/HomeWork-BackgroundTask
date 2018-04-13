package diana.softuni.bg.andoriddesignpatternshomework.ui;


public class WeatherModel {

    private String time;
    private String description;
    private String temp;
    private String rain;
    private int iconName;

    public WeatherModel(String time, String description, String temp, String rain, int iconName) {
        this.time = time;
        this.description = description;
        this.temp = temp;
        this.rain = rain;
        this.iconName = iconName;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getTemp() {
        return temp;
    }

    public String getRain() {
        return rain;
    }

    public int getIconName() {
        return iconName;
    }

    @Override
    public String toString(){
        return "Description: " + description + " and Rain:" + rain;
    }
}
