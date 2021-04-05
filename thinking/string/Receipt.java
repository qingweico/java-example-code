package thinking.string;

import java.util.Formatter;

/**
 * @author:qiming
 * @date: 2021/2/5
 */

// formatter syntax: %[argument_index$][flags][width][.precision]conversion
// with: Controls the minimum size of a domain
// By default, data is right-aligned, but you can change the alignment by using
// the "-" flag.
// When precision is used on a String, it represents the maximum number of characters
// to print on a String, when it is used on a floating-point number, it represents the
// number of decimal places to be displayed., if there are too many decimal places,
// it is rounded off, and if there are too few, it is zeroed at the end.
public class Receipt {

    private double total = 0;
    private final Formatter f = new Formatter(System.out);

    public void printTitle() {
        f.format("%-15s %5s %10s\n", "Item", "Qty", "Price");
        f.format("%-15s %5s %10s\n", "----", "----", "----");
    }
    public void print(String name, int qty, double price) {
        f.format("%-15.15s %5d %10.2f\n", name, qty, price);
        total += price;
    }
    public void printTotal() {
        f.format("%-15s %5s %10.2f\n", "Tax", "", total * 0.06);
        f.format("%-15s %5s %10s\n","", "", "-----");
        f.format("%-15s %5s %10.2f\n", "Total", "", total * 1.06);
    }

    public static void main(String[] args) {
        Receipt receipt = new Receipt();
        receipt.printTitle();
        receipt.print("Jack's Magic Beans", 4, 4.25);
        receipt.print("Princess Peas", 3, 5.1);
        receipt.print("Three Beans Porridge", 1, 14.29);
        receipt.printTotal();
    }
}
