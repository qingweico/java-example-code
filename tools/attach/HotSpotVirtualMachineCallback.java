package tools.attach;

import sun.tools.attach.HotSpotVirtualMachine;

/**
 * {@link HotSpotVirtualMachine} {@link VirtualMachineCallback}
 *
 * @author zqw
 * @see HotSpotVirtualMachine
 * Compile Args: --add-exports jdk.attach/sun.tools.attach=ALL-UNNAMED
 */
public interface HotSpotVirtualMachineCallback<T> extends VirtualMachineCallback<HotSpotVirtualMachine, T> {

}
