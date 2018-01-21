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
		 * This line is call with a get /trigger/"name of the new container" it will
		 * create it and then reroute GF1 to this new container instead of GI
		 */
		serv.get("/trigger", ctx -> {
			String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			int portCont = VNFManager.launchGW(name);
			SDNControllerAdapter.reRoute("10.0.0.4", "00:00:00:00:00:02", portCont, name);
			ctx.result("Well done you've redirected GF1 to your new gateway, Name of the gateway : " + name + "\n");
		});

		/*
		 * Dans notre cas la regle sera renomme du nom de la nouvelle GW pour des
		 * questions de praticites
		 */
		serv.delete("rules/delete/:ruleName", ctx -> {
			SDNControllerAdapter.deleteRoute(ctx.param("ruleName"));
			ctx.result("You have deleted the rule : " + ctx.param("ruleName") + "\n");
		});

		/*
		 * Start the thread and make automatic flow gestion
		 */
		serv.get("/mode-auto-on", ctx -> {
			flowG.start();
			ctx.result("You set auto-mode on ! \n");
		});

		/*
		 * Stop the thread
		 */
		serv.get("/mode-auto-off", ctx -> {
			flowG.interrupt();
			ctx.result("You set auto-mode off ! \n");
		});

	}
}
