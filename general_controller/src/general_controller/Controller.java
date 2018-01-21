package general_controller;

import org.apache.log4j.BasicConfigurator;

import io.javalin.Javalin;

public class Controller {

	public String getFlow(String switchID, String portID) {
		return SDNControllerAdapter.getFlowInfo(switchID, portID);
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();
		/*
		 * SDNControllerAdapter.enableStats(); FlowGestion flowG = new FlowGestion();
		 * flowG.start();
		 */
		/*
		 * int portNathan = VNFManager.launchGW("nathan");
		 * VNFManager.getInfoContainer("nathan");
		 */
		Javalin serv = Javalin.start(9500);
		serv.get("/hello", ctx -> {
			ctx.result("Hello \n");
		});
		
		serv.get("/trigger/:name", ctx -> {
			int portCont = VNFManager.launchGW(ctx.param("name"));
			SDNControllerAdapter.reRoute("10.0.0.4", "00:00:00:00:00:02", portCont);
			ctx.result("Well done you've redirected GF1 to your new gateway, Nane of the gateway : "+ ctx.param("name")+ "\n");
		});
		
		// SDNControllerAdapter.reRoute("10.0.0.4", "00:00:00:00:00:02", 8080);

		// SDNControllerAdapter.reRoute("10.0.0.6", "00:00:00:00:00:04", portNathan);

	}
}
