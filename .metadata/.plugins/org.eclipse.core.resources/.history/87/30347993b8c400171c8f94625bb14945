import static org.junit.Assert.*;

import org.junit.Test;

public class Tests {

	@Test
	public void basicTest() {

		JobSchedule schedule = new JobSchedule();
		schedule.addJob(5);
		schedule.addJob(2);
		schedule.addJob(15);
		schedule.addJob(6);
		
		
		schedule.jobs.get(1).requires(schedule.jobs.get(0));
		schedule.jobs.get(2).requires(schedule.jobs.get(0));
		schedule.jobs.get(3).requires(schedule.jobs.get(0));
		schedule.jobs.get(3).requires(schedule.jobs.get(1));
		

		
		assertEquals(0, schedule.getJob(0).getStartTime());
		assertEquals(5, schedule.getJob(1).getStartTime());
		assertEquals(5, schedule.getJob(2).getStartTime());
		assertEquals(7, schedule.getJob(3).getStartTime());
		
		assertEquals(20,schedule.minCompletionTime());

	}
	@Test
	public void BigTest() 
	{
		JobSchedule schedule = new JobSchedule();
		schedule.addJob(3); //0
		schedule.addJob(6);//1
		schedule.addJob(7);//2
		schedule.addJob(9);//3
		schedule.addJob(2);//4
		schedule.addJob(12);//5
		schedule.addJob(0); //6
		schedule.addJob(1);//7
		schedule.addJob(2);//8
		schedule.addJob(0);//9
		
		
		schedule.jobs.get(1).requires(schedule.jobs.get(0));
		schedule.jobs.get(2).requires(schedule.jobs.get(1));
		schedule.jobs.get(3).requires(schedule.jobs.get(1));
		schedule.jobs.get(3).requires(schedule.jobs.get(2));
		schedule.jobs.get(3).requires(schedule.jobs.get(5));
		schedule.jobs.get(4).requires(schedule.jobs.get(3));
		schedule.jobs.get(5).requires(schedule.jobs.get(0));
		
		schedule.jobs.get(6).requires(schedule.jobs.get(5));
		schedule.jobs.get(7).requires(schedule.jobs.get(5));
		schedule.jobs.get(8).requires(schedule.jobs.get(5));
		schedule.jobs.get(8).requires(schedule.jobs.get(7));
		
		schedule.jobs.get(9).requires(schedule.jobs.get(8));
		
		
		
		
		assertEquals(3, schedule.getJob(5).getStartTime());
		assertEquals(16,schedule.getJob(3).getStartTime());
		assertEquals(27,schedule.minCompletionTime());
		
	}
	
	@Test
	public void loopTest() 
	{
		JobSchedule schedule = new JobSchedule();
		schedule.addJob(5);
		schedule.addJob(2);
		schedule.addJob(15);
		
		schedule.jobs.get(1).requires(schedule.jobs.get(0));
		schedule.jobs.get(2).requires(schedule.jobs.get(1));
		schedule.jobs.get(0).requires(schedule.jobs.get(2));
		
		assertEquals(-1, schedule.getJob(0).getStartTime());
		assertEquals(-1,schedule.minCompletionTime());
		
	}
	
	@Test
	public void SingleTest() 
	{
		JobSchedule schedule = new JobSchedule();
		schedule.addJob(5);
		
		assertEquals(0, schedule.getJob(0).getStartTime());
		assertEquals(5,schedule.minCompletionTime());
		
	}
	
	@Test
	public void notConnectedTest() 
	{
		JobSchedule schedule = new JobSchedule();
		schedule.addJob(5);
		schedule.addJob(10);
		schedule.addJob(16);
		
		assertEquals(0, schedule.getJob(2).getStartTime());
		assertEquals(16,schedule.minCompletionTime());
		
	}
	
