package ProjectManagement;

import PriorityQueue.MaxHeap;
import PriorityQueue.PriorityQueueDriverCode;
import Trie.Trie;
import Trie.TrieDriverCode;
import Trie.TrieNode;
import RedBlack.RBTree;
import RedBlack.RedBlackDriverCode;
import RedBlack.RedBlackNode;
import java.util.Queue;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class Scheduler_Driver extends Thread implements SchedulerInterface {

    public static void main(String[] args) throws IOException {

        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File commandFile) throws IOException {


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                    //--------- New Queries

                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        timed_report(cmd);
                        break;
                    case "NEW_TOP":
                        qstart_time = System.nanoTime();
                        timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_FLUSH":
                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }

            }
            run_to_completion();
            print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }

    public int jobsremaining = 0;
    public int globaltime = 0;
    public int arrivaltime = 0;
    Trie<Project> TR = new Trie();
    MaxHeap<Job> mHeap = new MaxHeap();
    RBTree<String, User> rbt = new RBTree();
    RBTree<String, Job> rbt2 = new RBTree();
    ArrayList<Job> finishedjobs = new ArrayList();
    ArrayList<Job> unfinishedjobs = new ArrayList();
    Queue<RedBlackNode> q = new LinkedList();
    RedBlackNode<String, Job> x;
    MaxHeap<Job> alljobsproject = new MaxHeap();
    MaxHeap<Job> alljobsusers = new MaxHeap();
    MaxHeap<Job> alljobsprojectuser = new MaxHeap();
    Queue<RedBlackNode> qprior = new LinkedList();
    RedBlackNode<String, Job> xprior;
    ArrayList<User> allusers = new ArrayList();

    @Override
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        long qstart_time, qend_time;
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_USER":
                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                break;
            case "NEW_PROJECTUSER":
                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_PRIORITY":
                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
        }
        return res;
    }

    public void timed_handle_user(String name){
        User us = new User(name, 0, 0);
        rbt.insert(name , us);
        allusers.add(us);
    }

    public void timed_handle_job(String[] cmd) {
        TrieNode<Project> p = TR.search(cmd[2]);
        RedBlackNode<String, User> jp = rbt.search(cmd[3]);
        if(p == null || p.person == null) {}
        else if(jp == null || jp.getValues() == null) {}
        else {
            int pt = p.person.priority;
            int bud = p.person.budget;
            Job kjob = new Job(cmd[1], cmd[2], cmd[3], Integer.parseInt(cmd[4]), 0 ,pt, bud, arrivaltime, 0);
            mHeap.insert(kjob);
            alljobsproject.insert(kjob);
            alljobsusers.insert(kjob);
            alljobsprojectuser.insert(kjob);
            jobsremaining++;
        }
    }

    public void timed_handle_project(String[] cmd){
        Project pj = new Project(cmd[1], Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
        TR.insert(cmd[1], pj);
    }

    public void timed_run_to_completion(){
        while (mHeap.head != null) {
            int count = 0;
            while (count != 1) {
                Job j1 = mHeap.extractMax();
                int runtime = j1.runtime;
                TrieNode<Project> X = TR.search(j1.proj);
                if(X.person.budget < runtime) {
                    rbt2.insert(X.person.name, j1);
                    jobsremaining--;
                }
                else {
                    X.person.budget = X.person.budget - runtime;
                    jobsremaining--;
                    globaltime = globaltime + runtime;
                    j1.endtime = globaltime;
                    finishedjobs.add(j1);
                    count++;
                }
            }
        }
        q.add(rbt2.root); 
        while(q.peek()!=null) {    
            x = (RedBlackNode) q.remove();
            if(x.left!=null)    
            q.add(x.left);
            if(x.right!=null)    
            q.add(x.right);
             
            for (Job jh : x.getValues()) {
                unfinishedjobs.add(jh);
            }
        }
    }

    @Override
    public ArrayList<UserReport_> timed_top_consumer(int top) {
        System.out.println("Top query");
        ArrayList<UserReport_> userreptop = new ArrayList();

        User tempus;
        for (int i = 0; i < allusers.size(); i++) {
            for (int j = 0; j < allusers.size(); j++) {
                if(allusers.get(i).consumed() > allusers.get(j).consumed()) {
                    tempus = allusers.get(i);
                    allusers.set(i, allusers.get(j));
                    allusers.set(j, tempus);
                }
                if(allusers.get(i).consumed() == allusers.get(j).consumed()) {
                    if(allusers.get(i).lastjobtime < allusers.get(j).lastjobtime) {
                        tempus = allusers.get(i);
                        allusers.set(i, allusers.get(j));
                        allusers.set(j, tempus);
                    }
                }
            }
        }

        if (allusers.size() < top) {
            for (int m = 0; m < allusers.size(); m++) {
                userreptop.add(allusers.get(m));
            }
        }
        else {
            for (int m = 0; m < top; m++) {
                userreptop.add(allusers.get(m));
            }
        }
        return userreptop;
    }


    @Override
    public void timed_flush(int waittime) {
        System.out.println("Flush query");
        ArrayList<Job> templ = new ArrayList();
        ArrayList<String> print = new ArrayList();
        int pushed = 0;
        int temp = globaltime;

        while (mHeap.head != null) {
            Job gh = mHeap.extractMax();
            if (gh != null) {
                RedBlackNode<String, User> jp = rbt.search(gh.user());
                if (jp != null) {
                    TrieNode<Project> mnp = TR.search(gh.proj);
                    int wait = temp - gh.arrival;
                    if(wait >= waittime) {
                        if(mnp.person.budget > gh.runtime) {
                            pushed++;
                            gh.endtime = globaltime + gh.runtime;
                            arrivaltime = arrivaltime + gh.runtime;
                            globaltime = gh.endtime;
                            mnp.person.budget = mnp.person.budget - gh.runtime;
                            jp.getValue().consumed = jp.getValue().consumed + gh.runtime;
                            print.add("Job{user='" + gh.user + "', project='" + gh.proj + "', jobstatus=COMPLETED, execution_time=" + gh.runtime + ", end_time=" + gh.endtime +  ", priority=" + gh.priority+  ", name='" + gh.name + "'}");
                            finishedjobs.add(gh);
                            jobsremaining--;
                        }
                        else {
                            templ.add(gh);
                        }
                    }
                    else {
                        templ.add(gh);
                    }
                }
            }
        }
        //System.out.println("Total pushed: " + pushed);
        for (String fh : print) {
            System.out.println(fh);
        }
        for (Job jb : templ) {
            mHeap.insert(jb);
        }
    }

    private ArrayList<JobReport_> handle_new_priority(String s) {
        System.out.println("Priority query");
        ArrayList<JobReport_> jobprior = new ArrayList();
        ArrayList<Job> templ = new ArrayList();

        qprior.add(rbt2.root); 
        while(qprior.peek()!=null) {    
            xprior = (RedBlackNode) qprior.remove();
            if(xprior.left!=null)    
                qprior.add(xprior.left);
            if(xprior.right!=null)    
                qprior.add(xprior.right);
             
            for (Job gh : xprior.getValues()) {
                if(gh != null) {
                    if (gh.priority > Integer.parseInt(s)) {
                        jobprior.add(gh);
                    }
                }
            }
        }
        while (mHeap.head != null) {
            Job gh = mHeap.extractMax();
            templ.add(gh);
            if (gh != null) {
                if(gh.priority > Integer.parseInt(s)) {
                jobprior.add(gh);
                }
            }
        }
        for(Job tr : templ) {
            mHeap.insert(tr);
        }
        return jobprior;
    }

    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) {
        System.out.println("Project User query");
        ArrayList<JobReport_> jobprojuser = new ArrayList();
        while (alljobsprojectuser.head != null) {
            Job gh = alljobsprojectuser.extractMax();
            if(gh != null) {
                if (gh.proj.compareTo(cmd[1]) == 0 && gh.user.compareTo(cmd[2]) == 0 && gh.arrival >= Integer.parseInt(cmd[3]) && gh.arrival <= Integer.parseInt(cmd[4])) {
                    jobprojuser.add(gh);
                }
            }
        }
        return jobprojuser;
    }

    private ArrayList<JobReport_> handle_new_user(String[] cmd) {
        System.out.println("User query");
        ArrayList<JobReport_> jobuser = new ArrayList();
        while (alljobsusers.head != null) {
            Job gh = alljobsusers.extractMax();
            if(gh != null) {
                if (gh.user.compareTo(cmd[1]) == 0 && gh.arrival >= Integer.parseInt(cmd[2]) && gh.arrival <= Integer.parseInt(cmd[3])) {
                    jobuser.add(gh);
                }
            }
        }
        return jobuser;
    }

    private ArrayList<JobReport_> handle_new_project(String[] cmd) {
        System.out.println("Project query");
        ArrayList<JobReport_> jobproj = new ArrayList();
        while (alljobsproject.head != null) {
            Job gh = alljobsproject.extractMax();
            if (gh != null) {
                if (gh.proj.compareTo(cmd[1]) == 0 && gh.arrival >= Integer.parseInt(cmd[2]) && gh.arrival <= Integer.parseInt(cmd[3])) {
                    jobproj.add(gh);
                }
            }
        }
        return jobproj;
    }


    public void schedule() {
            execute_a_job();
    }

    @Override
    public void run_to_completion() {
        while (mHeap.head != null) {
            System.out.println("Running code");
            System.out.println("Remaining jobs: " + jobsremaining);
            int count = 0;
            while (count != 1) {
                Job j1 = mHeap.extractMax();
                int runtime = j1.runtime;
                TrieNode<Project> X = TR.search(j1.proj);
                if(X.person.budget < runtime) {
                    rbt2.insert(X.person.name, j1);
                    System.out.println("Executing: " + j1.name + " " + "from: " + X.person.name);
                    System.out.println("Un-sufficient budget.");
                    jobsremaining--;
                }
                else {
                    X.person.budget = X.person.budget - runtime;
                    System.out.println("Executing: " + j1.name + " " + "from: " + X.person.name);
                    System.out.println("Project: " + X.person.name + " " + "budget remaining: " + X.person.budget);
                    System.out.println("System execution completed");
                    jobsremaining--;
                    globaltime = globaltime + runtime;
                    j1.endtime = globaltime;
                    finishedjobs.add(j1);
                    count++;
                }
            }
        }
        q.add(rbt2.root); 
        while(q.peek()!=null) {    
            x = (RedBlackNode) q.remove();
            if(x.left!=null)    
            q.add(x.left);
            if(x.right!=null)    
            q.add(x.right);
             
            for (Job jh : x.getValues()) {
                unfinishedjobs.add(jh);
            }
        }
    }

    @Override
    public void print_stats() {
        System.out.println("--------------STATS---------------");
        System.out.println("Total jobs done: " + finishedjobs.size());
        for (Job jb : finishedjobs) {
            System.out.println("Job{user='" + jb.user + "', project='" + jb.proj + "', jobstatus=COMPLETED, execution_time=" + jb.runtime + ", end_time=" + jb.endtime + ", priority=" + jb.priority + ", name='" + jb.name + "'}");
        }
        System.out.println("------------------------");
        System.out.println("Unfinished jobs: ");


        for (Job jb : unfinishedjobs) {
            System.out.println("Job{user='" + jb.user + "', project='" + jb.proj + "', jobstatus=REQUESTED, execution_time=" + jb.runtime + ", end_time=null" + ", priority=" + jb.priority  +  ", name='" + jb.name + "'}");
        }

        System.out.println("Total unfinished jobs: " + unfinishedjobs.size());
        System.out.println("--------------STATS DONE---------------");
    }

    @Override
    public void handle_add(String[] cmd) {
        System.out.println("ADDING Budget");
        TrieNode<Project> tc = TR.search(cmd[1]);
        if(tc != null && tc.person != null) {
            tc.person.budget = tc.person.budget + Integer.parseInt(cmd[2]);
            RedBlackNode<String, Job> rv = rbt2.search(cmd[1]);
            if(rv != null && rv.getValues() != null) {
                while(rv.getValues().size() != 0 ) {
                    mHeap.insert(rv.getValues().remove(0));
                    jobsremaining++;
                }
            }
        }
    }

    @Override
    public void handle_empty_line() {
        System.out.println("Running code");
        System.out.println("Remaining jobs: " + jobsremaining);
        int count = 0;
        while (count != 1 ) {
            Job j1 = mHeap.extractMax();
            RedBlackNode<String, User> jp = rbt.search(j1.user());
            int runtime = j1.runtime;
            TrieNode<Project> X = TR.search(j1.proj);
            if(X.person.budget < runtime) {
                rbt2.insert(X.person.name, j1);
                System.out.println("Executing: " + j1.name + " " + "from: " + X.person.name);
                System.out.println("Un-sufficient budget.");
                jobsremaining--;
            }
            else {
                X.person.budget = X.person.budget - runtime;
                System.out.println("Executing: " + j1.name + " " + "from: " + X.person.name);
                System.out.println("Project: " + X.person.name + " " + "budget remaining: " + X.person.budget);
                System.out.println("Execution cycle completed");
                jobsremaining--;
                globaltime = globaltime + runtime;
                arrivaltime = arrivaltime + runtime;
                j1.endtime = globaltime;
                jp.getValue().lastjobtime = globaltime;
                jp.getValue().consumed = jp.getValue().consumed + j1.runtime;
                finishedjobs.add(j1);
                count++;
            }
        }
    }


    @Override
    public void handle_query(String key) {
        System.out.println("Querying");
        RedBlackNode<String, Job> d = rbt2.search(key);
        if (d != null && d.getValues() != null) {
            for (Job j : d.getValues()) {
                if(key.compareTo(j.name) == 0) {
                    System.out.println(key + ": NOT FINISHED");
                    return;
                }
            }
        }
        else {
            for (int i = 0; i < finishedjobs.size(); i++) {
                if(finishedjobs.get(i).name.compareTo(key) == 0) {
                    System.out.println(key + ": COMPLETED");
                    return;
                }
            }
            if(mHeap.head != null && mHeap.search(key) == true) {
                System.out.println(key + ": NOT FINISHED");
                return;
            }
        }
        System.out.println(key + ": NO SUCH JOB");
    }

    @Override
    public void handle_user(String name) {
        System.out.println("Creating user");
        User us = new User(name, 0, 0);
        rbt.insert(name , us);
        allusers.add(us);
    }

    @Override
    public void handle_job(String[] cmd) {
        System.out.println("Creating job");
        TrieNode<Project> p = TR.search(cmd[2]);
        RedBlackNode<String, User> jp = rbt.search(cmd[3]);
        if(p == null || p.person == null) {
            System.out.println("No such project exists. " + cmd[2]);
        }
        else if(jp == null || jp.getValues() == null) {
            System.out.println("No such user exists: " + cmd[3]);
        }
        else {
            int pt = p.person.priority;
            int bud = p.person.budget;
            Job kjob = new Job(cmd[1], cmd[2], cmd[3], Integer.parseInt(cmd[4]), 0 ,pt, bud, arrivaltime, 0);
            mHeap.insert(kjob);
            alljobsproject.insert(kjob);
            alljobsusers.insert(kjob);
            alljobsprojectuser.insert(kjob);
            jobsremaining++;
        }
    }

    @Override
    public void handle_project(String[] cmd) {
        System.out.println("Creating project");
        Project pj = new Project(cmd[1], Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]));
        TR.insert(cmd[1], pj);
    }

    public void execute_a_job() {

    }
}
