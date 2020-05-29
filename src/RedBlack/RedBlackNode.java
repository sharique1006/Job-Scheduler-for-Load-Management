package RedBlack;

import Util.RBNodeInterface;
import java.util.ArrayList;

import java.util.List;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {

    public RedBlackNode left, right;
    boolean color;
    E value;
    public T key;
    ArrayList<E> list = new ArrayList<>();

    RedBlackNode(E val, T name) {
        left = right = null;
        this.value = val;
        this.key = name;
        this.list.add(value);
        color = true;
    }

    RedBlackNode(E val, T name, boolean color) {
        left = right = null;
        this.value = val;
        this.key = name;
        this.list=null;
        color = true;
    }

    @Override
    public E getValue() {
        return value;
    }

    @Override
    public List<E> getValues() {
        return list;
    }
}