	@Test
	public void notConnectedLargerTest() 
	{
		JobSchedule schedule = new JobSchedule();
		schedule.addJob(5);
		schedule.addJob(10);
		schedule.addJob(16);
		schedule.addJob(2);
		schedule.addJob(20);
		
		schedule.jobs.get(1).requires(schedule.jobs.get(0));
		schedule.jobs.get(2).requires(schedule.jobs.get(0));
		schedule.jobs.get(1).requires(schedule.jobs.get(2));
		schedule.jobs.get(3).requires(schedule.jobs.get(4));
		assertEquals(21, schedule.getJob(1).getStartTime());
		
		
		assertEquals(31,schedule.minCompletionTime());
		
	}
	@Test
	public void loopsTest() 
	{
		JobSchedule schedule = new JobSchedule();
		schedule.addJob(8); //adds job 0 with time 8
		JobSchedule.Job j1 = schedule.addJob(3); //adds job 1 with time 3
		schedule.addJob(5); //adds job 2 with time 5
		assertEquals(8,schedule.minCompletionTime()); //should return 8, since job 0 takes time 8 to complete.
		/* Note it is not the min completion time of any job, but the earliest the entire set can complete. */
		schedule.getJob(0).requires(schedule.getJob(2)); //job 2 must precede job 0
		assertEquals(13,schedule.minCompletionTime()); //should return 13 (job 0 cannot start until time 5)
		schedule.getJob(0).requires(j1); //job 1 must precede job 0
		assertEquals(13,schedule.minCompletionTime()); //should return 13
		assertEquals(5, schedule.getJob(0).getStartTime()); //should return 5
		assertEquals(0, j1.getStartTime());  //should return 0
		assertEquals(0, schedule.getJob(2).getStartTime()); //should return 0
		j1.requires(schedule.getJob(2)); //job 2 must precede job 1
		assertEquals(16,schedule.minCompletionTime()); //should return 16
		assertEquals(8, schedule.getJob(0).getStartTime()); //should return 8
		assertEquals(5, schedule.getJob(1).getStartTime()); //should return 5
		assertEquals(0, schedule.getJob(2).getStartTime()); //should return 0
		schedule.getJob(1).requires(schedule.getJob(0)); //job 0 must precede job 1 (creates loop)
		assertEquals(-1,schedule.minCompletionTime()); //should return -1
		assertEquals(-1, schedule.getJob(0).getStartTime()); //should return -1
		assertEquals(-1, schedule.getJob(1).getStartTime()); //should return -1
		assertEquals(0, schedule.getJob(2).getStartTime()); //should return 0 (no loops in prerequisites)
		
		
		
		schedule.addJob(2);
		schedule.addJob(1);
		schedule.addJob(6);
		schedule.getJob(3).requires(schedule.getJob(2));
		schedule.getJob(4).requires(schedule.getJob(3));
		schedule.getJob(5).requires(schedule.getJob(3));
		schedule.getJob(4).requires(schedule.getJob(5));
		assertEquals(5, schedule.getJob(3).getStartTime()); //should return -1
		assertEquals(13, schedule.getJob(4).getStartTime()); //should return -1
		assertEquals(7, schedule.getJob(5).getStartTime()); //should return 0 (no loops in prerequisites)
		schedule.addJob(2);
		schedule.getJob(6).requires(schedule.getJob(1));
		assertEquals(-1, schedule.getJob(6).getStartTime());
		schedule.addJob(3);
		schedule.getJob(7).requires(schedule.getJob(6));
		assertEquals(-1, schedule.getJob(7).getStartTime());
		schedule.addJob(2);//8
		schedule.addJob(3);//9
		schedule.addJob(4);//10
		schedule.getJob(8).requires(schedule.getJob(4));
		schedule.getJob(9).requires(schedule.getJob(8));
		schedule.getJob(10).requires(schedule.getJob(9));
		schedule.getJob(8).requires(schedule.getJob(10));
		assertEquals(-1, schedule.getJob(8).getStartTime());
		assertEquals(-1, schedule.getJob(9).getStartTime());
		assertEquals(-1, schedule.getJob(10).getStartTime());
		assertEquals(-1,schedule.minCompletionTime());
		
	}
	
	@Test
	public void Stuckon85Test() 
	{
		JobSchedule schedule = new JobSchedule();
		schedule.addJob(3);
		schedule.addJob(5);
		schedule.addJob(8);
		schedule.addJob(3);
		schedule.addJob(5);
		
		schedule.jobs.get(1).requires(schedule.jobs.get(0));
		schedule.jobs.get(2).requires(schedule.jobs.get(1));
		schedule.jobs.get(0).requires(schedule.jobs.get(2));
		schedule.jobs.get(4).requires(schedule.jobs.get(3));
		
		assertEquals(-1, schedule.getJob(0).getStartTime());
		assertEquals(-1, schedule.getJob(1).getStartTime());

		
		assertEquals(0, schedule.getJob(3).getStartTime());
		assertEquals(3, schedule.getJob(4).getStartTime());
		assertEquals(-1,schedule.minCompletionTime());
		
	}
	
	

}