# sdciproject

## Developer Setup


1. `sudo mn -c` to clean previous topology.
1. Go to your `SDNController` directory and run `java -jar myTarget/sdncontroller.jar` to start the controller.
1. `./build_topo` to create the topology.
1. Docker images will be downloaded automatically during the topology creation (and  are available on Docker Hub: https://hub.docker.com/u/sdciproject)
1. `sudo docker run -it --privileged --name datacenter -d docker:dind sh` to launch the datacenter
   - In a new terminal: `sudo docker exec -it datacenter sh -c "dockerd -H unix://var/run/docker.sock -H tcp://0.0.0.0:2375"`
   - Back to the initial terminal: `sudo docker exec -it datacenter sh -c "docker pull sdciproject/gi-template"`
