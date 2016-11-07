package Simulacion;
import Modelado.MyPulseGenerator;
import xdevs.core.modeling.*;
import xdevs.core.simulation.*;

public class MyPulseGeneratorExample extends Coupled {
	@SuppressWarnings("deprecation")
	public MyPulseGeneratorExample() {
		super("PulseGeneratorExample");
		MyPulseGenerator pulse = new MyPulseGenerator("Pulse", 10, 3, 5, 5);
		super.addComponent(pulse);
		MyCsvConsole scope = new MyCsvConsole("CSV");
		super.addComponent(scope);
		super.addCoupling(pulse, pulse.oOut, scope, scope.iIn);
	}

	public static void main(String[] args) {
		MyPulseGeneratorExample pulseExample = new MyPulseGeneratorExample();
		Coordinator coordinator = new Coordinator(pulseExample);
		coordinator.initialize();
		coordinator.simulate(30.0);
		coordinator.exit();
	}
}
