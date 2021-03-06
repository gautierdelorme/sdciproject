package general_controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.json.simple.JSONObject;

import io.javalin.Javalin;

public class Controller {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		FlowGestion flowG = new FlowGestion();

		Javalin serv = Javalin.start(9500);

		/*
		 * Create it and then reroute a finale gateway to this new container instead of
		 * the initial gateway
		 */
		serv.get("/trigger", ctx -> {
			String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			int portCont = VNFManager.launchGW(name);
			String ipSrc = ctx.queryParam("ipSrc") != null ? ctx.queryParam("ipSrc") : "172.17.0.3";
			String macAddress = ctx.queryParam("macAddress") != null ? ctx.queryParam("macAddress") : "2";
			String switchID = ctx.queryParam("switchID") != null ? ctx.queryParam("switchID") : "3";
			SDNControllerAdapter.reRoute(ipSrc, "00:00:00:00:00:0" + macAddress, "00:00:00:00:00:00:00:0" + switchID,
					portCont, name);
			ctx.result("RULE # \n : " + name + "\n");
		});

		serv.post("stats/enable", ctx -> {
			SDNControllerAdapter.enableStats();
			ctx.result("Enable statistics on mininet network\n");
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
			if (!flowG.isAlive()) {
				flowG.start();
				ctx.result("You set autonomous flow management on ! \n");
			} else {
				ctx.result("Autonomous flow management already enabled \n");
			}

		});

		/*
		 * Stop the thread
		 */
		serv.get("/mode-auto-off", ctx -> {
			if (flowG.isAlive()) {
				flowG.interrupt();
				ctx.result("You set autonomous flow management off ! \n");
			} else {
				ctx.result("Autonomous flow management already disabled \n");
			}

		});

		serv.get("/get-flow", ctx -> {
			String switchID = ctx.queryParam("switchID") != null ? ctx.queryParam("switchID") : "2";
			String port = ctx.queryParam("port") != null ? ctx.queryParam("port") : "2";
			JSONObject flow = SDNControllerAdapter.getFlowInfo("00:00:00:00:00:00:00:0" + switchID, port);
			String recep1 = (flow.get("bits-per-second-rx").toString());
			String trans1 = (flow.get("bits-per-second-tx").toString());
			ctx.result("Receive flow bit per second : " + recep1 + ", transmit data bit per second : " + trans1 + "\n");

		});

	}
}
