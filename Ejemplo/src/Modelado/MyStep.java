package Modelado;
import xdevs.core.modeling.*;

public class MyStep extends Atomic {

	public Port<Double> oOut = new Port<>("out");
	protected double initialValue;
	protected double stepTime;
	protected double finalValue;

	public MyStep(String name, double initialValue, double stepTime, double finalValue) {
		super(name);
		super.addOutPort(oOut);
		this.initialValue = initialValue;
		this.stepTime = stepTime;
		this.finalValue = finalValue;
	}

	@Override
	public void initialize() {
		super.holdIn("initialValue", 0.0);
	}

	@Override
	public void exit() {
	}

	@Override
	public void deltint() {
		if (super.phaseIs("initialValue")) {
			super.holdIn("finalValue", stepTime);
		} else if (super.phaseIs("finalValue")) {
			super.passivate();
		}
	}

	@Override
	public void deltext(double e) {
	}

	@Override
	public void lambda() {
		if (super.phaseIs("initialValue")) {
			oOut.addValue(initialValue);
		} else if (super.phaseIs("finalValue")) {
			oOut.addValue(finalValue);
		}
	}
}
