package demo.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * 日志工具
 *
 * @author wangyao
 */
public class LogUtil {

    private static final String TAG = LogUtil.class.getSimpleName();

    public static void main(String[] args) {
        LogUtil.v(new Object(), "verbose message");
        LogUtil.d(InputStream.class, "debug message");
        LogUtil.i(TAG, "info message");
        LogUtil.w(TAG, "warning message");
        LogUtil.e(TAG, "error message");
        LogUtil.e(TAG, new Exception("test exception"), "test error message");
    }

    static {
        InputStream input = null;
        try {
            input = new FileInputStream("logging.properties");
            LogManager.getLogManager().readConfiguration(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.closeQuietly(input);
        }
    }

    public static void v(Object obj, String format, Object... args) {
        log(Level.FINE, obj, format, args);
    }

    public static void d(Object obj, String format, Object... args) {
        log(Level.CONFIG, obj, format, args);
    }

    public static void i(Object obj, String format, Object... args) {
        log(Level.INFO, obj, format, args);
    }

    public static void w(Object obj, String format, Object... args) {
        log(Level.WARNING, obj, format, args);
    }

    public static void e(Object obj, String format, Object... args) {
        log(Level.SEVERE, obj, format, args);
    }

    public static void e(Object obj, Throwable th, String format, Object... args) {
        log(Level.SEVERE, obj, th, format, args);
    }

    private static final void log(Level level, Object obj, String format, Object... args) {
        String prefix = getPrefixFromObject(obj);
        Logger.getLogger(prefix).log(level, buildMessage(prefix, format, args));
    }

    private static final void log(Level level, Object obj, Throwable tr, String format, Object... args) {
        String prefix = getPrefixFromObject(obj);
        Logger.getLogger(prefix).log(level, buildMessage(prefix, format, args), tr);
    }

    private static final String getPrefixFromObject(Object obj) {
        if (null == obj) {
            return "<null>";
        }

        if (obj instanceof String) {
            return (String) obj;
        }

        Class<?> clz = obj instanceof Class ? (Class<?>) obj : obj.getClass();
        return clz.getSimpleName();
    }

    private static final String buildMessage(String prefix, String format, Object... args) {
        String msg;
        try {
            msg = (args == null || args.length == 0) ? format : String.format(Locale.US, format, args);
        } catch (IllegalFormatException ife) {
            msg = format + " (An error occurred while formatting the message.)";
        }
        return String.format(Locale.US, "%s: %s", prefix, msg);
    }
}
