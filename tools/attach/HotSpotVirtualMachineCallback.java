package tools.attach;

import sun.tools.attach.HotSpotVirtualMachine;

/**
 * {@link HotSpotVirtualMachine} {@link VirtualMachineCallback}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see HotSpotVirtualMachine
 */
public interface HotSpotVirtualMachineCallback<T> extends VirtualMachineCallback<HotSpotVirtualMachine, T> {

}
