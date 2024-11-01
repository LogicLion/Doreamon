package com.example.lib;

/**
 * @author wzh
 * @date 2024/6/17
 */
class WordDictionary {
    Trie trie;

    public WordDictionary() {
        Trie trie = new Trie();
    }

    public void addWord(String word) {
        trie.insert(word);
    }

    public boolean search(String word) {

        return dfs(word, 0, trie);
    }

    public boolean dfs(String word, int i, Trie curr) {

        if (i == word.length()) {
            return curr.isEnd;
        }
        char c = word.charAt(i);

        if (Character.isLetter(c)) {
            Trie child = curr.children[c - 'a'];
            if (child != null) {
                return dfs(word, i + 1, child);
            } else {
                return false;
            }
        } else {
            for (int j = 0; j < 26; j++) {
                Trie child = curr.children[j];
                if (curr.children[j] != null && dfs(word, i + 1, child)) {
                    return true;
                }
            }

        }
        return false;
    }





}
