package jvm;

/**
 * @author:qiming
 * @date: 2021/11/10
 */
public class LocalVarGc {

   public void localVar1() {
      byte[] buff = new byte[10 * 1024 * 1024];
      System.gc();
   }
   public void localVar2() {
      byte[] buff = new byte[10 * 1024 * 1024];
      buff = null;
      System.gc();
   }
   public void localVar3() {
      {
         byte[] buff = new byte[10 * 1024 * 1024];
      }
      System.gc();
   }
   public void localVar4() {
      {
         byte[] buff = new byte[10 * 1024 * 1024];
      }
      int v = 10;
      System.gc();
   }
   public void localVar5() {
      localVar1();
      System.gc();
   }

   public static void main(String[] args) {
      LocalVarGc localVarGc = new LocalVarGc();
      localVarGc.localVar1();
   }
}
