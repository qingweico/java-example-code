package collection;

import java.util.Objects;
import java.util.TreeSet;

/**
 * @author:qiming
 * @date: 2021/10/18
 */
public class Customer implements Comparable<Object>{

   private String name;
   private Integer age;

   public Customer(String name, Integer age) {
      this.name = name;
      this.age = age;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getAge() {
      return age;
   }

   public void setAge(Integer age) {
      this.age = age;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Customer customer = (Customer) o;
      return Objects.equals(name, customer.name) && Objects.equals(age, customer.age);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name, age);
   }

   @Override
   public String toString() {
      return "Customer{" +
              "name='" + name + '\'' +
              ", age=" + age +
              '}';
   }

   @Override
   public int compareTo(Object o) {
      Customer anObject = (Customer)o;
      if(this.name.compareTo(anObject.name) > 0) {
         return 1;
      }
      if(this.name.compareTo(anObject.name) < 0) {
         return -1;
      }

      return this.age.compareTo(anObject.getAge());
   }

   public static void main(String[] args) {
      TreeSet<Customer> customers = new TreeSet<>();
      customers.add(new Customer("Tom", 28));
      customers.add(new Customer("Jerry", 20));
      customers.add(new Customer("Rose", 24));
      System.out.println(customers);
   }
}
