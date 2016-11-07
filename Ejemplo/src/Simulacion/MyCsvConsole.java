package Simulacion;

import xdevs.core.modeling.*;

public class MyCsvConsole extends Atomic {
	public Port<Number> iIn = new Port<>("in");
	protected double time;

	public MyCsvConsole(String csvPath) {
		super("CsvConsole");
		super.addInPort(iIn);
	}

	@Override
	public void initialize() {
		this.time = 0.0;
		super.passivate();
	}

	@Override
	public void exit() {
	}

	@Override
	public void deltint() {
		time += super.getSigma();
		super.passivate();
	}

	@Override
	public void deltext(double e) {
		time += e;
		if (!iIn.isEmpty()) {
			System.out.println(time + ";" + iIn.getSingleValue().doubleValue());
		}
	}

	@Override
	public void lambda() {
	}
}
