package test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DataGenerator implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		doJob();
	}

	private void doJob() {
	  System.out.println("fire!!");

	}
	

}