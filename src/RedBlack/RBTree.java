package RedBlack;

import java.util.ArrayList;

public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E>  {

    public RedBlackNode root = null;
    public RedBlackNode nullNode = new RedBlackNode(null, null, false);

    public RedBlackNode LeftRotation(RedBlackNode thisNode) { 
        RedBlackNode child = thisNode.right; 
        RedBlackNode childLeft = child.left; 
        child.left = thisNode; 
        thisNode.right = childLeft; 
        return child; 
    } 

    public RedBlackNode RightRotation(RedBlackNode thisNode) { 
        RedBlackNode child = thisNode.left; 
        RedBlackNode childRight = child.right; 
        child.right = thisNode; 
        thisNode.left = childRight; 
        return child;  
    }

    boolean checkRed(RedBlackNode thisNode) { 
        if (thisNode == null)  
            return false; 
        return (thisNode.color == true); 
    }

    void swapColors(RedBlackNode node1, RedBlackNode node2) { 
        boolean temp = node1.color; 
        node1.color = node2.color; 
        node2.color = temp; 
    } 

    public RedBlackNode restructure(RedBlackNode thisNode) {
        if (checkRed(thisNode.right) && !checkRed(thisNode.left)) {
            thisNode = LeftRotation(thisNode); 
            swapColors(thisNode, thisNode.left); 
        } 
  
        if (checkRed(thisNode.left) && checkRed(thisNode.left.left)) {  
            thisNode = RightRotation(thisNode); 
            swapColors(thisNode, thisNode.right); 
        } 
   
        if (checkRed(thisNode.left) && checkRed(thisNode.right)) { 
            thisNode.color = !thisNode.color; 
            thisNode.left.color = false; 
            thisNode.right.color = false;  
        }

        return thisNode;
    }

    public RedBlackNode insertRec(RedBlackNode thisNode, T key, E value) {
        if(thisNode == null) {
            return new RedBlackNode(value, key);
        }
        if(key.compareTo(thisNode.key) == 0) {
            thisNode.list.add(value);
        } 
        if (key.compareTo(thisNode.key) < 0) { 
            thisNode.left = insertRec(thisNode.left, key, value); 
        }
        else if (key.compareTo(thisNode.key) > 0) {
            thisNode.right = insertRec(thisNode.right, key, value);
        }
        else {
            return thisNode;
        }
        thisNode = restructure(thisNode);  
        return thisNode; 
    }

    @Override
    public void insert(T key, E value) {
        root = insertRec(root, key, value);
    }

    public RedBlackNode searchRec(RedBlackNode thisNode, T key) {
        if(thisNode == null) {
            return thisNode;
        }
        if(key.compareTo(thisNode.key) == 0) {
            return thisNode;
        }
        if(key.compareTo(thisNode.key) < 0) {
            thisNode = searchRec(thisNode.left, key);
        }
        if(key.compareTo(thisNode.key) > 0) {
            thisNode = searchRec(thisNode.right, key);
        }
        return thisNode;
    }

    @Override
    public RedBlackNode<T, E> search(T key) {
        RedBlackNode X = searchRec(root, key);
        if (X == null || X.getValues() == null) {
            return nullNode;
        }
        else {
            return X;
        }
    }
}