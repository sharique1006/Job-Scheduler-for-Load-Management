package Trie;

public class Person {

    String Name, contact, search;

    public Person(String name, String phone_number) {
        this.Name = name;
        this.contact = phone_number;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        search = "[Name:" + " " + Name + "," + " " + "Phone=" + contact + "]";
        return search;
    }
}
  