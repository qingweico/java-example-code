package algorithm.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * //////////////////// 字典树[数组实现] ////////////////////
 * @author zqw
 * @date 2022/12/21
 * @see Trie
 */
public class TrieTree {
    static class TrieNode {
        Character data;
        TrieNode[] children;
        boolean isEndingChar;
        TrieNode(Character data) {
            this.data = data;
            this.isEndingChar = false;
            this.children = new TrieNode[26];
        }
    }

    private final TrieNode root = new TrieNode('/');

    public void insert(String text) {
        TrieNode p = this.root;
        for (int i = 0; i < text.length(); i++) {
            int idx = Math.abs(text.charAt(i) - 'a');
            if (p.children[idx] == null) {
                TrieNode node = new TrieNode(text.charAt(i));
                p.children[idx] = node;
            }
            p = p.children[idx];
        }
        p.isEndingChar = true;
    }

    public boolean find(String pattern) {
        TrieNode p = this.root;
        for (int i = 0; i < pattern.length(); i++) {
            int idx = Math.abs(pattern.charAt(i) - 'a');
            if (p.children[idx] == null) {
                return false;
            }
            p = p.children[idx];
        }
        return p.isEndingChar;
    }

    /**
     * 根据pattern返回匹配的[单词或者词语]列表
     * @param pattern input string pattern
     * @return 返回匹配pattern的所有结果集
     * eg: pattern : str => [string, strike, structure]
     */
    public List<String> showOptional(String pattern) {
        TrieNode p = this.root;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < pattern.length(); i++) {
            int idx = Math.abs(pattern.charAt(i) - 'a');
            if (p.children[idx] == null) {
                return list;
            }
            p = p.children[idx];
        }
        StringBuilder result = new StringBuilder(pattern);
        recurCollect(p, result, list);
        return list;
    }

    private void recurCollect(TrieNode p, StringBuilder result, List<String> list) {
        if (p == null) {
            return;
        }
        if (p.isEndingChar) {
            list.add(result.toString());
        }
        TrieNode[] tns = p.children;
        if (tns != null && Arrays.stream(tns)
                .filter(Objects::nonNull).toList().size() != 0) {
            for (int i = 0; i < p.children.length; i++) {
                TrieNode tn = p.children[i];
                if (tn != null) {
                    Character c = tn.data;
                    result.append(c);
                    recurCollect(tn, result, list);
                    result.delete(result.length() - 1, result.length());
                }
            }
        }
    }

    public static void main(String[] args) {
        TrieTree tt = new TrieTree();
        tt.insert("string");
        tt.insert("strike");
        tt.insert("structure");
        tt.insert("another");
        tt.insert("and");
        tt.insert("assume");
        tt.insert("analyze");
        System.out.println(tt.find("and"));
        System.out.println(tt.showOptional("str"));
        System.out.println(tt.showOptional("an"));
    }
}
