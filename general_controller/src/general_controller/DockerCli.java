package general_controller;

import java.io.IOException;
//import java.nio.file.Paths;
import java.util.List;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.api.DockerClient;

public class DockerCli {
	String ctrldockerurl;
	DockerClient dockerClient;

	DockerCli(String ctrldockerurl) {
		this.ctrldockerurl = ctrldockerurl;

		DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
				.withDockerHost("tcp://" + ctrldockerurl).withDockerTlsVerify(false).build();
		dockerClient = DockerClientBuilder.getInstance(config).build();
	}

//	public String createContainer(String img, String name, int port, int bindport) throws IOException {
//
//		ExposedPort http = ExposedPort.tcp(port);
//		Ports portBinding = new Ports();
//		portBinding.bind(http, Binding.bindPort(bindport));
//
//		CreateContainerResponse container = dockerClient.createContainerCmd(img).withExposedPorts(http)
//				.withPortBindings(portBinding).withName(name).withStdinOpen(true).withTty(true).exec();
//		System.out.println("ct id : " + container.getId());
//		return container.getId();
//	}

	public void startContainer(String containerId) throws IOException {
		dockerClient.startContainerCmd(containerId).exec();
	}

	public void stopContainer(String containerId) throws IOException {
		dockerClient.stopContainerCmd(containerId).exec();
	}

	public void removeContainer(String containerId) throws IOException {
		dockerClient.removeContainerCmd(containerId).exec();
	}

	public InspectContainerResponse getInfoContainer(String containerId) throws IOException {
		InspectContainerResponse info = dockerClient.inspectContainerCmd(containerId).exec();
		System.out.println("info : " + info.toString());
		return info;
	}

	public List<Container> listContainers(boolean showall) throws IOException {
		List<Container> containers = dockerClient.listContainersCmd().withShowAll(showall).exec();
		System.out.println("Cts Numb : " + containers.size());
		for (Container ct : containers) {
			System.out.println("ct id : " + ct.getId());
		}
		return containers;
	}

//	public String createAndRunContainer(String img, String name, int port, int bindport, String scriptPath)
//			throws IOException {
//
//		ExposedPort http = ExposedPort.tcp(port);
//		Ports portBinding = new Ports();
//		portBinding.bind(http, Binding.bindPort(bindport));
//
//		CreateContainerResponse container = dockerClient.createContainerCmd(img).withExposedPorts(http)
//				.withPortBindings(portBinding).withCmd("sh", "/home/" + Paths.get(scriptPath).getFileName().toString())
//				.withName(name).exec();
//		dockerClient.copyArchiveToContainerCmd(container.getId()).withHostResource(scriptPath).withRemotePath("/home")
//				.exec();
//
//		dockerClient.startContainerCmd(container.getId()).exec();
//		return container.getId();
//	}
	
	public String launchContainer(String img, String name, int port, int bindport) throws IOException {

		ExposedPort http = ExposedPort.tcp(port);
		Ports portBinding = new Ports();
		portBinding.bind(http, Binding.bindPort(bindport));
		
		CreateContainerResponse container = dockerClient.createContainerCmd(img)
				.withExposedPorts(http)
				.withPortBindings(portBinding)
				.withCmd("sh", "/workdir/launcher.sh", name)
				.withName(name)
				.withStdinOpen(true)
				.withTty(true)
				.exec();

		dockerClient.startContainerCmd(container.getId()).exec();
		return container.getId();
	}

}
