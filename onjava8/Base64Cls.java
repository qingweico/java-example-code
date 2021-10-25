package onjava8;

import java.util.Base64;

/**
 * @author:qiming
 * @date: 2021/10/22
 */
public class Base64Cls {
   public static void main(String[] args) {
      Base64.Decoder decode = Base64.getDecoder();
      Base64.Encoder encoder = Base64.getEncoder();
   }
}
