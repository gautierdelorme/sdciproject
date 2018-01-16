package general_controller;

public class Controller {

	public String getFlow(String switchID, String portID) {
		return SDNControllerAdapter.getFlowInfo(switchID, portID);
	}
	
	
	
	
	public static void main(String[] args) {
		SDNControllerAdapter.enableStats();
		FlowGestion flowG = new FlowGestion();
		flowG.start();
		
	}
}
