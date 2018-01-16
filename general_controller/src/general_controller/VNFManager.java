package general_controller;

import java.io.IOException;

public class VNFManager {
	private static String DOCKER_URL = "172.17.0.7";
	private static String DOCKER_GI_IMAGE = "sdciproject/gi-template";
	private static DockerCli dockercli = null;
	
	private static DockerCli dockerCli() {
		if (dockercli == null) {
			dockercli = new DockerCli(DOCKER_URL);
		}
		return dockercli;
	}
	
	public static void launchGW(String name) {
		try {
			dockerCli().launchContainer(DOCKER_GI_IMAGE, name, 8080, 8080+getBindPort());
		} catch (IOException e) {
			System.err.println("Launch GW");
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public static void stopGW(String name) {
		try {
			dockerCli().stopContainer(name);
		} catch (IOException e) {
			System.err.println("Stop GW");
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public static void removeGW(String name) {
		try {
			dockerCli().removeContainer(name);
		} catch (IOException e) {
			System.err.println("Remove GW");
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public static String getInfoContainer(String name) {
		try {
			return dockerCli().getInfoContainer(name).toString();
		} catch (IOException e) {
			System.err.println("GET info GW");
			System.err.println(e);
			e.printStackTrace();
			return "";
		}
	}
	
	private static int getBindPort() {
		try {
			return dockerCli().listContainers(true).size();
		} catch (IOException e) {
			System.err.println("GET bin port");
			System.err.println(e);
			e.printStackTrace();
			return 0;
		}
	}
}
