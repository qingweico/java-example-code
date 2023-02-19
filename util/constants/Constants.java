package util.constants;

/**
 * Global Constants
 *
 * @author zqw
 * @date 2022/1/28
 */
public interface Constants {
    /////////////// 0 ~ 20

    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;
    int FIVE = 5;
    int BYTE = 8;
    int TEN = 10;
    int TWENTY = 20;

    /////////////// num

    int NUM_100 = 100;
    int NUM_300 = 300;
    int NUM_1000 = 1000;
    int NUM_10000 = 10_000;
    int NUM_100000 = 100_000;
    int NUM_1000000 = 1_000_000;
    int NUM_10000000 = 10_000_000;
    int NUM_100000000 = 100_000_000;


    ///////////////  special

    int KB = 1024;
    int EOF = -1;
    String LOOP_BACK = "127.0.0.1";
    int DEFAULT_COMMON_PORT = 8848;

    ///////////////  field type

    String STRING = "string";
    String BOOL = "boolean";
    String INTEGER = "integer";
    String LONG = "long";
    String DATE = "date";
    String DOUBLE = "double";
    String LIST = "List";


    /////////////// unit

    String MS = "ms";
    String REDIS = "redis";
}
