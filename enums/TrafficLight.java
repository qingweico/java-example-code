package enums;

/**
 * @author:qiming
 * @date: 2020/10/18
 */
enum Signal {
    RED, YELLOW, GREEN
}
public class TrafficLight {
    Signal color = Signal.RED;

    public void change() {
        switch (color) {
            case RED -> color = Signal.GREEN;
            case GREEN -> color = Signal.YELLOW;
            case YELLOW -> color = Signal.RED;
        }
    }

    @Override
    public String toString() {
        return " The traffic light is " + color;
    }

    public static void main(String[] args) {
        TrafficLight t = new TrafficLight();
        for (int i = 0; i < 7; i++) {
            System.out.println(t);
            t.change();
        }
    }
}
