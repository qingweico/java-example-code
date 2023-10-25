package oak.optional;

import util.RandomDataUtil;

import java.util.Optional;

/**
 * @author zqw
 * @date 2023/10/17
 */
class OfNullable {
    public static void main(String[] args) {
        Leader leader = new Leader();
        leader.setName(RandomDataUtil.name());
        leader.setPhone(RandomDataUtil.phone(true));
        Address address = new Address();
        address.setHome(RandomDataUtil.address(true));
        address.setCompany(RandomDataUtil.address(true));
        leader.setAddress(address);
        Optional<Leader> optional = Optional.of(leader);
        System.out.println(optional);
    }
}
