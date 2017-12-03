package me.sr1.omanyte.base.util;

import android.util.Log;

/**
 * 日志输出工具类
 * @author SR1
 */

public class LogUtil {

    private static LogProxy sLogProxy = new AndroidDefaultLogProxy();

    public static void setLogProxy(LogProxy proxy) {
        sLogProxy = proxy;
    }

    public static void v(String tag, String message) {
        LogProxy proxy = sLogProxy;
        if (proxy != null) proxy.v(tag, message, null);
    }

    public static void v(String tag, String message, Throwable throwable) {
        LogProxy proxy = sLogProxy;
        if (proxy != null) proxy.v(tag, message, throwable);
    }

    public static void d(String tag, String message) {
        LogProxy proxy = sLogProxy;
        if (proxy != null) proxy.d(tag, message, null);
    }

    public static void d(String tag, String message, Throwable throwable) {
        LogProxy proxy = sLogProxy;
        if (proxy != null) proxy.d(tag, message, throwable);
    }

    public static void i(String tag, String message) {
        LogProxy proxy = sLogProxy;
        if (proxy != null) proxy.i(tag, message, null);
    }

    public static void i(String tag, String message, Throwable throwable) {
        LogProxy proxy = sLogProxy;
        if (proxy != null) proxy.i(tag, message, throwable);
    }

    public static void w(String tag, String message) {
        LogProxy proxy = sLogProxy;
        if (proxy != null) proxy.w(tag, message, null);
    }

    public static void w(String tag, String message, Throwable throwable) {
        LogProxy proxy = sLogProxy;
        if (proxy != null) proxy.w(tag, message, throwable);
    }

    public static void e(String tag, String message) {
        LogProxy proxy = sLogProxy;
        if (proxy != null) proxy.e(tag, message, null);
    }

    public static void e(String tag, String message, Throwable throwable) {
        LogProxy proxy = sLogProxy;
        if (proxy != null) proxy.e(tag, message, throwable);
    }

    public static class AndroidDefaultLogProxy implements LogProxy {

        @Override
        public void v(String tag, String message, Throwable throwable) {
            Log.v(tag, message, throwable);
        }

        @Override
        public void d(String tag, String message, Throwable throwable) {
            Log.d(tag, message, throwable);
        }

        @Override
        public void i(String tag, String message, Throwable throwable) {
            Log.i(tag, message, throwable);
        }

        @Override
        public void w(String tag, String message, Throwable throwable) {
            Log.w(tag, message, throwable);
        }

        @Override
        public void e(String tag, String message, Throwable throwable) {
            Log.e(tag, message, throwable);
        }
    }

    public interface LogProxy {
        void v(String tag, String message, Throwable throwable);
        void d(String tag, String message, Throwable throwable);
        void i(String tag, String message, Throwable throwable);
        void w(String tag, String message, Throwable throwable);
        void e(String tag, String message, Throwable throwable);
    }
}
