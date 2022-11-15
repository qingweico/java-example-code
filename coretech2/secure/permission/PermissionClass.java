package coretech2.secure.permission;

import java.io.FilePermission;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * 安全管理器和访问权限
 * --------------- 限类的层次结构 ---------------
 * {@link java.security.Permission}
 * {@link java.security.AllPermission}
 * {@link java.security.BasicPermission}
 * {@link java.io.FilePermission}
 * {@link java.net.SocketPermission}
 * BasicPermission 的子类包括以下:
 * {@link java.sql.SQLPermission}
 * {@link java.security.SecurityPermission}
 * {@link java.lang.reflect.ReflectPermission}
 * {@link java.net.NetPermission}
 * {@link java.awt.AWTPermission}
 * {@link javax.security.auth.AuthPermission}
 * {@link java.util.logging.LoggingPermission}
 * {@link java.util.PropertyPermission}
 * {@link java.lang.RuntimePermission}
 * {@link java.io.SerializablePermission}
 *
 * @author zqw
 * @date 2022/8/23
 */
class PermissionClass {
    public static void main(String[] args) {
        // 表示允许在 /tmp 目录下读取和写入任何文件
        var p = new FilePermission("/tmp", "read,write");
        // java.security.Policy 类的默认实现可从访问权限文件中读取权限

        final SecurityManager sm = new SecurityManager();
        // 检查当前的安全管理器是否授予给定的权限,如果没有授予该权限,则抛出一个 SecurityException 异常
        sm.checkPermission(p);

        // 获取该类的保护域,如果该类被加载时没有保护域,则返回 null
        PermissionClass.class.getProtectionDomain();

        // 用给定的代码来源和权限构建一个保护域

        // new ProtectionDomain(CodeSource source, PermissionCollection permission)
        // implies(Permission p) 如果该保护域允许给定的权限 则返回true
        // getCodeSource() 获取该保护域的代码来源



    }
}
