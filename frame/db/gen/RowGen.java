package frame.db.gen;

import com.github.javafaker.Faker;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import util.RandomDataUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Supplier;

/**
 * @author zqw
 */
@Slf4j
class RowGen {
    Faker faker = RandomDataUtil.localFaker;

    static ArrayList<BookResourceLoader.Sentence> sentences = null;

    static {
        try {
            sentences = new BookResourceLoader().sentences();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private String genBatch(String table, String column, int batchSize, Supplier<String> supplier) {

        var batch = new LinkedList<String>();
        for (int j = 0; j < batchSize; j++) {
            batch.add('(' + supplier.get() + ')');
        }
        var joined = String.join(",", batch);
        var sql = String.format("insert into %s (%s) values " + joined + ";", table, column);
        log.info("executing: {}", sql);
        return sql;
    }

    public String genUserBatch(int bucketSize) {
        var column = "`name`, `uname`, `pwd`, `salt`, `tel`, `address`, `avatar`, `ip`";
        return this.genBatch("user", column, bucketSize, () -> {
            var name = faker.name().firstName() + faker.name().lastName();
            var uname = faker.name().username();
            var salt = faker.number().randomNumber(6, false);
            var pwd = faker.number().digits(6);
            pwd = Hashing.sha256().hashString(pwd + salt, Charsets.UTF_8).toString();
            var tel = faker.phoneNumber().phoneNumber();
            var address = faker.address().fullAddress();
            var avatar = faker.avatar().image();
            var ip = faker.internet().ipV4Address();
            String[] ipValString = ip.split("\\.");
            Integer[] ipVal =  Arrays.stream(ipValString).map(Integer::parseInt).toArray(Integer[]::new);
            long ipInt = 0;
            for (int k = 0; k < ipValString.length; k++) {
                ipInt += (long) ipVal[k] << (24 - (8 * k));
            }
            return String.format("'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'",
                    name,
                    uname,
                    pwd,
                    salt,
                    tel,
                    address,
                    avatar,
                    ipInt
            );
        });
    }
    public String getBatchPost(int bucketSize, int userStart, int userEnd) {
        var column = "name,user_id,title,info,approve, dislike,state";
        return this.genBatch("post", column, bucketSize, () -> {
            var name = faker.name().fullName();
            int userId = (int) (Math.random() * (userEnd - userStart) + userStart);
            var title = faker.medical().medicineName();
            var info = sentences.get((int) (Math.random() * sentences.size())).text;
            var approve = Math.round(Math.random() * 10000);
            var dislike = Math.round(Math.random() * 10000);
            var state = Math.round(Math.random() * 4);
            title = title.replace("'", "\\'");
            if (title.length() > 12) {
                title = title.substring(0, 12);
            }
            return String.format("'%s', %s, '%s','%s',%s,%s,%s",
                    name,
                    userId,
                    title,
                    info,
                    approve,
                    dislike,
                    state
            );
        });
    }
}
