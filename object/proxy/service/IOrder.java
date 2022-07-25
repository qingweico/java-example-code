package object.proxy.service;

/**
 * @author zqw
 * @date 2021/10/22
 */
public interface IOrder {
   /**
    * payment interface
    */
   void pay();
   /**
    * show payment status
    */
   void show();
}
