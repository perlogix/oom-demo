# oom-demo

SpringBoot API OOM demo with Actuator Prometheus metrics enabled.

### Query API

    # Get OOM status
    curl http://localhost:8888/api
    
    # Trigger OOM
    curl http://localhost:8888/api/oom
    
    # List Actuator endpoints
    curl http://localhost:8888/actuator
    
    # Get Prometheus Exporter metrics
    curl http://localhost:8888/actuator/prometheus
    
### Build Container
    
    # Build container
    ./run.sh build
    
    # Run container
    ./run.sh run
    
    # Destroy container
    ./run.sh destroy

#### Run Public Container

    docker run -d --name oom-demo --net=host --restart always yeticloud/oom-demo:latest
