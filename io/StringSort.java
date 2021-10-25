package io;

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class StringSort {
    public static void main(String[] args) throws IOException {
        // Sort the strings and write the sorted strings to the file
        BufferedWriter fw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("io/temp", true)));
        Scanner sc = new Scanner(System.in);
        System.out.println("please input strings: ");
        String s = sc.nextLine();
        char[] ch = s.toCharArray();
        Arrays.sort(ch);
        String r = String.valueOf(ch);
        fw.write(r);
        fw.newLine();
        sc.close();
        fw.close();
        BufferedReader fo = new BufferedReader(new FileReader("io/temp"));
        String ss;
        while ((ss = fo.readLine()) != null) {
            System.out.println(ss);
        }
        fo.close();
    }
}
