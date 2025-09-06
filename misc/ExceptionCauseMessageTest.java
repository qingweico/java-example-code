package misc;

import cn.qingweico.model.BusinessException;
import cn.qingweico.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.SQLException;

/**
 * {@link Exception#getCause()} 和 {@link Exception#getMessage()} 使用区别
 *
 * @author zqw
 * @date 2025/9/6
 */
@Slf4j
public class ExceptionCauseMessageTest {
    static void level1() throws ControllerException {
        try {
            level2();
        } catch (ServiceException e) {
            throw new ControllerException("Controller层操作失败", e);
        }
    }

    static void level2() throws ServiceException {
        try {
            level3();
        } catch (RepositoryException e) {
            throw new ServiceException("Service层操作失败", e);
        }
    }

    static void level3() throws RepositoryException {
        throw new RepositoryException("Repository层操作失败");
    }

    static class ControllerException extends BusinessException {
        public ControllerException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class ServiceException extends BusinessException {
        public ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class RepositoryException extends BusinessException {
        public RepositoryException(String message) {
            super(message);
        }
    }

    @Test
    public void multiLevelExMessage() {
        try {
            level1();
        } catch (ControllerException e) {
            System.out.println("=== 直接getMessage() ===");
            System.out.println(e.getMessage());

            System.out.println("=== 异常链追踪 ===");
            Throwable cause = e;
            int level = 1;

            while (cause != null) {
                System.out.println("Level " + level + ": " + cause.getMessage());
                cause = cause.getCause();
                level++;
            }
        }
    }

    /**
     * 异常包装原则: 给用户展示顶层异常消息, 通常是业务自定义友好的且直观的信息,
     * 同时需要记录完整的异常链用于调试, 所以使用 getCause + getMessage 结合
     * 使用来获得完整的错误画面
     * getCause提供了详细的根本原因信息, 而getMessage提供了更友好的顶层错误信息
     */
    @Test
    public void wrapperException() {
        try {
            getUser();
        } catch (SQLException e) {
            logDebugInfo(e);
            // 包装异常: 保留原始异常信息, 并将异常信息友好地展示给用户
            throw new BusinessException("获取用户信息失败", e);
        }
    }

    private void getUser() throws SQLException {
        throw new SQLException("SQLException");
    }

    private void logDebugInfo(Exception e) {
        String rootCause = ExceptionUtils.getRootCauseMessage(e);
        log.error("根本原因: {}", rootCause);
    }
}
