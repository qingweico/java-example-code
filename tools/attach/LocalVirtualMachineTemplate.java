package tools.attach;

import cn.qingweico.runtime.ManagementUtils;

/**
 * Local {@link VirtualMachineTemplate}
 *
 * @author zqw
 * @see LocalVirtualMachineTemplate
 */
public class LocalVirtualMachineTemplate extends VirtualMachineTemplate {

    public LocalVirtualMachineTemplate() {
        super(String.valueOf(ManagementUtils.getCurrentProcessId()));
    }

}
