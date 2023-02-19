package design.adapter;

/**
 * @author zqw
 * @date 2023/2/18
 */
class Starter {
    public static void main(String[] args) {
        // 类适配器模式 : 通过继承来实现适配器功能
        Mp4 mp4 = new VideoPlayer();
        mp4.playMp4();

        Avi avi = new FormatFactory();
        avi.playAvi();


        // 对象适配器模式 : 通过组合来实现适配器功能
        Rmvb rmvb = new AdapterFactory(new VideoPlayer());
        rmvb.playRmvb();
    }
}
