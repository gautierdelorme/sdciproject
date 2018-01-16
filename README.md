# sdciproject

## Developer Setup

### Requirements

- Download and install `docker-compose`:

```bash
sudo curl -L https://github.com/docker/compose/releases/download/1.18.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

### Project Setup

1. `sudo mn -c` to clean previous topology.
1. Go to your `SDNController` directory and run `java -jar myTarget/sdncontroller.jar` to start the controller.
1. `./build_topo` to create the topology.
1. Docker images will be downloaded automatically during the topology creation (and  are available on Docker Hub: https://hub.docker.com/u/sdciproject)
1. `sudo docker-compose up -d` to launch the datacenter
