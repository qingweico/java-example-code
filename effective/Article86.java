package effective;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 谨慎地实现 Serializable 接口
 * @author zqw
 * @date 2025/12/6
 * @see ObjectInputStream#readFields()
 * @see ObjectOutputStream#putFields()
 */
public class Article86 {

    public static void main(String[] args) {


    }

    private void readObjectNoData() throws InvalidObjectException {
        throw new InvalidObjectException("Stream data required");
    }
}
