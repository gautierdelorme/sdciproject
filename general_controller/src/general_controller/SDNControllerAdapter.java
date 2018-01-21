package general_controller;

import org.json.simple.JSONObject;

public class SDNControllerAdapter {

	/*
	 * This method permits to create new roads in json format
	 */
	private static JSONObject createRoute(String ipSrc, String ethSrc, int portCont, String ruleName) {
		String switchID = "00:00:00:00:00:00:00:02";
		String ipDst = "172.17.0.7";
		String actionMan = "set_field=eth_dst->"+VNFManager.MAC_ADDRESS_DATACENTER+",set_field=ipv4_dst->"+ipDst+",set_field=tcp_dst->"+portCont+",normal";
		JSONObject json = new JSONObject() ;
		json.put("switch", switchID);
		json.put("name", ruleName);
		json.put("priority", "36000");
		json.put("ip_proto", "0x06");
		json.put("eth_type", "0x0800");
		json.put("eth_src", ethSrc);
		json.put("ipv4_src", ipSrc);
		json.put("eth_dst", "00:00:00:00:00:05");
		json.put("ipv4_dst", "10.0.0.3");
		json.put("tcp_dst","8080");
		json.put("actions", actionMan);
		return json;
	}

	/*
	 * This method permits to updload new roads to a switch
	 */
	public static void reRoute(String ipSrc, String ethSrc,int numCont,String ruleName) {
		RestClient.post("http://10.0.2.15:8080/wm/staticentrypusher/json", createRoute(ipSrc,ethSrc,numCont,ruleName));
	}

	public static void deleteRoute(String ruleName) {
		String switchID = "00:00:00:00:00:00:00:02";
		JSONObject json = new JSONObject() ;
		json.put("switch", switchID);
		json.put("name", ruleName);
		RestClient.delete("http://10.0.2.15:8080/wm/staticflowpusher/json", json);
	}
	/*
	 * This method permits to get flow from a switch and a portID
	 * */
	public static JSONObject getFlowInfo(String switchID, String portID) {
		return RestClient.get("http://10.0.2.15:8080/wm/statistics/bandwidth/" + switchID + "/" + portID + "/json");
	}
	
	public static void enableStats() {
		JSONObject json = new JSONObject();
		json.put("","");
		RestClient.post("http://10.0.2.15:8080/wm/statistics/config/enable/json", json);
	}
}
