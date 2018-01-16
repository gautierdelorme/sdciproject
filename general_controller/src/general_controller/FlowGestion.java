package general_controller;

public class FlowGestion  extends Thread{
	String flow;
	
	public FlowGestion() {
		this.flow = null;
	}
	
	public void run() {
		this.flow = SDNControllerAdapter.getFlowInfo("00:00:00:00:00:00:00:02", "2");
		System.out.println(flow);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
