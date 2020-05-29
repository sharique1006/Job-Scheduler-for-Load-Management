package PriorityQueue;

public class Student implements Comparable<Student> {
    String name, max, s1, s2;
    int marks;

    public Student(String trim, int parseInt) {
        this.name = trim;
        this.marks = parseInt;
    }

    public String toString() {
        max = "Student{name=" + "'" + name + "'" + "," + " " + "marks=" + marks + "}"; 
        return max;
    }

    @Override
    public int compareTo(Student student) {
        if(this.marks > (student.marks) ) {
            return 1;
        }
        else if (this.marks == (student.marks)) {
            return 0;
        }
        else {
            return -1;
        }
    }

    public String getName() {
        return name;
    }
}
