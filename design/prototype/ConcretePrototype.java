package design.prototype;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author:qiming
 * @date: 2021/12/23
 */
public class ConcretePrototype extends Prototype{

    private final String field;
    public ConcretePrototype(String field) {
        this.field = field;
    }
    @Override
    Prototype fieldClone() {
        return new ConcretePrototype(field);
    }

    @Override
    public String toString() {
        return field;
    }
}
class Client {
    public static void main(String[] args) {
        ConcretePrototype concretePrototype = new ConcretePrototype("you");
        Prototype prototype = concretePrototype.fieldClone();
        System.out.println(prototype);
    }
}
 class SwitchTest{//1
    public static void main(String[] args) {//2
        try {
            FileOutputStream fos = new FileOutputStream("afile.txt");
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(3);
            dos.writeChar(1);
            dos.close();
            fos.close();
        } catch (IOException e) {}

    }//4
}