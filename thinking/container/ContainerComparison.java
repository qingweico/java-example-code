package thinking.container;

import util.Print;
import util.constants.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zqw
 * @date 2020/09/12
 */


public class ContainerComparison {
    public static void main(String[] args) {
        BerylliumSphere[] berylliumSpheres = new BerylliumSphere[10];
        for (int i = 0; i < Constants.FIVE; i++) {
            berylliumSpheres[i] = new BerylliumSphere();
        }
        Print.println(Arrays.toString(berylliumSpheres));
        Print.println(berylliumSpheres.length);
        List<BerylliumSphere> berylliumSphereList = new ArrayList<>();
        for (int i = 0; i < Constants.FIVE; i++) {
            berylliumSphereList.add(new BerylliumSphere());
        }
        Print.println(berylliumSphereList);
    }


}

class BerylliumSphere {
    private static long counter;
    private final long id = counter++;

    @Override
    public String toString() {
        return "Sphere" + id;
    }
}
