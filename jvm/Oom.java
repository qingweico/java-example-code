package jvm;

/**
 * OOM
 *
 * @author zqw
 * @date 2022/7/9
 */
class Oom {
    public static void main(String[] args) {
        // 配置OOM时执行的脚本
        // VM: -Xmx20m -XX:OnOutOfMemoryError=/opt/server/restart.sh
        @SuppressWarnings("unused") byte[] bytes = new byte[1024 * 1024 * 50];
    }
}
