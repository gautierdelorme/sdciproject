package general_controller;

import org.json.simple.JSONObject;

public class SDNControllerAdapter {

	/*
	 * This method permits to create new roads in json format
	 */
	private static JSONObject createRoute(String ipSrc, String ethSrc, int portCont) {
		String switchID = "00:00:00:00:00:00:00:02";
		String ipDst = "172.17.0.7";
		String actionMan = "set_field=eth_dst->02:42:ac:11:00:07"  + ",set_field=ipv4_dst->" + ipDst +":"+portCont+ ",normal";
		JSONObject json = new JSONObject() ;
		json.put("switch", switchID);
		json.put("name", "ZeName");
		json.put("priority", "36000");
		json.put("eth_type", "0x0800");
		json.put("eth_src", ethSrc);
		json.put("ipv4_src", ipSrc);
		json.put("eth_dst", "00:00:00:00:00:05");
		json.put("ipv4_dst", "10.0.0.3");
		json.put("actions", actionMan);
		return json;
	}

	/*
	 * This method permits to updload new roads to a switch
	 */
	public static void reRoute(String ipSrc, String ethSrc,int numCont) {
		RestClient.post("http://10.0.2.15:8080/wm/staticflowpusher/json", createRoute(ipSrc,ethSrc,numCont));
	}

	/*
	 * This method permits to get flow from a switch and a portID
	 * */
	public static String getFlowInfo(String switchID, String portID) {
		return RestClient.get("http://10.0.2.15:8080/wm/statistics/bandwidth/" + switchID + "/" + portID + "/json");
	}
	
	public static void enableStats() {
		JSONObject json = new JSONObject();
		json.put("","");
		RestClient.post("http://10.0.2.15:8080/wm/statistics/config/enable/json", json);
	}
}
