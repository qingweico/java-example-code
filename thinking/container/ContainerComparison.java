package thinking.container;

import util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.Print.print;

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
        print(Arrays.toString(berylliumSpheres));
        print(berylliumSpheres.length);
        List<BerylliumSphere> berylliumSphereList = new ArrayList<>();
        for (int i = 0; i < Constants.FIVE; i++) {
            berylliumSphereList.add(new BerylliumSphere());
        }
        print(berylliumSphereList);
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
