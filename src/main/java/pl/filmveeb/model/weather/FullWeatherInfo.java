package pl.filmveeb.model.weather;

public class FullWeatherInfo {

    private String name;
    private MainWeatherInfo main;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MainWeatherInfo getMain() {
        return main;
    }

    public void setMain(MainWeatherInfo main) {
        this.main = main;
    }
}
