package Pruebas;
import xdevs.core.modeling.*;

public class MyEf extends Coupled {
	protected Port<MyJob> iStart = new Port<>("iStart");
	protected Port<MyJob> iIn = new Port<>("iIn");
	protected Port<MyJob> oOut = new Port<>("oOut");

	public MyEf(String name, double period, double observationTime) {
		super(name);
		super.addInPort(iIn);
		super.addInPort(iStart);
		super.addOutPort(oOut);
		MyGenerator generator = new MyGenerator("generator", period);
		addComponent(generator);
		MyTransducer transducer = new MyTransducer("transducer", observationTime);
		addComponent(transducer);
		addCoupling(this.iIn, transducer.iSolved);
		addCoupling(generator.oOut, this.oOut);
		addCoupling(generator.oOut, transducer.iArrived);
		addCoupling(transducer.oOut, generator.iStop);
		addCoupling(this.iStart, generator.iStart);
	}
}
