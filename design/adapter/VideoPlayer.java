package design.adapter;

/**
 * @author zqw
 * @date 2023/2/18
 */
class VideoPlayer implements Mp4{
    @Override
    public void playMp4() {
        System.out.println("playMp4");
    }
}
