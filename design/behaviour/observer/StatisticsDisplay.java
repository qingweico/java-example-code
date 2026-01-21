package design.behaviour.observer;

/**
 * @author zqw
 * @date 2022/3/2
 */
public class StatisticsDisplay implements Observer {
    public StatisticsDisplay(Subject weatherData) {
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        System.out.println("StatisticsDisplay.update: " + temp + " " + humidity + " " + pressure);
    }

    @Override
    public void cancel(Subject weatherData) {
        weatherData.removeObserver(this);
    }
}
