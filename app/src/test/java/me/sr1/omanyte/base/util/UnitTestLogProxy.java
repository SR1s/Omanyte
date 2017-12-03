package me.sr1.omanyte.base.util;

/**
 * 单元测试用的日志输出代理
 * @author SR1
 */

public class UnitTestLogProxy implements LogUtil.LogProxy {

    @Override
    public void v(String tag, String message, Throwable throwable) {
        printLog(formatLog("VERBOSE", tag, message), throwable);
    }

    @Override
    public void d(String tag, String message, Throwable throwable) {
        printLog(formatLog("DEBUG", tag, message), throwable);
    }

    @Override
    public void i(String tag, String message, Throwable throwable) {
        printLog(formatLog("INFO", tag, message), throwable);
    }

    @Override
    public void w(String tag, String message, Throwable throwable) {
        printLog(formatLog("WARN", tag, message), throwable);
    }

    @Override
    public void e(String tag, String message, Throwable throwable) {
        printLog(formatLog("ERROR", tag, message), throwable);
    }

    private String formatLog(String level, String tag, String message) {
        return String.format("[%s] %s - %s", level, tag, message);
    }

    private void printLog(String log, Throwable throwable) {
        System.out.println(log);
        if (throwable != null) {
            System.out.println(
                    String.format(
                            "[%s] %s",
                            throwable.getClass().getSimpleName(),
                            throwable.toString()
                    )
            );
            throwable.printStackTrace(System.out);
        }
    }
}
