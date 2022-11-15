package util.process;

import util.constants.Symbol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeoutException;

/**
 * {@link Process} Executor
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @date 2022/7/11
 */
public class ProcessExecutor {

    private static final long WAIT_FOR_TIME_IN_SECOND = Long.getLong("process.executor.wait.for", 1);
    private final String command;
    private final String arguments;
    private final Runtime runtime = Runtime.getRuntime();
    private final ProcessManager processManager = ProcessManager.INSTANCE;
    private boolean finished;


    /**
     * Constructor
     *
     * @param processName command
     * @param arguments   process arguments
     */
    public ProcessExecutor(String processName, String... arguments) {
        StringBuilder argumentsBuilder = new StringBuilder();
        if (arguments != null) {
            for (String argument : arguments) {
                argumentsBuilder.append(Symbol.WHITE_SPACE).append(argument);
            }
        }
        this.arguments = argumentsBuilder.toString();
        this.command = processName + this.arguments;
    }

    /**
     * Execute current process.
     * <p/>
     *
     * @param outputStream output stream for process normal or error input stream.
     * @throws IOException if process execution is failed.
     */
    public void execute(OutputStream outputStream) throws IOException {
        try {
            this.execute(outputStream, Long.MAX_VALUE);
        } catch (TimeoutException e) {
            // ignore
        }
    }

    /**
     * Execute current process.
     * <p/>
     *
     * @param outputStream          output stream for process normal or error input stream.
     * @param timeoutInMilliseconds milliseconds timeout
     * @throws IOException      if process execution is failed.
     * @throws TimeoutException if the execution is timeout over specified <code>timeoutInMilliseconds</code>
     */
    public void execute(OutputStream outputStream, long timeoutInMilliseconds) throws IOException, TimeoutException {
        Process process = runtime.exec(command);
        long startTime = System.currentTimeMillis();
        long endTime = -1L;
        InputStream processInputStream = process.getInputStream();
        InputStream processErrorInputStream = process.getErrorStream();
        int exitValue;
        while (!finished) {
            long costTime = endTime - startTime;
            if (costTime > timeoutInMilliseconds) {
                finished = true;
                process.destroy();
                String message = String.format("Execution is timeout[%d ms]!", timeoutInMilliseconds);
                throw new TimeoutException(message);
            }
            try {
                processManager.addUnfinishedProcess(process, arguments);
                while (processInputStream.available() > 0) {
                    outputStream.write(processInputStream.read());
                }
                while (processErrorInputStream.available() > 0) {
                    outputStream.write(processErrorInputStream.read());
                }
                exitValue = process.exitValue();
                if (exitValue != 0) {
                    throw new IOException();
                }
                finished = true;
            } catch (IllegalThreadStateException e) {
                // Process is not finished yet;
                // Sleep a little to save on CPU cycles
                waitFor(WAIT_FOR_TIME_IN_SECOND);
                endTime = System.currentTimeMillis();
            } finally {
                processManager.removeUnfinishedProcess(process, arguments);
            }
        }
    }

    /**
     * Wait for specified seconds
     *
     * @param seconds specified seconds
     */
    public static void waitFor(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Check current process finish or not.
     *
     * @return <code>true</code> if current process finished
     */
    public boolean isFinished() {
        return finished;
    }

    public static void main(String[] args) throws IOException {
        ProcessExecutor executor = new ProcessExecutor("java -XX:+PrintCommandLineFlags", "-version");
        executor.execute(System.out);
    }
}
