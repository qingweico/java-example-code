package tools.attach;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import lombok.Getter;

import java.io.IOException;

/**
 * {@link VirtualMachine} Template Class
 *
 * @author zqw
 * @see VirtualMachine
 */
@Getter
public class VirtualMachineTemplate {

    /**
     * Process ID
     */
    private final String processId;

    /**
     * Constructor with {@link Process} ID
     *
     * @param processId {@link Process} ID
     */
    public VirtualMachineTemplate(String processId) {
        this.processId = processId;
    }

    /**
     * Execute {@link VirtualMachineCallback}
     *
     * @param callback {@link VirtualMachineCallback}
     * @param <T>      VirtualMachineCallback<V, T> callback
     * @return V extends VirtualMachine
     * @throws AttachNotSupportedException AttachNotSupportedException
     * @throws IOException                 IOException
     */
    public final <V extends VirtualMachine, T> T execute(VirtualMachineCallback<V, T> callback) throws AttachNotSupportedException, IOException {
        VirtualMachine virtualMachine = null;
        T result;
        try {
            virtualMachine = VirtualMachine.attach(processId);
            @SuppressWarnings("unchecked") V v = (V) virtualMachine;
            result = callback.doInVirtualMachine(v);
        } finally {
            if (virtualMachine != null) {
                virtualMachine.detach();
            }
        }
        return result;
    }


}
