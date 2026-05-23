package oak;

import cn.qingweico.datetime.DateUtil;
import jodd.util.StringPool;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Welcome, What doesn't kill you makes you stronger
 *
 * @author zqw
 * @date 2026/5/22
 */
public class MachineInfo {
    // ── ANSI 颜色 ──────────────────────────────────────────────────────────────
    static final String RESET = "\u001B[0m";
    static final String BOLD = "\u001B[1m";
    static final String CYAN = "\u001B[36m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String MAGENTA = "\u001B[35m";
    static final String RED = "\u001B[31m";
    static final String WHITE = "\u001B[37m";

    static final int WIDTH = 72;

    public static void main(String[] args) throws InterruptedException {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        printBanner();
        printOsInfo(os);
        printCpuInfo(hal.getProcessor());
        printMemoryInfo(hal.getMemory());
        printDiskInfo(hal.getDiskStores());
        printGpuInfo(hal.getGraphicsCards());
        printNetworkInfo(hal.getNetworkIFs());
        printBatteryInfo(hal.getPowerSources());
        printSensorInfo(hal.getSensors());
        printProcessInfo(os);
        printFooter();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Banner
    // ══════════════════════════════════════════════════════════════════════════
    static void printBanner() {
        String now = DateUtil.now();
        String machine = Optional.ofNullable(System.getenv("COMPUTERNAME"))
                .orElse(System.getenv("HOSTNAME")) + StringPool.LEFT_BRACKET + System.getProperty("user.name") + StringPool.RIGHT_BRACKET;
        System.out.println();
        printDoubleLine();
        printCentered("  ██████╗ ███████╗██╗  ██╗██╗███╗   ██╗███████╗ ██████╗  ");
        printCentered("  ██╔═══██╗██╔════╝██║  ██║██║████╗  ██║██╔════╝██╔═══██╗ ");
        printCentered("  ██║   ██║███████╗███████║██║██╔██╗ ██║█████╗  ██║   ██║ ");
        printCentered("  ██║   ██║╚════██║██╔══██║██║██║╚██╗██║██╔══╝  ██║   ██║ ");
        printCentered("  ╚██████╔╝███████║██║  ██║██║██║ ╚████║██║     ╚██████╔╝ ");
        printCentered("   ╚═════╝ ╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝╚═╝      ╚═════╝  ");
        printCentered(CYAN + "System Information Reporter For " + machine + RESET);
        printCentered(WHITE + "Generated: " + now + RESET);
        printDoubleLine();
        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  OS / System
    // ══════════════════════════════════════════════════════════════════════════
    static void printOsInfo(OperatingSystem os) {
        printSectionHeader("🖥  OPERATING SYSTEM", GREEN);
        OperatingSystem.OSVersionInfo ver = os.getVersionInfo();
        long uptimeSec = os.getSystemUptime();

        printRow("OS Family", os.getFamily());
        printRow("Manufacturer", os.getManufacturer());
        printRow("Version", ver.getVersion() + "  build " + ver.getBuildNumber());
        printRow("Code Name", ver.getCodeName().isEmpty() ? "N/A" : ver.getCodeName());
        printRow("Architecture", os.getBitness() + "-bit");
        printRow("System Uptime", formatUptime(uptimeSec));
        printRow("Boot Time", formatEpoch(os.getSystemBootTime()));
        printRow("Process Count", String.valueOf(os.getProcessCount()));
        printRow("Thread Count", String.valueOf(os.getThreadCount()));
        printSectionFooter();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  CPU
    // ══════════════════════════════════════════════════════════════════════════
    static void printCpuInfo(CentralProcessor cpu) throws InterruptedException {
        printSectionHeader("⚙  CPU — Central Processing Unit", CYAN);
        CentralProcessor.ProcessorIdentifier id = cpu.getProcessorIdentifier();

        printRow("Name", id.getName());
        printRow("Vendor", id.getVendor());
        printRow("Family / Model", "Family " + id.getFamily() + "  Model " + id.getModel() + "  Stepping " + id.getStepping());
        printRow("Identifier", id.getIdentifier());
        printRow("Microarchitecture", id.getMicroarchitecture());
        printRow("Physical CPUs", String.valueOf(cpu.getPhysicalPackageCount()));
        printRow("Physical Cores", String.valueOf(cpu.getPhysicalProcessorCount()));
        printRow("Logical Cores", String.valueOf(cpu.getLogicalProcessorCount()));
        printRow("64-bit", String.valueOf(id.isCpu64bit()));
        printRow("Max Frequency", FormatUtil.formatHertz(cpu.getMaxFreq()));

        // Per-core current frequency
        long[] freqs = cpu.getCurrentFreq();
        for (int i = 0; i < freqs.length; i++) {
            if (freqs[i] > 0) {
                printRow("Core-" + i + " Freq", FormatUtil.formatHertz(freqs[i]));
            }
        }

        // CPU load – sample 1 second
        long[] prev = cpu.getSystemCpuLoadTicks();
        Thread.sleep(1000);
        long[] next = cpu.getSystemCpuLoadTicks();
        double load = cpu.getSystemCpuLoadBetweenTicks(prev) * 100.0;
        printRow("CPU Load (1s)", String.format("%.1f%%", load) + "  " + barGraph(load, 100, 30));

        // Per-core load
        double[] corePrev = cpu.getProcessorCpuLoadBetweenTicks(cpu.getProcessorCpuLoadTicks());
        for (int i = 0; i < corePrev.length; i++) {
            double pct = corePrev[i] * 100.0;
            printRow("  Core-" + i + " Load", String.format("%.1f%%", pct) + "  " + barGraph(pct, 100, 20));
        }

        // Context switches / interrupts
        printRow("Context Switches", String.valueOf(cpu.getContextSwitches()));
        printRow("Interrupts", String.valueOf(cpu.getInterrupts()));

        // Cache sizes
        List<CentralProcessor.LogicalProcessor> lps = cpu.getLogicalProcessors();
        if (!lps.isEmpty()) {
            printSubHeader("Logical Processors");
            for (CentralProcessor.LogicalProcessor lp : lps) {
                printRow("  LP-" + lp.getProcessorNumber(),
                        "NumaNode=" + lp.getNumaNode() +
                                "  PhysCore=" + lp.getPhysicalProcessorNumber() +
                                "  Package=" + lp.getPhysicalPackageNumber());
            }
        }

        printSectionFooter();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Memory
    // ══════════════════════════════════════════════════════════════════════════
    static void printMemoryInfo(GlobalMemory mem) {
        printSectionHeader("🧠  MEMORY", YELLOW);

        long total = mem.getTotal();
        long avail = mem.getAvailable();
        long used = total - avail;
        double usedPct = (double) used / total * 100.0;

        printRow("Total RAM", FormatUtil.formatBytes(total));
        printRow("Used RAM", FormatUtil.formatBytes(used) + "  (" + String.format("%.1f%%", usedPct) + ")");
        printRow("Available", FormatUtil.formatBytes(avail));
        printRow("Usage", barGraph(usedPct, 100, 40));
        printRow("Page Size", FormatUtil.formatBytes(mem.getPageSize()));

        VirtualMemory vm = mem.getVirtualMemory();
        printSubHeader("Virtual Memory / Swap");
        printRow("  Swap Total", FormatUtil.formatBytes(vm.getSwapTotal()));
        printRow("  Swap Used", FormatUtil.formatBytes(vm.getSwapUsed()));
        printRow("  Virtual Max", FormatUtil.formatBytes(vm.getVirtualMax()));
        printRow("  Virtual In Use", FormatUtil.formatBytes(vm.getVirtualInUse()));
        printRow("  Pages Swapped In", String.valueOf(vm.getSwapPagesIn()));
        printRow("  Pages Swapped Out", String.valueOf(vm.getSwapPagesOut()));

        // DIMM / Physical Memory
        List<PhysicalMemory> dimms = mem.getPhysicalMemory();
        if (!dimms.isEmpty()) {
            printSubHeader("Physical Memory Modules (DIMMs)");
            for (int i = 0; i < dimms.size(); i++) {
                PhysicalMemory d = dimms.get(i);
                printRow("  Slot-" + i + " Bank", d.getBankLabel());
                printRow("  Slot-" + i + " Capacity", FormatUtil.formatBytes(d.getCapacity()));
                printRow("  Slot-" + i + " Speed", FormatUtil.formatHertz(d.getClockSpeed()));
                printRow("  Slot-" + i + " Type", d.getMemoryType());
                printRow("  Slot-" + i + " Manufacturer", d.getManufacturer());
            }
        }

        printSectionFooter();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Disk
    // ══════════════════════════════════════════════════════════════════════════
    static void printDiskInfo(List<HWDiskStore> disks) {
        printSectionHeader("💾  DISK STORAGE", MAGENTA);
        if (disks.isEmpty()) {
            printRow("Info", "No disks found");
        }

        for (int i = 0; i < disks.size(); i++) {
            HWDiskStore d = disks.get(i);
            printSubHeader("Disk-" + i + ": " + d.getName());
            printRow("  Model", d.getModel());
            printRow("  Serial", d.getSerial().isEmpty() ? "N/A" : d.getSerial());
            printRow("  Size", FormatUtil.formatBytesDecimal(d.getSize()));
            printRow("  Reads", d.getReads() + "  (" + FormatUtil.formatBytes(d.getReadBytes()) + ")");
            printRow("  Writes", d.getWrites() + "  (" + FormatUtil.formatBytes(d.getWriteBytes()) + ")");
            printRow("  Transfer Time", d.getTransferTime() + " ms");
            printRow("  Queue Length", String.valueOf(d.getCurrentQueueLength()));

            List<HWPartition> parts = d.getPartitions();
            if (!parts.isEmpty()) {
                printRow("  Partitions", String.valueOf(parts.size()));
                for (HWPartition p : parts) {
                    printRow("    " + p.getName(),
                            "Type=" + p.getType() +
                                    "  Size=" + FormatUtil.formatBytesDecimal(p.getSize()) +
                                    "  Mount=" + p.getMountPoint());
                }
            }
        }
        printSectionFooter();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  GPU
    // ══════════════════════════════════════════════════════════════════════════
    static void printGpuInfo(List<GraphicsCard> gpus) {
        printSectionHeader("🎮  GPU — Graphics Cards", BLUE);
        if (gpus.isEmpty()) {
            printRow("Info", "No GPU info available");
        }
        for (int i = 0; i < gpus.size(); i++) {
            GraphicsCard g = gpus.get(i);
            printSubHeader("GPU-" + i + ": " + g.getName());
            printRow("  Vendor", g.getVendor());
            printRow("  VRAM", FormatUtil.formatBytes(g.getVRam()));
            printRow("  Device ID", g.getDeviceId());
            printRow("  Version Info", g.getVersionInfo());
        }
        printSectionFooter();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Network
    // ══════════════════════════════════════════════════════════════════════════
    static void printNetworkInfo(List<NetworkIF> nics) {
        printSectionHeader("🌐  NETWORK INTERFACES", CYAN);
        if (nics.isEmpty()) {
            printRow("Info", "No network interfaces found");
        }
        for (NetworkIF nic : nics) {
            if (!nic.isKnownVmMacAddr()) {
                printSubHeader(nic.getDisplayName() + " [" + nic.getName() + "]");
                printRow("  MAC Address", nic.getMacaddr());
                String[] ipv4 = nic.getIPv4addr();
                String[] ipv6 = nic.getIPv6addr();
                printRow("  IPv4", ipv4.length > 0 ? String.join(", ", ipv4) : "N/A");
                printRow("  IPv6", ipv6.length > 0 ? ipv6[0] : "N/A");
                printRow("  MTU", String.valueOf(nic.getMTU()));
                printRow("  Speed", FormatUtil.formatValue(nic.getSpeed(), "bps"));
                printRow("  Bytes Recv", FormatUtil.formatBytes(nic.getBytesRecv()));
                printRow("  Bytes Sent", FormatUtil.formatBytes(nic.getBytesSent()));
                printRow("  Pkts Recv", String.valueOf(nic.getPacketsRecv()));
                printRow("  Pkts Sent", String.valueOf(nic.getPacketsSent()));
                printRow("  In Errors", String.valueOf(nic.getInErrors()));
                printRow("  Out Errors", String.valueOf(nic.getOutErrors()));
            }
        }
        printSectionFooter();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Battery
    // ══════════════════════════════════════════════════════════════════════════
    static void printBatteryInfo(List<PowerSource> batteries) {
        printSectionHeader("🔋  BATTERY / POWER SOURCES", YELLOW);
        if (batteries.isEmpty()) {
            printRow("Info", "No battery / power source detected");
        }
        for (PowerSource b : batteries) {
            printSubHeader(b.getName());
            printRow("  Device Name", b.getDeviceName());
            printRow("  Manufacturer", b.getManufacturer());
            printRow("  Serial Number", b.getSerialNumber());
            printRow("  Chemistry", b.getChemistry());
            printRow("  Capacity Remaining", String.format("%.1f%%", b.getRemainingCapacityPercent() * 100)
                    + "  " + barGraph(b.getRemainingCapacityPercent() * 100, 100, 30));
            printRow("  Is Charging", String.valueOf(b.isCharging()));
            printRow("  Is Discharging", String.valueOf(b.isDischarging()));
            printRow("  Power On Line", String.valueOf(b.isPowerOnLine()));
            if (b.getTimeRemainingEstimated() >= 0) {
                printRow("  Time Remaining", formatUptime((long) b.getTimeRemainingEstimated()));
            }
            printRow("  Voltage", String.format("%.3f V", b.getVoltage()));
            printRow("  Amperage", String.format("%.0f mA", b.getAmperage()));
            if (b.getPowerUsageRate() != 0) {
                printRow("  Power Usage", String.format("%.3f mW", b.getPowerUsageRate()));
            }
            printRow("  Design Capacity", String.format("%d mWh", b.getDesignCapacity()));
            printRow("  Max Capacity", String.format("%d mWh", b.getMaxCapacity()));
            if (b.getCurrentCapacity() > 0) {
                printRow("  Current Capacity", String.format("%d mWh", b.getCurrentCapacity()));
            }
            printRow("  Cycle Count", b.getCycleCount() < 0 ? "N/A" : String.valueOf(b.getCycleCount()));
            printRow("  Manufacture Date", b.getManufactureDate() == null ? "N/A" : b.getManufactureDate().toString());
        }
        printSectionFooter();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Sensors
    // ══════════════════════════════════════════════════════════════════════════
    static void printSensorInfo(Sensors sensors) {
        printSectionHeader("🌡  SENSORS", RED);
        double cpuTemp = sensors.getCpuTemperature();
        double cpuVolt = sensors.getCpuVoltage();
        int[] fanSpeeds = sensors.getFanSpeeds();

        printRow("CPU Temperature", cpuTemp > 0 ? String.format("%.1f °C", cpuTemp) : "N/A");
        printRow("CPU Voltage", cpuVolt > 0 ? String.format("%.3f V", cpuVolt) : "N/A");
        if (fanSpeeds != null && fanSpeeds.length > 0) {
            for (int i = 0; i < fanSpeeds.length; i++) {
                printRow("Fan-" + i + " Speed", fanSpeeds[i] + " RPM");
            }
        } else {
            printRow("Fan Speeds", "N/A");
        }
        printSectionFooter();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Top Processes
    // ══════════════════════════════════════════════════════════════════════════
    static void printProcessInfo(OperatingSystem os) {
        printSectionHeader("📋  TOP PROCESSES (by CPU)", WHITE);
        List<OSProcess> procs = os.getProcesses(
                oshi.software.os.OperatingSystem.ProcessFiltering.ALL_PROCESSES,
                oshi.software.os.OperatingSystem.ProcessSorting.CPU_DESC,
                15);

        System.out.printf("  %-8s %-30s %-10s %-12s %-12s %-6s%n",
                "PID", "Name", "User", "Memory", "CPU Time", "Thd");
        printThinLine();
        for (OSProcess p : procs) {
            System.out.printf("  %-8d %-30s %-10s %-12s %-12s %-6d%n",
                    p.getProcessID(),
                    truncate(p.getName(), 30),
                    truncate(p.getUser(), 10),
                    FormatUtil.formatBytes(p.getResidentSetSize()),
                    formatUptime(p.getUpTime() / 1000),
                    p.getThreadCount());
        }
        printSectionFooter();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Footer
    // ══════════════════════════════════════════════════════════════════════════
    static void printFooter() {
        printDoubleLine();
        printCentered(GREEN + "✔  System scan complete" + RESET);
        printDoubleLine();
        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Formatting helpers
    // ══════════════════════════════════════════════════════════════════════════

    static void printDoubleLine() {
        System.out.println(BOLD + CYAN + "╔" + "═".repeat(WIDTH) + "╗" + RESET);
    }

    static void printSectionHeader(String title, String color) {
        System.out.println();
        System.out.println(color + BOLD +
                "┌" + "─".repeat(WIDTH) + "┐" + RESET);
        System.out.printf(color + BOLD + "│  %-" + (WIDTH - 3) + "s│%n" + RESET, title);
        System.out.println(color + BOLD +
                "├" + "─".repeat(WIDTH) + "┤" + RESET);
    }

    static void printSectionFooter() {
        System.out.println(CYAN + "└" + "─".repeat(WIDTH) + "┘" + RESET);
    }

    static void printSubHeader(String title) {
        System.out.println("  " + YELLOW + BOLD + "▸ " + title + RESET);
        System.out.println("  " + "─".repeat(WIDTH - 4));
    }

    static void printThinLine() {
        System.out.println("  " + "─".repeat(WIDTH - 2));
    }

    static void printRow(String key, String value) {
        int keyWidth = 26;
        String k = String.format("  %-" + keyWidth + "s", key);
        System.out.println(GREEN + k + RESET + " : " + WHITE + value + RESET);
    }

    static void printCentered(String text) {
        // Strip ANSI for length calculation
        int visLen = text.replaceAll("\u001B\\[[;\\d]*m", "").length();
        int padding = Math.max(0, (WIDTH - visLen) / 2);
        System.out.println(" ".repeat(padding) + text);
    }

    static String barGraph(double value, double max, int width) {
        int filled = (int) Math.round(value / max * width);
        filled = Math.min(filled, width);
        String color = value < 60 ? GREEN : value < 80 ? YELLOW : RED;
        return color + "[" + "█".repeat(filled) + "░".repeat(width - filled) + "]" + RESET
                + " " + String.format("%.1f%%", value);
    }

    static String formatUptime(long seconds) {
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long mins = (seconds % 3600) / 60;
        long secs = seconds % 60;
        if (days > 0) {
            return String.format("%dd %02dh %02dm %02ds", days, hours, mins, secs);
        }
        if (hours > 0) {
            return String.format("%dh %02dm %02ds", hours, mins, secs);
        }
        return String.format("%dm %02ds", mins, secs);
    }

    static String formatEpoch(long epochSec) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSec), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    static String truncate(String s, int max) {
        if (s == null) {
            return "N/A";
        }
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}
