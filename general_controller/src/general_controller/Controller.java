package general_controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;

import io.javalin.Javalin;

public class Controller {

	public String getFlow(String switchID, String portID) {
		return SDNControllerAdapter.getFlowInfo(switchID, portID);
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();
		FlowGestion flowG = new FlowGestion();
		
		Javalin serv = Javalin.start(9500);

		/*
		 * Create it and then reroute a finale gateway to this new container instead of the initial gateway
		 */
		serv.get("/trigger", ctx -> {
			String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			int portCont = VNFManager.launchGW(name);
			SDNControllerAdapter.reRoute("10.0.0.4", "00:00:00:00:00:02", portCont, name);
			ctx.result("RULE # \n : " + name + "\n");
		});

		/*
		 * Delete rule by name
		 */
		serv.delete("rules/:ruleName", ctx -> {
			SDNControllerAdapter.deleteRoute(ctx.param("ruleName"));
			VNFManager.stopGW(ctx.param("ruleName"));
			VNFManager.removeGW(ctx.param("ruleName"));
			ctx.result("You have deleted the rule : " + ctx.param("ruleName") + "\n");
		});

		/*
		 * Start the thread and make automatic flow management
		 */
		serv.get("/mode-auto-on", ctx -> {
			if(!flowG.isAlive()){
				flowG.start();
				ctx.result("You set autonomous flow management on ! \n");
			}else {
				ctx.result("Autonomous flow management already enabled \n");
			}
			
		});

		/*
		 * Stop the thread
		 */
		serv.get("/mode-auto-off", ctx -> {
			if(flowG.isAlive()){
				flowG.interrupt();
				ctx.result("You set autonomous flow management off ! \n");
			}else {
				ctx.result("Autonomous flow management already disabled \n");
			}
			
		});

	}
}
