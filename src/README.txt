SHARIQUE SHAMIM
2018CS10388
COL106 - Assignment 5

In this assignment you need to work with Tries, Red-Black trees and Priority queues. 
There will be four components of the assignment. 
The first three will check tries, red-black trees and priority queues independently. 
The last part of the assignment will be a combination of all the previous components.

***Trie***

The Data Structure Trie has various methods such as insert, search, match, startswith, printlevel, print.
I have used a TrieNode to implement Trie.java
In TrieNode I have made an array of TrieNodes of size 95 which stores level, the person & the character in form of string.
Initially the array, person & value have been assigned null.

A public TrieNode called "root" which is the 0th level of the Trie is declared & all functions are implemented starting from the root.

1. boolean insert(String word, Object value) :
	When inserting any word i start from the root and find the index corresponding to each character in the word.
	If the nextlevel of that TrieNode is null make a new TrieNode and refer that Node to nextNode.
	Since we are also storing the level of trie, make the level as i+1, and store the value as that index to be the character of the word.
	
	else if the nextlevel of that TrieNode is not null, simply go to the nextlevel.
	Do this for all characters for the word.
	Also store the Person Object at each TrieNode if it is null.

2. TrieNode search(String word) :
	Starting searching from the root using searchNode(word) function if the TrieNode found is null or its level not equal to the length of the word being searched for return null.
	else return that TrieNode.

3. TrieNode searchNode(String word) :
	Starting from the root, find the index corresponding to each character of the word.
	if the index in the nextlevel of the trienode is not null && there is some value at that index go to the nextlevel TrieNode.
	Keep doing this for all the characters of the word and return the last trienode reached.

4. TrieNode startsWith(String prefix) :
	Starting searching from the root using searchNode(word) function if the TrieNode found is null or its level not equal to the length of the word being searched for return null.
	else return that TrieNode.

5. printTrie(TrieNode trieNode) :
	The TrieNode being returned by the Startswith function is used to print all the persons whose name starts with prefix.
	To print, use an ArrayList because we need to print all the persons in sorted manner.
	Since each TrieNode is an array of length 95, iterate over the array and the indexes which are not null & also have a person stored at that index, add them to the list, do this recursively
	as there may be multiple people with the same name.
	Sort the ArrayList and print all the names.

6. boolean delete(String word):
	First search for the word in the Trie starting from the root, if its null or the node found is not complete as the word because only some part of the word may match
	as there may be multiple people with some part of their names matching so return false,
	if the word to be deleted is there,
	start from the root and we first find that level at which more than one character is stored because there may be more than one person having the same start in their names
	& thus only the latter part which is unique for this person is to be deleted.
	Once we find the TrieNode from which all has to be deleted put the values at that position as null,
	so they are no more in the Trie.

7. String[] printR (TrieNode<T> thisN, String k, String[] jkl) :
	I have already stored the maxlevel possible by declaring a global integer which stores the length of that word.
	Declare an array of the max level size and start storing the characters at each level in the string.
	But it is possible that for some particular level, or that index in that array a string is already present
	so to avoid loss of string set a bool function and first find if the index/level in the array is null or not,
	if it is null simply add the string to k and put it in the array
	else if it is not null take the string drom that index in the array & add the characters to it.
	After completion return the array.

8. void printLevel(int level) :
	Here it is required to print all the characters at the given level "level" in the required format.
	Using the printR function I can have the required characters of each level
	Simply print the fgh[level] as required by sorting the strings.

9. void print() :
	Here it is required to print all the levels of trie.
	Using the printR function i have all the levels.
	Simply sort each level and print it in the required format.
	Finally print an extra level to show that the Trie has ended.

***Red Black Tree***

The Data Structure Red Black Tree has two methods insert & search.
I have used a RedBlackNode to implement RBTree.java
In the RedBlackNode declare left, right, color, value, key & an arraylist of all values.

**RedBlackNode**
Their are two constructors one for a normal RedBlackNode & one for the nullNode.

---nullNode = It is a RedBlackNode with 3 parameters value & key as null and boolean color as false to differentiate it from other RedBlackNodes.

1. void insert(T key, E value) :
	start from the root to insert using the insertRec function.

