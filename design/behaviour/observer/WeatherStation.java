package design.behaviour.observer;

/**
 * @author zqw
 * @date 2022/3/2
 */
public class WeatherStation {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        var ccd = new CurrentConditionsDisplay(weatherData);
        var sd = new StatisticsDisplay(weatherData);
        weatherData.setMeasurements(0, 0, 0);
        weatherData.setMeasurements(1, 1, 1);
        System.out.println(weatherData);
        ccd.cancel(weatherData);
        System.out.println(weatherData);
        sd.cancel(weatherData);
        System.out.println(weatherData);
    }
}
