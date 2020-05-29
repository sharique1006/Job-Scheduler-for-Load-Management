package Trie;

import java.util.ArrayList;
import java.util.Collections;

public class Trie<T> implements TrieInterface {

    public TrieNode root;
    public int maxlevel = 0;

    public Trie() {
        root = new TrieNode();
    }


    @Override
    public boolean delete(String word) {

        TrieNode<T> p = root;
        TrieNode<T> temp = new TrieNode();
        int index, r, tempindex = 0,count = 0, counter = 0;
        boolean split = false; 

        TrieNode<T> Z = searchNode(word);
        if (Z == null || Z.value == null || Character.toString(word.charAt(word.length() - 1)).compareTo(Z.value) != 0) {
            return false;
        }
        else {
            for (int i = 0; i < word.length(); i++) {
                count = 0;
                index = word.charAt(i) - 32;
                if(p.nextlevel[index] != null) {
                    for (int j = 0; j < 95; j++) {
                        if(p.nextlevel[j] != null) {
                            count++;
                        }
                    }
                    if (count == 1) {
                        p = (TrieNode) p.nextlevel[index];
                    }
                    if(count == 2) {
                        temp = p;
                        tempindex = index;
                        if(split == true) {}
                        else {
                            p = (TrieNode) p.nextlevel[index];
                        }
                        split = true;
                    }
                    else if (count > 2) {
                        count = 0;
                        p = (TrieNode) p.nextlevel[index];
                        split = false;
                    }
                }
            } 
            if (p.level != word.length()) {
                TrieNode<T> X = (TrieNode) temp.nextlevel[tempindex];
                word = word.substring(X.level, word.length());
                for (int i = 0; i < word.length(); i++) {
                    index = word.charAt(i) - 32;
                    X = X.nextlevel[index];
                    X.value = null;
                    X.person = null;
                }
            } 
            else{
                for (int u = 0; u < 95; u++) {
                    if(p.nextlevel[u] != null) {
                        counter++;
                    }
                }
                if(counter == 0) {
                    TrieNode<T> X = (TrieNode) root.nextlevel[word.charAt(0) - 32];
                    X.value = null;
                    X.person = null;
                }
                else {
                    p.person = null;
                }
            }
            return true;
        }
    }

    public TrieNode searchNode(String word) {
        TrieNode<T> lev = root;
        int index = 0;
        for (int i = 0; i < word.length(); i++) {
            index = word.charAt(i) - 32;
            if(lev.nextlevel[index] != null && lev.nextlevel[index].value != null) {
                lev = (TrieNode) lev.nextlevel[index];
            }
        }
        return lev;
    }

    @Override
    public TrieNode search(String word) {
        TrieNode<T>  p = searchNode(word);
        if (p == root || p.level != word.length() || p.person == null ) {
            return null;
        }
        else{
            return p;
        }
    }


    @Override
    public TrieNode startsWith(String prefix) { 
        TrieNode<T> lastprefix = searchNode(prefix);
        if (lastprefix == root || lastprefix.level != prefix.length()) {
            return null;
        }
        else {
            return lastprefix;
        }
    }

    @Override
    public void printTrie(TrieNode trieNode) {
        ArrayList<String> all = new ArrayList<>();

        for(int i = 0; i < 95; i++) {
            TrieNode<T> X = trieNode.nextlevel[i];
            if(X != null) {
                if(X.person != null) {
                    all.add(X.person.toString());
                }
                printTrie(X);
            }
        }
        Collections.sort(all);
        for (String names : all) {
            System.out.println(names);
        }
    }

    @Override
    public boolean insert(String word, Object value) {
        TrieNode<T> lev = root;
        if (maxlevel <= word.length()) {
            maxlevel = word.length();
        }
        int index = 0;
        for (int i = 0; i < word.length(); i++) {
            index = word.charAt(i) - 32;
            if(lev.nextlevel[index] == null) {
                TrieNode<T> thislevel = new TrieNode<T>();
                lev.nextlevel[index] = thislevel;
                thislevel.level = i+1;
                thislevel.value = Character.toString(word.charAt(i));
            }
            lev = lev.nextlevel[index];
        }
        if(lev.person == null) {
            lev.person = (T) value;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void printLevel(int level) {
        TrieNode<T> p = root;
        String[] asd = new String[maxlevel + 1];
        String[] fgh = printR(p, "", asd);

        String h = fgh[level];
        String g = h.substring(0, 4);
        if(g.compareTo("null") == 0) {
            h = h.substring(4, h.length()-1);
            String str =sortString(h);
            System.out.println("Level" + " " + level + ": " + str);
        }
        else {
            h = h.substring(0, h.length()-1);
            String str = sortString(h);
            System.out.println("Level" + " " + level + ": " + str);
        }
    }

    public String[] printR (TrieNode<T> thisN, String k, String[] jkl) {
        boolean iskeyp = false;
        for (int lm = 0; lm < jkl.length; lm++) {
            if (jkl[thisN.level] != null) {
                iskeyp = true;
            }
        }
        for (int i = 0; i < 95; i++) {
            TrieNode<T> X = thisN.nextlevel[i];
            if(X != null) {
                if (X.value != null && X.value.compareTo(" ") != 0) {
                    if(iskeyp == true) {
                        String s = jkl[X.level];
                        s = s + X.value + ",";
                        jkl[X.level] = s;
                    }
                    else {
                        k = k + X.value + ",";
                        jkl[X.level] = k;
                    }
                }
                printR((TrieNode) X, "", jkl);
            }
        }
        return jkl;
    }

    public String sortString(String w) {
        String q = w;
        char temp;
        char abc[] = q.toCharArray();

        for (int j = 0; j < q.length(); j= j+2) {
            for (int k = 0; k < q.length(); k= k+2) {
                if(Character.toString(abc[j]).compareTo(Character.toString(abc[k])) <= 0) {
                    temp = abc[j];
                    abc[j]  = abc[k];
                    abc[k] = temp;
                }
            }
        }
        return new String(abc);
    }

    @Override
    public void print() {
        System.out.println("-------------");
        System.out.println("Printing Trie");
        TrieNode<T> p = root;
        int count = 1;
        String[] asd = new String[maxlevel + 1];
        String[] fgh = printR(p, "", asd);

        for (int i = 1; i < fgh.length; i++) {
            String s1 = fgh[i];
            String s2 = s1.substring(0, 4);
            if (s2.compareTo("null") == 0) {
                count++;
                s1 = s1.substring(4, s1.length()-1);
                String s3 =sortString(s1);
                System.out.println("Level" + " " + i + ": " + s3);
            }
            else {
                count++;
                s1 = s1.substring(0, s1.length()-1);
                String s3 = sortString(s1);
                System.out.println("Level" + " " + i + ": " + s3);
            }
        }
        System.out.println("Level" + " " + count + ": ");
        System.out.println("-------------");
    }
}