package effective;

import java.math.BigDecimal;

/**
 * 如果需要精确的答案，请避免使用float和double
 *
 * @author:周庆伟
 * @date: 2020/11/23
 */
public class Article60 {
    public static void main(String[] args) {
        // 0.6100000000000001
        System.out.println(1.03 - 0.42);
        // 0.09999999999999998
        System.out.println(1.00 - 9 * 0.10);
    }
}

// eg: Let's say you have one dollar in your pocket and you see on
// the shelf a row of delicious candy for 10 cents,20 cents, 30 cents and so on
// you plan to start with candy priced at 10 cents and buy one of each until you
// can't afford the one on the shelf,so how much candy can you buy and how much
// change will you get back?
class MonetaryCalculation {
    public static void main(String[] args) {
        // Broken
//        double funds = 1.00;
//        int itemsBought = 0;
//        for(double price = 0.10; funds >= price;price += 0.10){
//            funds -= price;
//            itemsBought++;
//        }
//        System.out.println(itemsBought + " items bought");
//        System.out.println("Changes " + funds);


        // Good
        // The following program is a copy of the previous one,using BigDecimal instead of a double
        final BigDecimal TEN_CENTS = new BigDecimal(".10");

        int itemsBought = 0;
        // It uses the String constructor for BigDecimal instead of the double constructor.
        // This is necessary to avoid introducing incorrect values into the calculation.
        BigDecimal funds = new BigDecimal("1.00");
        for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
            funds = funds.subtract(price);
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought");
        System.out.println("Changes " + funds);
        usingIntOrLongInsteadOfBigDecimal();
    }

    // But using BigDecimal is slow,so you could use int or long to instead of BigDecimal
    public static void usingIntOrLongInsteadOfBigDecimal() {
        int itemsBought = 0;
        int funds = 100;
        for (int price = 10; funds >= price; price += 10) {
            funds -= price;
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought");
        System.out.println("Changes " + funds);
    }
}
