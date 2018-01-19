# sdciproject

## Developer Setup


1. `sudo mn -c` to clean previous topology.
1. Go to your `SDNController` directory and run `java -jar myTarget/sdncontroller.jar` to start the controller.
1. `./build_topo` to create the topology. (do not forget to run `pingallfull` in mininet shell)
1. Docker images will be downloaded automatically during the topology creation (and  are available on Docker Hub: https://hub.docker.com/u/sdciproject)
1. `sudo ./start_all` to start all gateways and servers.
1. To start the client app open new terminal and write: `sudo docker exec -it mn.appserver sh -c "cd /workdir && java -jar iotapp1.jar"`
1. To start the IoT device #1 open new terminal and write: `sudo docker exec -it mn.gf1 sh -c "cd /workdir && java -jar iotdevice.jar"`
   - to start devices 2 and 3 as well open new terminals and use the same command but replace `mn.gf1` with `mn.gf2` and `mn.gf3`
1. `sudo docker run -it --privileged --name datacenter -d docker:dind sh` to launch the datacenter
   - Open new terminal and write: `sudo docker exec -it datacenter sh -c "dockerd -H unix://var/run/docker.sock -H tcp://0.0.0.0:2375"`
   - Back to the initial terminal: `sudo docker exec -it datacenter sh -c "docker pull sdciproject/gi-template"`
