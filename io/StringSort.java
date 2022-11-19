package io;

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * @author zqw
 * @date 2020/04/27
 */
class StringSort {
    public static void main(String[] args) throws IOException {
        String fileName = "data";
        // Sort the strings and write the sorted strings to the file
        BufferedWriter fw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(fileName, true)));
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        char[] ch = s.toCharArray();
        Arrays.sort(ch);
        String r = String.valueOf(ch);
        fw.write(r);
        fw.newLine();
        sc.close();
        fw.close();
        BufferedReader fo = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = fo.readLine()) != null) {
            System.out.println(line);
        }
        fo.close();
    }
}
