package Pruebas;
import xdevs.core.modeling.*;

public class MyGenerator extends Atomic {
	protected Port<MyJob> iStart = new Port<>("iStart");
	protected Port<MyJob> iStop = new Port<>("iStop");
	protected Port<MyJob> oOut = new Port<>("oOut");
	protected int jobCounter;
	protected double period;

	public MyGenerator(String name, double period) {
		super(name);
		super.addInPort(iStop);
		super.addInPort(iStart);
		super.addOutPort(oOut);
		this.period = period;
	}

	@Override
	public void initialize() {
		jobCounter = 1;
		this.holdIn("active", period);
	}

	@Override
	public void exit() {
	}

	@Override
	public void deltint() {
		jobCounter++;
		this.holdIn("active", period);
	}

	@Override
	public void deltext(double e) {
		super.passivate();
	}

	@Override
	public void lambda() {
		MyJob job = new MyJob("" + jobCounter + "");
		oOut.addValue(job);
	}
}
