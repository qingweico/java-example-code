package jcip;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author:qiming
 * @date: 2021/10/23
 */
public class FileCrawler implements Runnable{
   private final Integer CAPACITY = 1000;
   private final BlockingQueue<File> fileQueue;
   private final FileFilter fileFilter;
   private final File root;

   public FileCrawler(File root, FileFilter fileFilter) {
      this.root = root;
      fileQueue = new LinkedBlockingQueue<>(CAPACITY);
      this.fileFilter = new FileFilter() {
         @Override
         public boolean accept(File file) {
            return file.isDirectory() || fileFilter.accept(file);
         }
      };


   }   @Override
   public void run() {
   }
   private void crawl(File root) {
      File[] entries = root.listFiles(fileFilter);
      if(entries != null) {
         for(File entry : entries) {
            if(entry.isDirectory()) {
               crawl(entry);
            }
         }
      }
   }
}
