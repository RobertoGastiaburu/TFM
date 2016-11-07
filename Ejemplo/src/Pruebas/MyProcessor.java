package Pruebas;
import xdevs.core.modeling.*;

public class MyProcessor extends Atomic {
	protected Port<MyJob> iIn = new Port<>("iIn");
	protected Port<MyJob> oOut = new Port<>("oOut");
	protected MyJob currentJob = null;
	protected double processingTime;

	public MyProcessor(String name, double processingTime) {
		super(name);
		super.addInPort(iIn);
		super.addOutPort(oOut);
		this.processingTime = processingTime;
	}

	@Override
	public void initialize() {
		super.passivate();
	}

	@Override
	public void exit() {
	}

	@Override
	public void deltint() {
		super.passivate();
	}

	@Override
	public void deltext(double e) {
		if (super.phaseIs("passive")) {
			MyJob job = iIn.getSingleValue();
			currentJob = job;
			super.holdIn("active", processingTime);
		}
	}

	@Override
	public void lambda() {
		oOut.addValue(currentJob);
	}
}
