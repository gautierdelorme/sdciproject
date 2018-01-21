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
			flow2 = SDNControllerAdapter.getFlowInfo("00:00:00:00:00:00:00:04", "1");
			float recep1 = Integer.parseInt(flow.get("bits-per-second-rx").toString());
			float recep2 = Integer.parseInt(flow.get("bits-per-second-rx").toString());

			float trans1 = Integer.parseInt(flow.get("bits-per-second-tx").toString());
			float trans2 = Integer.parseInt(flow.get("bits-per-second-tx").toString());
			
			float total1 = trans1 + recep1;
			float total2 = trans2 + recep2;
			
			float seuil = total1/total2;
			if(seuil >= 0.70) {
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
