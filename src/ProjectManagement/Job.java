package ProjectManagement;

public class Job implements Comparable<Job>, JobReport_ {

    public String name, proj, user;
    int runtime, endtime, priority, budget, arrival, complete;

    public Job(String n, String pj, String us, int run, int end, int prior, int budg, int arrivalt, int complete) {
        this.name = n;
        this.proj = pj;
        this.user = us;
        this.runtime = run;
        this.endtime = end;
        this.priority = prior;
        this.budget = budg;
        this.arrival = arrivalt;
        this.complete = complete;
    }

    public String user() {
        return user;
    }

    public String project_name() {
        return proj;
    }
    
    public int budget() {
        return budget;
    }
    
    public int arrival_time() {
        return arrival;
    }
    
    public int completion_time() {
        complete = endtime;
        return complete;
    }

    @Override
    public int compareTo(Job job) {
        if(priority > job.priority) {
            return 1;
        }
        if (priority < job.priority) {
            return -1;
        }
        return 0;
    }

    public String toString() {
        return name;
    }
}