package Modelado;
import xdevs.core.modeling.*;

public class MyRamp extends Atomic {

	public Port<Double> oOut = new Port<>("out");
	protected double startTime;
	protected double slope;
	protected double sampleTime;
	protected double nextOutput;

	public MyRamp(String name, double initialOutput, double startTime, double slope, double sampleTime) {
		super(name);
		super.addOutPort(oOut);
		this.nextOutput = initialOutput;
		this.startTime = startTime;
		this.slope = slope;
		this.sampleTime = sampleTime;
	}

	@Override
	public void initialize() {
		super.holdIn("initialOutput", 0.0);
	}

	@Override
	public void exit() {
	}

	@Override
	public void deltint() {
		if (super.phaseIs("initialOutput")) {
			super.holdIn("startTime", startTime);
		} else {
			nextOutput += slope * sampleTime;
			super.holdIn("active", sampleTime);
		}
	}

	@Override
	public void deltext(double e) {
	}

	@Override
	public void lambda() {
		oOut.addValue(nextOutput);
	}
}
