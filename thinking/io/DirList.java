package thinking.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author:÷‹«ÏŒ∞
 * @date: 2021/1/7
 */

public class DirList {

    // Or use anonymous inner class
    public static FilenameFilter filter(String regex) {
        return new FilenameFilter() {
            private final Pattern pattern = Pattern.compile(regex);

            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).matches();
            }
        };
    }


    public static void main(String[] args) {
        File path = new File("./");
        String[] list;
        if (args.length == 0) {
            list = path.list();
        } else {
            list = path.list(new DirFilter(args[0]));
        }
        Arrays.sort(Objects.requireNonNull(list), String.CASE_INSENSITIVE_ORDER);
        for (String dirItem : list) {
            System.out.println(dirItem);
        }
    }
}

class DirFilter implements FilenameFilter {
    private final Pattern pattern;

    public DirFilter(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }
}
