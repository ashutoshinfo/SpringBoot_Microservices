package info.ashutosh.listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobMonitoringListener implements JobExecutionListener {

	private long start, end;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("JobMonitoringListener.beforeJob() : Start" + new Date());
		System.out.println("Status " + jobExecution.getStatus());
		start = System.currentTimeMillis();

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("JobMonitoringListener.afterJob() : End" + new Date());
		System.out.println("Status " + jobExecution.getStatus());
		end = System.currentTimeMillis();
		System.out.println("Time Consumprion : " + (end - start) + " ms");
	}

}
