package Simulacion;
import Modelado.MyRamp;
import xdevs.core.modeling.*;
import xdevs.core.simulation.*;

public class MyRampExample extends Coupled {
	@SuppressWarnings("deprecation")
	public MyRampExample() {
		super("MyRampExample");
		MyRamp ramp = new MyRamp("MyRamp", 2, 10, 2, 0.1);
		super.addComponent(ramp);
		MyCsvConsole scope = new MyCsvConsole("CSV");
		super.addComponent(scope);
		super.addCoupling(ramp, ramp.oOut, scope, scope.iIn);
	}

	public static void main(String[] args) {
		MyRampExample example = new MyRampExample();
		Coordinator coordinator = new Coordinator(example);
		coordinator.initialize();
		coordinator.simulate(30.0);
		coordinator.exit();
	}
}
