package pay.point.sample;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

class TrieNode {
    TrieNode[] children;
    boolean isEndOfCode;

    TrieNode() {
        children = new TrieNode[26]; // Assuming codes contain only uppercase letters (A-Z)
        isEndOfCode = false;
    }

    // Recursive method to print the Trie structure
    public void printTrie(String prefix) {
        if (isEndOfCode) {
            System.out.println(prefix);
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            int index = c - 'A';
            if (children[index] != null) {
                children[index].printTrie(prefix + c);
            }
        }
    }
}

 class Trie {
    private TrieNode root;

    Trie() {
        root = new TrieNode();
    }

    public void insert(String code) {
        TrieNode node = root;
        for (char c : code.toCharArray()) {
            int index = c - 'A';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfCode = true;
    }

    public boolean search(String code) {
        TrieNode node = root;
        for (char c : code.toCharArray()) {
            int index = c - 'A';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return node.isEndOfCode;
    }

public class Triecode {
    // ... (rest of the Trie class remains the same as before)

    public void main(String[] args) {
        Trie trie = new Trie();

        // Assuming you have a list of unique codes, add them to the Trie
        Set<String> uniqueCodes = new HashSet<>();
        uniqueCodes.add("ABC123");
        uniqueCodes.add("XYZ456");
        uniqueCodes.add("DEF789");

        for (String code : uniqueCodes) {
            trie.insert(code);
        }

        // Now, you can search for a specific code provided by the user
        String userInputCode = "ABC123";
        if (trie.search(userInputCode)) {
            System.out.println("The code '" + userInputCode + "' is present in the list.");
        } else {
            System.out.println("The code '" + userInputCode + "' is not present in the list.");
        }

        // Print the Trie structure
        System.out.println("Trie structure:");
        trie.root.printTrie("");
    }
}

}