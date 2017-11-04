import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class JobSchedule {
	// jobs in arrayList
	ArrayList<Job> jobs;
	
	public JobSchedule() {
		jobs = new ArrayList<Job>();
	}

	public Job addJob(int time) {
		Job job = new Job(time);
		jobs.add(job);
		return job;
	}

	// get job with given index in jobs arraylist
	public Job getJob(int index) {
		return jobs.get(index);
	}

	public int minCompletionTime() {
		Job minJ = this.jobs.get(0);
		for (Job j : jobs) {
			if (j.getStartTime() < 0)
				return -1;
			if (minJ.getStartTime() + minJ.getTimeToFinish() < j.getStartTime() + j.getTimeToFinish())
				minJ = j;
		}
		return minJ.getStartTime() + minJ.getTimeToFinish();
	}

	class Job {
		ArrayList<Job> outGoing;
		int inDegree;
		int dummy;
		int timeToFinish;
		ArrayList<Job> requireJobs;
		int startTime = 0;
		boolean finished = false;
		Job pi ;
		int i ;

		protected Job(int time) {
			
			timeToFinish = time;
			outGoing = new ArrayList<Job>();
			startTime = 0;
			finished = false;
			pi = null;
			inDegree = 0;
			i = jobs.size();
		}
		
		public void helper(Job j)
		{
			if(this.pi == null || (this.pi.startTime + this.pi.timeToFinish) < (j.startTime + j.timeToFinish))
			{
				this.pi = j;
				
				this.startTime = j.startTime + j.timeToFinish;
				for(Job t: this.outGoing)
				{
					t.helper(this);
				}
			}
		}

		public void requires(Job j) {
			j.outGoing.add(this);
		
		if(this.pi == null || (this.pi.startTime + this.pi.timeToFinish) < (j.startTime + j.timeToFinish))
		{
			this.helper(j);
		}

			
			
			
			
			
			
			
//			requireJobs.add(j);
//			j.outGoing.add(this);
//			this.inDegree++;
//			DAG();
		}

		int getTimeToFinish() {
			return timeToFinish;
		}

		public int getStartTime() {
			
			boolean loop = true;
			
			boolean[] member = new boolean[jobs.size()];
			Arrays.fill(member, Boolean.FALSE);
			Job j = this.pi;
			while(j != null )
			{
				if(member[j.i]  == true)
				{
					this.startTime = Integer.MIN_VALUE;
					break;
				}
				member[j.i] = true;
				j = j.pi;
			}
			
			
			if (startTime < 0)
				startTime = -1;
			return startTime;
		}
	}
	
	

	void DAG() {
		ArrayList<Job> vert = TopOrder(jobs);
		for (Job u : vert) {
			u.finished = true;
			for (Job out : u.outGoing) {
				Relax(u, out);
			}
		}
	}

	void Relax(Job u, Job v) {
		if ((u.startTime + u.timeToFinish) > (v.startTime)) {
			if (v.finished) {
				v.startTime = Integer.MIN_VALUE;
				u.startTime = Integer.MIN_VALUE;
				return;
			}
			v.startTime = u.startTime + u.timeToFinish;
		}
	}

	// make a list of topological order
	// return a topological order
	private ArrayList<Job> TopOrder(ArrayList<Job> unsorted) {
		ArrayList<Job> topOrdering = new ArrayList<Job>(unsorted);
		int index = 0;
		for (Job j : unsorted) {
			j.dummy = j.inDegree;
			j.startTime = Integer.MIN_VALUE;
			j.finished = false;
			if (j.inDegree == 0) {
				topOrdering.set(index++, j);
				j.startTime = 0;
			}
		}
		if (index == 0) {
			return topOrdering;
		}
		
		Iterator<Job> iter = topOrdering.iterator();
		while (iter.hasNext()) {
			Job u = iter.next();
			for (Job j : u.outGoing) {
				j.dummy--;
				if (j.dummy == 0) {
					topOrdering.set(index++, j);
				}
			}
		}
		return topOrdering;
	}

}
