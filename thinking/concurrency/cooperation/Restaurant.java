package thinking.concurrency.cooperation;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static cn.qingweico.io.Print.print;

/**
 * The producer-consumer approach to task cooperation
 *
 * @author zqw
 * @date 2021/4/11
 */
class Restaurant {
   Meal meal;
   ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2);
   final WaitPerson waitPerson = new WaitPerson(this);
   final Chef chef = new Chef(this);
   public Restaurant() {
      pool.execute(chef);
      pool.execute(waitPerson);
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
   @Override
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
               while (restaurant.meal == null) {
                   wait();
               }
            }
            Print.println("WaitPerson got " + restaurant.meal);
            synchronized (restaurant.chef) {
               restaurant.meal = null;
               // Ready for another
               restaurant.chef.notifyAll();
            }
         }
      }catch (InterruptedException e) {
         Print.println("WaitPerson interrupted");
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
               Print.println("Out of food, closing");
               restaurant.pool.shutdownNow();
            }
            print("Order up! ");
            synchronized (restaurant.waitPerson) {
               restaurant.meal = new Meal(count);
               restaurant.waitPerson.notifyAll();
            }
            TimeUnit.MILLISECONDS.sleep(100);
         }
      }catch (InterruptedException e) {
         Print.println("Chef interrupted");
      }
   }
}
