package effective;

import static effective.PhysConstants.*;

/**
 * 接口只用于定义类型
 *
 * @author zqw
 * @date 2021/2/21
 */
class Article22 {
    public static void main(String[] args) {
        System.out.println("接口只用于定义类型");
    }

}

// Constant interface anti-pattern - do not use!
// This interface does not contain any methods, it contains only
// static final fields, each of which exports a constant.
interface PhysicalConstants {

    // Avogadro's number (1/mol)
    static final double AVOGADRO_NUMBER = 6.022_140_857e23;

    // Boltzmann constant (J/K)
    static final double BOLTZMANN_CONSTANT = 1.308_648_52e-23;

    // Mass of electron (kg)
    static final double ELECTRON_MASS = 9.109_383_56e-31;
}

// Classes that use these constants implement this interface to avoid decorating
// the constant name with the class name.
class PhysicalConstantsImpl implements PhysicalConstants {
    public static void main(String[] args) {
        System.out.println(AVOGADRO_NUMBER);
    }

}

// The constant interface pattern is a poor use of interfaces
// Implementing the constant interface results in exposing the implementation
// details of those constants to the exported API of the class.

// If a non-final class implements a constant interface, the namespace of all
// its subclasses will also be polluted by the constants in the interface.

// Constant interfaces in the JDK
// java.io.ObjectStreamConstants;

// Constant utility class
class PhysConstants {
    private PhysConstants() {
    } // Prevents instantiation

    public static final double AVOGADRO_NUMBER = 6.022_140_857e23;
    public static final double BOLTZMANN_CONSTANT = 1.308_648_52e-23;
    public static final double ELECTRON_MASS = 9.109_383_56e-31;

}

// Use of static import to avoid qualifying constants.
class Test {
    double atoms(double mols) {
        return AVOGADRO_NUMBER * mols;
    }

    // Many more uses of PhysicalConstants justify static import.
}

// To sum, interfaces should only be used to define type,
// they should not be used to export constants.