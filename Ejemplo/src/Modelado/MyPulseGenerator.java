package Modelado;
import xdevs.core.modeling.*;

public class MyPulseGenerator extends Atomic {

	public Port<Double> oOut = new Port<>("out");
	protected double amplitude;
	protected double pulseWidth;
	protected double period;
	protected double phaseDelay;

	public MyPulseGenerator(String name, double amplitude, double pulseWidth, double period, double phaseDelay) {
		super(name);
		super.addOutPort(oOut);
		this.amplitude = amplitude;
		this.pulseWidth = pulseWidth;
		this.period = period;
		this.phaseDelay = phaseDelay;
	}

	@Override
	public void initialize() {
		super.holdIn("delay", 0);
	}

	@Override
	public void exit() {
	}

	@Override
	public void deltint() {
		if (super.phaseIs("delay")) {
			super.holdIn("high", phaseDelay);
		} else if (super.phaseIs("high")) {
			super.holdIn("low", pulseWidth);
		} else if (super.phaseIs("low")) {
			super.holdIn("high", period - pulseWidth);
		}
	}

	@Override
	public void deltext(double e) {
	}

	@Override
	public void lambda() {
		if (super.phaseIs("delay")) {
			oOut.addValue(0.0);
		} else if (super.phaseIs("high")) {
			oOut.addValue(amplitude);
		} else if (super.phaseIs("low")) {
			oOut.addValue(0.0);
		}
	}
}
