package thinking.holding;

import thinking.holding.pets.Hamster;
import thinking.holding.pets.Pet;
import thinking.holding.pets.Pets;
import thinking.holding.pets.Rat;
import cn.qingweico.io.Print;

import java.util.LinkedList;

/**
 * @author zqw
 * @date 2021/1/18
 */
public class LinkedListFeatures {
    public static void main(String[] args) {
        LinkedList<Pet> pets = new LinkedList<>(Pets.arrayList(5));
        Print.println(pets);

        // Identical
        // The getFirst() and element() methods are exactly the same in that they
        // both return the first element in the list without removing it, a
        // NoSuchElementException is thrown when the list is empty.
        Print.println("pets.getFirst(): " + pets.getFirst());
        Print.println("pets.element(): " + pets.element());
        // Only differs in empty-list behavior, it returns null if the list is empty.
        Print.println("pets.peek(): " + pets.peek());


        // Identical
        // The remove() and removeFirst() methods are exactly the same in that they
        // remove and return the head of the list and throw a NoSuchElementException
        // when the list is empty.
        Print.println("pets.remove(): " + pets.remove());
        Print.println("pets.removeFirst(): " + pets.removeFirst());
        // Only differs in empty-list behavior, it returns null if the list is empty.
        Print.println("pets.poll(): " + pets.poll());
        Print.println(pets);

        // Insert an element at the head of the list
        pets.addFirst(new Rat());
        Print.println("After addFirst(): " + pets);

        // They both insert an element at the end of the list.
        pets.offer(Pets.randomPet());
        Print.println("After offer(): " + pets);
        pets.add(Pets.randomPet());
        Print.println("After add(): " + pets);
        pets.addLast(new Hamster());
        Print.println("After addLast(): " + pets);


        // The removeLast() method removes and returns the last element in the list.
        Print.println("pets.removeLast(): " + pets.removeLast());


    }
}
