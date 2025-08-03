package misc;

import cn.qingweico.io.Print;

/**
 * 使用 \r 符号和 jline 库实现进度条
 *
 * @author zqw
 * @date 2023/12/3
 */
class ConsoleProgressBar {
    public static void main(String[] args) {
        int totalSteps = 100;

        for (int i = 0; i <= totalSteps; i++) {
            drawProgressBar(i, totalSteps);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            }
        }

        System.out.println("\n任务完成!");
    }

    private static void drawProgressBar(int currentStep, int totalSteps) {
        int progressBarSize = 100;
        int progress = (int) ((double) currentStep / totalSteps * progressBarSize);

        StringBuilder progressBar = new StringBuilder("[");
        for (int i = 0; i < progressBarSize; i++) {
            if (i < progress) {
                progressBar.append("\u001B[32m=\u001B[0m");
            } else {
                progressBar.append(" ");
            }
        }
        progressBar.append("] ");

        double percentage = ((double) currentStep / totalSteps) * 100;
        String status = String.format("%.2f%%", percentage);

        System.out.print("\r" + progressBar + status);
        System.out.flush();
    }
}
