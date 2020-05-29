package PriorityQueue;

import java.util.ArrayList;
import java.util.LinkedList;

public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {

    public LinkedListNode newNode(T stud)  {  
        LinkedListNode temp = new LinkedListNode();  
        temp.student = stud;   
        temp.next = null;  
        return temp;  
    } 

    public LinkedListNode head = newNode(null);

    public LinkedListNode push(LinkedListNode head, T d)  {  
        LinkedListNode start = head; 
        LinkedListNode temp = newNode(d);  

        if (head.student.compareTo(d) < 0) {   
            temp.next = head;  
            head = temp;  
        }  
        else {   
            while (start.next != null && start.next.student.compareTo(d) >= 0)  {  
                start = start.next;  
            }   
        temp.next = start.next;  
        start.next = temp;  
        }  
        return head; 
    }    

    @Override
    public void insert(T element) {
        if(head == null || head.student == null) {
            head = newNode(element);
        }
        else {
            head = push(head, element);
        }
    }

    @Override
    public T extractMax() {
        LinkedListNode max = head;
        if(head == null) {
            return null;
        }
        head = head.next;
        return (T) max.student;
    }

    public boolean search(String sch) {
        LinkedListNode start = head;
        if (start == null) {
            return false;
        }
        else {
            while (start != null && start.student != null) {
            if (start.student.toString().compareTo(sch) == 0) {
                return true;
            }
            start = start.next;
        }
        return false;
        }
    }
}
