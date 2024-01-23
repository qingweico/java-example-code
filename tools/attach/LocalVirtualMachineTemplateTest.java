package tools.attach;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.spi.AttachProvider;
import junit.framework.Assert;
import org.junit.Test;
import util.Print;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 * {@link LocalVirtualMachineTemplate} Test
 *
 * @author zqw
 * @see LocalVirtualMachineTemplate
 * @since jdk 6 引入 Attach API, 用于与JVM进行通信的API, Core Class {@link VirtualMachine}
 * JDK17 Runtime VM Param : --add-opens java.management/sun.management=ALL-UNNAMED
 */
public class LocalVirtualMachineTemplateTest {

    @Test
    public void getVirtualMachineProcessorId() {
        LocalVirtualMachineTemplate localVirtualMachineTemplate = new LocalVirtualMachineTemplate();
        String processId = localVirtualMachineTemplate.getProcessId();
        Assert.assertNotNull(processId);
        System.out.println(processId);
        Assert.assertTrue(Integer.parseInt(processId) > -1);
    }

    @Test
    public void executeCallback() throws IOException, AttachNotSupportedException {
        LocalVirtualMachineTemplate localVirtualMachineTemplate = new LocalVirtualMachineTemplate();

        AttachProvider result = localVirtualMachineTemplate.execute((HotSpotVirtualMachineCallback<AttachProvider>) virtualMachine -> {
            Print.println("VirtualMachineCallback");
            return virtualMachine.provider();
        });

        Assert.assertNotNull(result);
    }

    @Test
    public void printVirtualMachines() {
        VirtualMachineManager virtualMachineManager = Bootstrap.virtualMachineManager();
        List<Connector> allConnectors = virtualMachineManager.allConnectors();
        List<VirtualMachine> connectedVirtualMachines = virtualMachineManager.connectedVirtualMachines();
        Print.printColl(allConnectors);
        Print.printColl(connectedVirtualMachines);
    }

    @Test
    public void getVirtualMachine() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        // Get the name of the JVM
        String jvmName = runtimeMXBean.getName();
        Print.grace("JVM Name", jvmName);
    }
    @Test
    public void connectedVM() {

    }
}
