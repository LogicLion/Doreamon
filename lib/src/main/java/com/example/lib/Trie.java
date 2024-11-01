package com.example.lib;

/**
 * @author wzh
 * @date 2024/6/4
 */
class Trie {

    Trie[] children;
    boolean isEnd;
    String word;

    public Trie() {
        children = new Trie[26];
        isEnd = false;
        word = "";

    }

    public void insert(String word) {
        Trie curr = this;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (curr.children[c] == null) {
                curr.children[c] = new Trie();
            }
            curr = curr.children[c];
        }
        curr.isEnd = true;
        curr.word = word;
    }

    public boolean search(String word) {
        Trie curr = this;

        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (curr.children[c] == null) {
                return false;
            }
            curr = curr.children[c];
        }
        return curr.isEnd;
    }

    public boolean startsWith(String prefix) {

        Trie curr = this;
        for (int i = 0; i < prefix.length(); i++) {
            int c = prefix.charAt(i) - 'a';
            if (curr.children[c] == null) {
                return false;
            }
            curr = curr.children[c];
        }
        return true;
    }


}
