package tools.json;

import annotation.Ignore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.ObjectFactory;
import util.RandomDataGenerator;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * --------------- Jackson(Spring Boot 默认的JSON解析框架) ---------------
 *
 * @author zqw
 * @date 2022/7/18
 */
@Slf4j
public class JacksonTest {

    static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // 在反序列化时忽略 json 中存在 但 Java 对象中不存在的属性
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 在序列化时日期格式默认为 yyyy-MM-dd'T' HH:mm:ss.SSSX
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 在序列化时自定义时间日期格式
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 在序列化时忽略值为 null 的属性
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 在序列化时忽略值为默认值的属性
        MAPPER.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
    }

    /**
     * 简单对象和Json之间的互相转换
     */
    @Test
    public void toJson() throws JsonProcessingException {
        JacksonClass ins = ObjectFactory.create(JacksonClass.class, true);
        String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(ins);
        log.info("序列化:");
        System.out.println(json);
        log.info("反序列化:");
        ins = MAPPER.readValue(json, JacksonClass.class);
        System.out.println(ins);


    }

    /**
     * map <——> json <——> pojo
     */
    @Test
    public void mapToPojo() throws JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("name", RandomDataGenerator.randomName());
        map.put("date", new Date());
        map.put("is_present", RandomDataGenerator.tf());
        String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        log.info("序列化:");
        System.out.println(json);
        log.info("反序列化:");
        JacksonClass ins = MAPPER.readValue(json, JacksonClass.class);
        System.out.println(ins);
        json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(ins);
        System.out.println(json);

    }

    @Data
    static class JacksonClass implements Serializable {
        private String name;
        // 默认情况下序列化时会将日期类型转换为long类型
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        private Date date;
        // 序列化时将字段名称由isPresent -> is_present
        @JsonProperty("is_present")
        private boolean isPresent;
        // 使该字段不参与序列化和反序列化
        @JsonIgnore
        private Integer sum;
        @Ignore
        private Integer defaultValue;
        @Ignore
        private String nullValue;
    }
}
