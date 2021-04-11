package thinking.concurrency.cooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.Print.print;
import static util.Print.printnb;

/**
 * The producer-consumer approach to task cooperation
 *
 * @author:qiming
 * @date: 2021/4/11
 */
public class Restaurant {
   Meal meal;
   ExecutorService exec = Executors.newCachedThreadPool();
   final WaitPerson waitPerson = new WaitPerson(this);
   final Chef chef = new Chef(this);
   public Restaurant() {
      exec.execute(chef);
      exec.execute(waitPerson);
   }

   public static void main(String[] args) {
      new Restaurant();
   }

}
class Meal {
   private final int orderNum;
   public Meal(int orderNum) {
      this.orderNum = orderNum;
   }
   public String toString() {
      return "Meal: " + orderNum;
   }
}
class WaitPerson implements Runnable {
   private final Restaurant restaurant;

   public WaitPerson(Restaurant r) {
      restaurant = r;
   }

   @Override
   public void run() {
      try {
         while(!Thread.interrupted()) {
            synchronized (this) {
               // Wait for the chef to produce a meal
               while (restaurant.meal == null) wait();
            }
            print("WaitPerson got " + restaurant.meal);
            synchronized (restaurant.chef) {
               restaurant.meal = null;
               // Ready for another
               restaurant.chef.notifyAll();
            }
         }
      }catch (InterruptedException e) {
         print("WaitPerson interrupted");
      }
   }
}
class Chef implements Runnable {
   private final Restaurant restaurant;
   private int count = 0;
   public Chef(Restaurant restaurant) {
      this.restaurant = restaurant;
   }


   @Override
   public void run() {
      try {
         while (!Thread.interrupted()) {
            synchronized (this) {
               // Wait for the meal to be taken!
               while (restaurant.meal != null) {wait();}
            }
            if(++count == 10) {
               print("Out of food, closing");
               restaurant.exec.shutdownNow();
            }
            printnb("Order up! ");
            synchronized (restaurant.waitPerson) {
               restaurant.meal = new Meal(count);
               restaurant.waitPerson.notifyAll();
            }
            TimeUnit.MILLISECONDS.sleep(100);
         }
      }catch (InterruptedException e) {
         print("Chef interrupted");
      }
   }
}