2. RedBlackNode insertRec(RedBlackNode thisNode, T key, E value) :
	if the node is null, create a new RedBlackNode there.
	if the key matches to the key at the current Node add the value to the list at that RedBlackNode.
	if the key is less than the key at that node go left.
	if the key is more than the key at that node go right.

3. restructure(RedBlackNode thisNode)
	Now we need to restructure & change the colors of node of the RB Tree to meet the properties of RedBlackTree
	--if the right child of the node is red & left child is black
		rotateleft the node and swap the colors of parent and left.
	--if the left child of the node is red and left child of the left child is also Red
		rotate right the node & swap the colors of the parent and right child.
	--if the left & right child both are Red then reverse the color of the parent & set the color of left & right as black.

3. RedBlackNode<T, E> search(T key) :
	start searching from the root using searchRec function
	if the Node found is null return a nullNode.
	else return that RedBlackNode. 

4. RedBlackNode searchRec(RedBlackNode myNode, T key) : 
	search function for this is same as Binary Search Tree
	if the key is same as the key at that node return the node
	if the key is less than than the key at that node go left
	if the key is more than the key at that node go right.

***Priority Queue***

I have implemented MaxHeap using a LinkedList.
In the LinkedListNode.java I made a T student & a LinkedListNode next.

Make a LinkedListNode head and initialise as null.
Constructor takes T as argument.

1. insert() :
	if head is null or head.student is null then
		create a newNode()
	else just call push(head, element)

2. push(Node head, T element) :
	if(marks are less than just point it to next)
	but if marks are most search in the whole list and insert where the element just greater than it is found.
	and point to next.
	return head

3. extractMax() :
	if head is null return null
	else return head as it is maximum and do head as head.next

4. search(String s) :

	if head is null return false;
	else search in the whole list if found return true else false.

***Project Management***

The Project Management has a UserReport_, JobReport_, SchedulerInterface whicha are implemented by User, Job, Scheduler_Driver.java 

1. User.java
	It implements the UserReport_ Interface
	It has functions as :
		1.user() : returns the name of the user.
		2.consumed() : returns the total consumption for that user.
	Constructor of User takes user, consumed and lastjobtime as arguments
	where lastjobtime equals the globaltime of the last executed job for that user.
	I made lastjobtime because we need to sort the users in timed_top_consumer according to their latest executed job.

2. Job.java
	It implements the JobReport_ Interface
	It has functions as:
		1.user() : returns the name of user for that job.
		2.project_name() : returns the name of the project for that job
		3.budget() : returns the budget of that job as given while making the project of the job.
		4.arrival_time() : returns the time of arrival of the job, i.e when the job was created.
		5.completion_time() : returns the time of completion of the job, i.e when the job was executed.
		6.compareTo() : compares the job priority and returns -1,0,1 accordingly.
	Constructor of Job takes name of job, project name, user name, runtime, endtime, priority, 
	budget, arrivaltime, completion time as arguments.

3. Project.java
	This class has been made to create an object of type project whenever a project is created.
	It takes name of project, budget and priority of the project as arguments.

4. Scheduler_Driver.java
This is like the heart of the assignmet and was fun doing it.
It imports all the three Data structures Trie, Red Black Tree & MaxHeap created in Assignment4
This has various functions and are called as per the kind of line encountered in input line.

To store all the data and use them as required I have initilised various data structures as:
	1.mHeap : A MaxHeap which Stores all the Jobs and Jobs are taken out from it while execution.
	2.TR : A Trie Stores all the Projects given in input file.
	3.rbt : A RedBlackTree which Stores all users.
	4.rbt2: A RedBlackTree which stores all jobs which could not be ececuted when extracted from mHeap
		using extractMax() due to unsufficient budget are stored here.
	5.finishedjobs : An ArrayList in which Jobs which had sufficient budget to be executed when extracted from
			mHeap are stored.
	6.q, qprior : A Queue of RedBlackNode to do the traversal of the RedBlackTree
	7.x, xprior : A Helper RedBlackNode while doing the traversal of RedBlackTree
	8.alljobs : A MaxHeap which stores all the jobs created.
	9.allusers : An ArrayList which stores the list of all users created.

1. handle_user(String name) :
	Create a User Object & Insert the user in RedBlackTree rbt by calling the insert function 
	of the RBT.

2. handle_project(String[] cmd) :
	Create a Project object and insert the Project in Trie by calling its insert function.

