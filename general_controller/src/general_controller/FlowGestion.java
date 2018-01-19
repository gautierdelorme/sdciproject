package general_controller;

public class FlowGestion extends Thread {
	String flow;

	public FlowGestion() {
		this.flow = null;
	}

	public void run() {
		while (true) {
			this.flow = SDNControllerAdapter.getFlowInfo("00:00:00:00:00:00:00:02", "2");
			System.out.println("Print flow on port 2 main switch");
			System.out.println(flow);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
