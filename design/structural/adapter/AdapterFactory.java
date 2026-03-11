package design.structural.adapter;

import lombok.AllArgsConstructor;

/**
 * @author zqw
 * @date 2023/2/18
 */
@AllArgsConstructor
class AdapterFactory implements Rmvb {
    private Mp4 mp4;

    @Override
    public void playRmvb() {
        mp4.playMp4();
    }
}
