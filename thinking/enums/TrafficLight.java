package thinking.enums;

import cn.qingweico.constants.Constants;

/**
 * @author zqw
 * @date 2020/10/18
 */
public class TrafficLight {
    Signal color = Signal.RED;

    public void change() {
        switch (color) {
            case RED:
                color = Signal.GREEN;
                break;
            case GREEN:
                color = Signal.YELLOW;
                break;
            case YELLOW:
                color = Signal.RED;
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return " The traffic light is " + color;
    }

    public static void main(String[] args) {
        TrafficLight t = new TrafficLight();
        for (int i = 0; i < Constants.FIVE; i++) {
            System.out.println(t);
            t.change();
        }
    }
}
