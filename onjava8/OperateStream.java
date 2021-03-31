package onjava8;

import util.Print;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/2/5
 */
public class OperateStream {
    public static void main(String[] args) {
        List<Integer> container = Arrays.asList(0, 1, 3, 5, 7, 8, 8);
        container.stream().filter(x -> x % 2 == 0).forEach(Print::prints);
        print();
        container.stream().limit(3).forEach(Print::prints);
        print();
        container.stream().skip(3).forEach(Print::prints);
        print();
        // The equals() and hashCode() methods need to be overridden when used on objects
        container.stream().distinct().forEach(Print::prints);

        print();

        // map
        container.stream().map(x -> x * 2).forEach(Print::prints);


        print();

        // General
        List<String> stringList = Arrays.asList("hello", "world");
        Stream<Stream<Character>> streamStream = stringList
                .stream()
                .map(OperateStream::filterCharacter);

        streamStream.forEach((stream) -> {
            stream.forEach(Print::prints);
        });
        print();

        // Using flatMap
        stringList.stream().flatMap(OperateStream::filterCharacter).forEach(Print::prints);
    }

    private static Stream<Character> filterCharacter(String str) {
        List<Character> res = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            res.add(c);
        }
        return res.stream();
    }
}
