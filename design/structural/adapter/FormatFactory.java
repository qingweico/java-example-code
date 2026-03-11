package design.structural.adapter;

/**
 * @author zqw
 * @date 2023/2/18
 */
class FormatFactory extends VideoPlayer implements Avi {
    @Override
    public void playAvi() {
        playMp4();
    }
}
