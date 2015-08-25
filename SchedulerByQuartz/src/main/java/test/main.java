package test;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class main {

  /**
   * @param args
   */
  private static final String GROUP_ID = "upload_group";
  private static final String TRIGGER_ID = "upload_trigger";
  private static final String JOB_ID = "upload_job";
  public static void main(String[] args) {
  
    try {
      Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
      scheduler.start();

      JobDetail job = newJob(DataGenerator.class)
                          .withIdentity(JOB_ID, GROUP_ID)
                          .build();
      
      job.getJobDataMap().put("test", "123");

      Trigger trigger = newTrigger()
                          .withIdentity(TRIGGER_ID, GROUP_ID)
                          .startNow()
                          .withSchedule(cronSchedule("0/6 * * * * ?"))
                          .build();

      scheduler.scheduleJob(job, trigger);
      



  } catch (SchedulerException e) {

      System.out.println("GenerateSchedule start fail. SchedulerException => "+e.toString());
  }
}
}