3. handle_job(String[] cmd) :
	--First search if a project with name given in cmd exist in the Trie(TR) or not.
	  Also search if a user with name given in cmd exist in the RBT(rbt) or not. 
	--If the project does not exist print No such project exist.
	--If the user does not exist print No such user exist.
	--But if both the user and the project exist then find the budget and priority of the job by using the TrieNode obtained while 
	  searching and Create a Job Object and insert it into the mHeap(Max Heap of Jobs) and increased the count of jobsremaining by 1
	  as now the count of number of jobs to be executed increases by 1.

4. handle_query(String key) :
	A query could be either in rbt2(all unexecuted jobs due to insuffiecient budget) or
	finishedjobs(ArrayList of executed jobs) or mHeap(Jobs which haven't been touched yet)
	--Search for the key in all the 3 data structures and print statements accordingly if found or not.

5. handle_empty_line() :
	Whenever an empty line is encountered in the input file one job with sufficient budget based on priority and FIFO
	has to be executed.
	Extract a Job from maxHeap -- Check if it has sufficient budegt to be executed
	If No --put that job in rbt2 and keep extracting from this and repeat this until a job with suffiecient job is encountered.
	When a job with sufficient budget is found increase the globaltime, change its endtime, change the consumption of user of that job,
	add it to the list of finished jobs and also change the arrivaltime for upcoming jobs nd decrease the count of jobsremaining by 1.

6. handle_add(String[] cmd) :
	This function is used to increase the budget of a given project
	Search for the project in the Trie(TR)--- if found increase the budget of that project and 
	since now its budget has been increased so it may be executed so put it back in the MaxHeap

7. run_to_completeion() :
	--Execute all the jobs present in the MaxHeap if it has sufficient budget and add it to the list
	  of finished jobs.
	  If it does not have sufficient budget put that job in rbt2.
	 Also keep changing all parameters as jobsremaining, globaltime, endtime of the job etc.
	--Keep doing the above until the MaxHeap is empty so that there are no more jobs.
	--Jobs which could not be executed due to unsufficient budget in a list of unfinished jobs from rbt2 by
	doing the traversal of rbt2 using queue.

8. print_stats() : 
	Print in the required format all parameters of the finished and unfinished jobs.

9. handle_new_project(String[] cmd) :
	Return a list of all jobs which have their project name as given in cmd and their arrivaltime is between T1 & T2 given in cmd.
	First take out all jobs from MaxHeap alljobs put all the jobs meeting required parameters in ArrayList<JobReport_>
	and then putback all the jobs in alljobs MaxHeap.

10. handle_new_user(String[] cmd) :
	Return a list of all jobs which have their user name as given in cmd and their arrivaltime is between T1 & T2 given in cmd.
	First take out all jobs from MaxHeap alljobs put all the jobs meeting required parameters in ArrayList<JobReport_>
	and then putback all the jobs in alljobs MaxHeap.

11. handle_new_projectuser(String[] cmd) :
	Return a list of all jobs which have their project name and user name as given in cmd and their arrivaltime is between T1 & T2 given in cmd.
	First take out all jobs from MaxHeap alljobs put all the jobs meeting required parameters in ArrayList<JobReport_>
	and then putback all the jobs in alljobs MaxHeap. Now since the jobs are taken from the all jobs maxHeap according to priority its already sorted.

12.handle_new_priority(String s) :
	We need to return the list of waiting jobs which have priority greater than int(s)
	waiting jobs may be present in rbt2 and maxHeap. 
	Check both of them and add all jobs with priority greater than int(s) ArrayList<JobReport_>

13. timed_flush(int waittime) :
	All jobs present in mHeap with sufficient budget and which have been waiting for time greater than equal to waittime needs to be executed.
	wait = globaltime(at that instant when flush is called) - arrivaltime of that job.
	If sufficient budget execute it, change all parameters as endtime, globaltime, arrivaltime for upcoming jobs and budget.
	If the budget is insufficient add it back to the mHeap.
	Print the count of flushed jobs and the parameters of all flushed jobs.

14. timed_top_consumer(int top) : 
	All users have certain consumption
	where consumption of any user = sum of runtimes of all executed jobs.
	We need to return the Top "top" users.
	But if there are users have same consumption then compare the lastjobtime for the users. 
	The User whose last job was executed earlier comes first.

