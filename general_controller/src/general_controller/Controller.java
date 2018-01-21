package general_controller;

import org.apache.log4j.BasicConfigurator;
import org.json.simple.JSONObject;

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
		
		
		/*
		 * This line is call with a get /trigger/"name of the new container" it will create it and then reroute GF1 to this new container instead of GI
		 * */
		serv.get("/trigger/:name", ctx -> {
			int portCont = VNFManager.launchGW(ctx.param("name"));
			SDNControllerAdapter.reRoute("10.0.0.4", "00:00:00:00:00:02", portCont,ctx.param("name"));
			ctx.result("Well done you've redirected GF1 to your new gateway, Nane of the gateway : "+ ctx.param("name")+ "\n");
		});
		
		serv.delete("rules/delete", ctx -> {
			JSONObject jsonBody = ctx.bodyAsClass(JSONObject.class);
			SDNControllerAdapter.deleteRoute((String)jsonBody.get("name"));
			ctx.result("You have deleted the rule : "+ jsonBody.get("name") + "\n");
		});
		
		serv.post("rules/delete/:ruleName", ctx -> {
			ctx.result("You have deleted the rule : "+ ctx.param("ruleName") + "\n");
		});
		

	}
}
