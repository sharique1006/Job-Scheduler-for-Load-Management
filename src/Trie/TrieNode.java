package Trie;


import Util.NodeInterface;
import java.util.ArrayList;

public class TrieNode<T> implements NodeInterface<T> {

    public T person;
    public String value;
    public int level;
    public TrieNode <T> nextlevel[] = new TrieNode[95];
    
    TrieNode() {
        value = null;
        person = null;
        level = 0;
        for (int i = 0; i < 95; i++) {
            nextlevel[i] = null;
        }
    }

    @Override
    public T getValue() {
        return person;
    }
}