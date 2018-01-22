package general_controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

public class FlowGestion extends Thread {
	
	public void run() {
		while (!isInterrupted()) {
			JSONObject flow, flow2;
			String name;
			flow = SDNControllerAdapter.getFlowInfo("00:00:00:00:00:00:00:02", "2");
			int recep1 = Integer.parseInt(flow.get("bits-per-second-rx").toString());
			int trans1 = Integer.parseInt(flow.get("bits-per-second-tx").toString());	
			int seuil = trans1 + recep1;
			
			if(seuil >= 120) {
				System.out.println("Need redirection !");
				name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				int portCont = VNFManager.launchGW(name);
				SDNControllerAdapter.reRoute("10.0.0.4", "00:00:00:00:00:02", portCont, name);
				System.out.println("Redirection  done!");
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				System.out.println("Flow gestion interrupted mode auto : OFF");
				Thread.currentThread().interrupt();
			}
		}
	}
	
	
}
