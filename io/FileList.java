package io;

import java.io.File;

/**
 * Recursively traverse all the files in a folder
 *
 * @author zqw
 * @date 2021/6/17
 */
public class FileList {

   public static void fileList(File target) {
      if(target.isFile()) {
          return;
      }
      File[] files = target.listFiles();
      if(files == null) {
          return;
      }
      for(File file : files) {
         System.out.println(file.getName());
         fileList(file);
      }
   }
   public static void main(String[] args) {
         fileList(new File(args[0]));
   }
}
