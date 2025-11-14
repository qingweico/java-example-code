package misc;

import com.sun.jna.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author zqw
 * @date 2025/11/6
 */
public interface CLibrary extends Library {
    CLibrary INSTANCE = Native.load(
            Platform.isWindows() ? "msvcrt" :
                    "c",
            CLibrary.class
    );

    long time(Pointer time);

    Pointer localtime(Pointer timep);

    class tm extends Structure {
        // 秒
        public int tm_sec;
        // 分
        public int tm_min;
        // 时
        public int tm_hour;
        // 日
        public int tm_mday;
        // 月
        public int tm_mon;
        // 年 since 1900
        public int tm_year;
        // 星期 start 0
        public int tm_wday;
        // 年中的日
        public int tm_yday;
        // 夏令时标志
        public int tm_isdst;

        public tm() {
            super();
        }

        public tm(Pointer p) {
            super(p);
            read();
        }


        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("tm_sec", "tm_min", "tm_hour", "tm_mday", "tm_mon", "tm_year", "tm_wday", "tm_yday", "tm_isdst");
        }
    }


    NativeLong strftime(Pointer s, NativeLong maxsize, String format, tm timeptr);


    int printf(String format, Object... args);

    Pointer fopen(String filename, String mode);

    int fclose(Pointer stream);

    int fread(Pointer ptr, NativeLong size, NativeLong nmemb, Pointer stream);

    int fwrite(Pointer ptr, NativeLong size, NativeLong nmemb, Pointer stream);

    int fseek(Pointer stream, NativeLong offset, int whence);
    NativeLong ftell(Pointer stream);

    int SEEK_SET = 0;
    int SEEK_END = 2;

}
