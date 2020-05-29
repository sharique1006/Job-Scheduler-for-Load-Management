package ProjectManagement;

public class User implements Comparable<User>, UserReport_ {

    String kuser;
    int consumed;
    int lastjobtime;

    public User(String u, int consumed, int timeoflastjob) {
        this.kuser = u;
        this.consumed = consumed;
        this.lastjobtime = timeoflastjob;
    }

    public String user() {
        return kuser;
    }

    public int consumed() {
        return consumed;
    }

    @Override
    public int compareTo(User user) {
        return kuser.compareTo(user.kuser);
    }

    public String toString() {
        return "User{name='" + kuser + "', usage=" + consumed +"}";
    }
}
