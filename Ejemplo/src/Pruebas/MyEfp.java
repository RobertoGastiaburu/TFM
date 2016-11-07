package Pruebas;
import java.util.logging.Level;
import xdevs.core.modeling.*;
import xdevs.core.simulation.Coordinator;
import xdevs.core.util.DevsLogger;

public class MyEfp extends Coupled {
	public MyEfp(String name, double generatorPeriod, double processorPeriod, double transducerPeriod) {
		super(name);
		MyEf ef = new MyEf("ef", generatorPeriod, transducerPeriod);
		addComponent(ef);
		MyProcessor processor = new MyProcessor("processor", processorPeriod);
		addComponent(processor);
		addCoupling(ef.oOut, processor.iIn);
		addCoupling(processor.oOut, ef.iIn);
	}

	public static void main(String args[]) {
		DevsLogger.setup(Level.FINE);
		MyEfp efp = new MyEfp("efp", 1, 3, 100);
		Coordinator coordinator = new Coordinator(efp);
		coordinator.initialize();
		coordinator.simulate(Long.MAX_VALUE);
		coordinator.exit();
	}
}
