package design.observer;

/**
 * @author zqw
 * @date 2022/3/2
 */
public class WeatherStation {
   public static void main(String[] args) {
      WeatherData weatherData = new WeatherData();
      new CurrentConditionsDisplay(weatherData);
      new StatisticsDisplay(weatherData);
      weatherData.setMeasurements(0, 0, 0);
      weatherData.setMeasurements(1, 1, 1);
   }
}
