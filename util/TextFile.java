package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Static functions for reading and writing text files as as single string,
 * and treating a file s as ArrayList.
 *
 * @author:qiming
 * @date: 2021/2/4
 */
public class TextFile extends ArrayList<String> {

    // Read a file as a single string
    public static String read(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    // Write a single file in one method call:
    public static void write(String fileName, String text) {
        try {
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Read a file, split by any regular expression
    public TextFile(String fileName, String splitter) {
        super(Arrays.asList(read(fileName).split(splitter)));
        // Regular expression split() often leaves an empty String at the first position
        if ("".equals(get(0))) remove(0);
    }

    // Normally read by lines:
    public TextFile(String fileName) {
        this(fileName, "\n");
    }

    public void write(String fileName) {
        try {
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
            try {
                for (String item : this) {
                    out.println(item);
                }
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String file = read("src/util/TextFile.java");
        write("src/util/test.txt", file);
        TextFile text = new TextFile("src/util/test.txt");
        text.write("src/util/test2.txt");
        // Break into unique sorted list of word:
        TreeSet<String> words = new TreeSet<>(
                new TextFile("src/util/TextFile.java", "\\W+")
        );
        // Display the capitalized words:
        System.out.println(words.headSet("a"));
    }
}
