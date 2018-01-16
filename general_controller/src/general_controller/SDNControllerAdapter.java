package general_controller;

import org.json.simple.JSONObject;

public class SDNControllerAdapter {
	
	/*
	 * This method permits to create new roads in json format
	 * */
	private static JSONObject createRoute(String ipSrc, String ipDst, String ethDst, String ethSrc) {
		String switchID = "00:00:00:00:00:00:00:02";
		String actionMan = "set_field=eth_dst->"+ethDst+",set_field=ipv4_dst->"+ipDst+",normal";
		JSONObject json = null;
		json.put("switch", switchID);
		json.put("name", "ZeName");
		json.put("priority","36000");
		json.put("eth_type","0x0800");
		json.put("eth_src",ethSrc);
		json.put("ipv4_src",ipSrc);
		json.put("eth_dst","00:00:00:00:00:05");
		json.put("ipv4_dst","10.0.0.3");
		json.put("actions",actionMan);
		return json;
	}

	
	/*
	 *	This method permits to updload new roads to a switch  
	 * */
	public static void reRoute(String ipSrc, String ipDst, String ethDst, String ethSrc) {	
		RestClient.post("http://10.0.2.15:8080/wm/staticflowpusher/json", createRoute(ipSrc,ipDst,ethDst,ethSrc));
	}
}
	
	
	