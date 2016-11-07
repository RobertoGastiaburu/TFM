package Pruebas;
import java.util.*;
import java.util.logging.Logger;
import xdevs.core.modeling.*;

public class MyTransducer extends Atomic {
	private static final Logger logger = Logger.getLogger(MyTransducer.class.getName());
	protected Port<MyJob> iArrived = new Port<>("iArrived");
	protected Port<MyJob> iSolved = new Port<>("iSolved");
	protected Port<MyJob> oOut = new Port<>("oOut");
	protected LinkedList<MyJob> jobsArrived = new LinkedList<>();
	protected LinkedList<MyJob> jobsSolved = new LinkedList<>();
	protected double observationTime;
	protected double totalTa;
	protected double clock;

	public MyTransducer(String name, double observationTime) {
		super(name);
		super.addInPort(iArrived);
		super.addInPort(iSolved);
		super.addOutPort(oOut);
		totalTa = 0;
		clock = 0;
		this.observationTime = observationTime;
	}

	@Override
	public void initialize() {
		super.holdIn("active", observationTime);
	}

	@Override
	public void exit() {
	}

	@Override
	public void deltint() {
		clock = clock + getSigma();
		double throughput;
		double avgTaTime;
		if (phaseIs("active")) {
			if (!jobsSolved.isEmpty()) {
				avgTaTime = totalTa / jobsSolved.size();
				if (clock > 0.0) {
					throughput = jobsSolved.size() / clock;
				} else {
					throughput = 0.0;
				}
			} else {
				avgTaTime = 0.0;
				throughput = 0.0;
			}
			logger.info("End time: " + clock);
			logger.info("Jobs arrived : " + jobsArrived.size());
			logger.info("Jobs solved : " + jobsSolved.size());
			logger.info("Average TA = " + avgTaTime);
			logger.info("Throughput = " + throughput);
			holdIn("done", 0);
		} else {
			passivate();
		}
		// logger.info("####deltint: "+showState());
	}

	@Override
	public void deltext(double e) {
		clock = clock + e;
		if (phaseIs("active")) {
			MyJob job = null;
			if (!iArrived.isEmpty()) {
				job = iArrived.getSingleValue();
				logger.fine("Start job " + job.id + " @ t = " + clock);
				job.time = clock;
				jobsArrived.add(job);
			}
			if (!iSolved.isEmpty()) {
				job = iSolved.getSingleValue();
				totalTa += (clock - job.time);
				logger.fine("Finish job " + job.id + " @ t = " + clock);
				job.time = clock;
				jobsSolved.add(job);
			}
		}
		// logger.info("###Deltext: "+showState());
	}

	@Override
	public void lambda() {
		if (phaseIs("done")) {
			MyJob job = new MyJob("null");
			oOut.addValue(job);
		}
	}
}
