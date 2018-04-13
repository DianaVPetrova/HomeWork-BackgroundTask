package diana.softuni.bg.andoriddesignpatternshomework.api;

public class List {

    private Main main;
    private Weather[] weather = null;
    private Integer dt;
    private Temp temp;
    private Rain rain;


    public Main getMain() {
        return main;
    }


    public Weather getWeather() {

        return weather[0];
    }

    public Integer getDt() {
        return dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public Rain getRain() {
        return rain;
    }

}
