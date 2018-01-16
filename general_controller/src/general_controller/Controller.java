package general_controller;

public class Controller {

	public String getFlow(String switchID, String portID) {
		return SDNControllerAdapter.getFlowInfo(switchID, portID);
	}
}
