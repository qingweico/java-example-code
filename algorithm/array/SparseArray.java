package algorithm.array;

/**
 * @author zqw
 * @date 2021/11/9
 */
public class SparseArray {
   public static void main(String[] args) {
      int[][] array = new int[11][11];
      array[2][1] = 4;
      array[1][3] = 2;
      for (int[] row : array) {
         for (int item : row) {
            System.out.printf("%d \t", item);
         }
         System.out.println();
      }
      int sum = 0;
      for (int[] ai : array) {
         for (int anInt : ai) {
            if (anInt != 0) {
               sum++;
            }
         }
      }
      System.out.println(sum);
      int[][] sparseArray = new int[sum + 1][3];
      sparseArray[0][0] = array.length;
      sparseArray[0][1] = array[0].length;
      sparseArray[0][2] = sum;
      int count = 0;
      for (int i = 0; i < array.length; i++) {
         for (int j = 0; j < array[i].length; j++) {
            if (array[i][j] != 0) {
               count++;
               sparseArray[count][0] = i;
               sparseArray[count][1] = j;
               sparseArray[count][2] = array[i][j];
            }
         }
      }
      for (int[] row : sparseArray) {
         for (int item : row) {
            System.out.printf("%d\t", item);
         }
         System.out.println();
      }
      System.out.println("##########");
      int[][] arrays = new int[sparseArray[0][0]][sparseArray[0][1]];
      for (int i = 1; i < sparseArray.length; i++) {
         arrays[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
      }
      for (int[] row : arrays) {
         for (int item : row) {
            System.out.printf("%d\t", item);
         }
         System.out.println();
      }
   }
}
