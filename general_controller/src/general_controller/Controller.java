package general_controller;

import org.apache.log4j.BasicConfigurator;

public class Controller {

	public String getFlow(String switchID, String portID) {
		return SDNControllerAdapter.getFlowInfo(switchID, portID);
	}
	
	
	
	
	public static void main(String[] args) {
		/*SDNControllerAdapter.enableStats();
		FlowGestion flowG = new FlowGestion();
		flowG.start();*/
		BasicConfigurator.configure();
		SDNControllerAdapter.reRoute("10.0.0.2", "10.0.0.6", "00:00:00:00:00:01", "00:00:00:00:00:04");
		
	}
}
