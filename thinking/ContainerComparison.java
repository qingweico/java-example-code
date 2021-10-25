package thinking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.Print.print;

class BerylliumSphere {
    private static long counter;
    private final long id = counter++;

    public String toString() {
        return "Sphere" + id;
    }
}

public class ContainerComparison {
    public static void main(String[] args) {
        BerylliumSphere[] berylliumSpheres = new BerylliumSphere[10];
        for (int i = 0; i < 5; i++) {
            berylliumSpheres[i] = new BerylliumSphere();
        }
        print(Arrays.toString(berylliumSpheres));
        print(berylliumSpheres.length);
        List<BerylliumSphere> berylliumSphereList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            berylliumSphereList.add(new BerylliumSphere());
        }
        print(berylliumSphereList);
    }

}

