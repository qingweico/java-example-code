package tools.attach;

import util.ManagementUtils;

/**
 * Local {@link VirtualMachineTemplate}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see LocalVirtualMachineTemplate
 */
public class LocalVirtualMachineTemplate extends VirtualMachineTemplate {

    public LocalVirtualMachineTemplate() {
        super(String.valueOf(ManagementUtils.getCurrentProcessId()));
    }

}
