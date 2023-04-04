# Mazes
A Maze Generator


## JDK
build docker image (30s):
```shell
docker build -f Docker/jdk.Dockerfile -t mazes:jdk .
```
start the app:
```shell
docker run -it -ePORT=8080 -p8080:8080 mazes:jdk
```
or run as cli:
```shell
docker run -it -ePORT=8080 -eMAZES_CLI=yes -p8080:8080 mazes:jdk
```


## JRE
build docker image (30s):
```shell
docker build -f Docker/jre.Dockerfile -t mazes:jre .
```
start the app:
```shell
docker run -it -ePORT=8080 -p8080:8080 mazes:jib
```


## Jib
build Docker Image (14s):
```shell
./mvnw compile jib:dockerBuild -Dimage=mazes:jib
```
start the app:
```shell
docker run -it -ePORT=8080 -p8080:8080 mazes:jib
```


## CNB
build prerequisites:
* `docker`

build container image with spring-boot CNB plugin (18s): 
```shell
./mvnw clean spring-boot:build-image \
      -Dspring-boot.build-image.imageName=mazes:cnb
```
alternatively, we can use the `pack` cli (50s):
```shell
pack build --builder=paketobuildpacks/builder:tiny -eGOOGLE_RUNTIME_VERSION=17 mazes:pack
```
start the app: 
```shell
docker run -it -ePORT=8080 -p8080:8080 mazes:cnb
```


## Native CNB
build prerequisites: 
* `docker (started with 4 CPU & 8Gb RAM)`
* `java 22.3.1.r17-grl`

build native container image with spring-boot CNB plugin (6m40s):
```shell
./mvnw clean spring-boot:build-image \
      -Pnative \
      -Dspring-boot.build-image.imageName=mazes:cnb-native
```
run the app:
```shell
docker run -it -ePORT=8080 -p8080:8080 mazes:cnb-native
```


## Native CNB from jar
build AOT processed Spring Boot executable jar:
```shell
./mvnw clean package
```
then build with pack:
```shell
pack build --builder paketobuildpacks/builder:tiny \
    --path target/mazes-1.0-exec.jar \
    --env 'BP_NATIVE_IMAGE=true' \
    mazes:native-jar-cnb
```


## Bonus: Executable CLI
set environment variable: 
```shell
export MAZES_CLI=yes
```
build exe (1m50s):
```shell
./mvnw clean package -Pnative
```
run cli:
```shell
target/mazes

target/mazes sw 6 9
```
