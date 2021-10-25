package thinking.holding;

import thinking.holding.pets.Hamster;
import thinking.holding.pets.Pet;
import thinking.holding.pets.Pets;
import thinking.holding.pets.Rat;

import java.util.LinkedList;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/1/18
 */
public class LinkedListFeatures {
    public static void main(String[] args) {
        LinkedList<Pet> pets = new LinkedList<>(Pets.arrayList(5));
        print(pets);

        // Identical
        // The getFirst() and element() methods are exactly the same in that they
        // both return the first element in the list without removing it, a
        // NoSuchElementException is thrown when the list is empty.
        print("pets.getFirst(): " + pets.getFirst());
        print("pets.element(): " + pets.element());
        // Only differs in empty-list behavior, it returns null if the list is empty.
        print("pets.peek(): " + pets.peek());


        // Identical
        // The remove() and removeFirst() methods are exactly the same in that they
        // remove and return the head of the list and throw a NoSuchElementException
        // when the list is empty.
        print("pets.remove(): " + pets.remove());
        print("pets.removeFirst(): " + pets.removeFirst());
        // Only differs in empty-list behavior, it returns null if the list is empty.
        print("pets.poll(): " + pets.poll());
        print(pets);

        // Insert an element at the head of the list
        pets.addFirst(new Rat());
        print("After addFirst(): " + pets);

        // They both insert an element at the end of the list.
        pets.offer(Pets.randomPet());
        print("After offer(): " + pets);
        pets.add(Pets.randomPet());
        print("After add(): " + pets);
        pets.addLast(new Hamster());
        print("After addLast(): " + pets);


        // The removeLast() method removes and returns the last element in the list.
        print("pets.removeLast(): " + pets.removeLast());


    }
}
