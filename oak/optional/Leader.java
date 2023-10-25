package oak.optional;

import lombok.Data;

/**
 * Nested Object
 *
 * @author zqw
 * @date 2023/10/17
 */
@Data
class Leader {
    private String name;
    private String phone;
    private Address address;
}

@Data
class Address {
    private String home;
    private String company;
}
