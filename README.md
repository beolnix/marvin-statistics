## Project description
It is a RESTful web-service. It is a backend for the [statistics frontend](https://github.com/beolnix/statistics-web) 
and [statistics plugin](https://github.com/beolnix/marvin-statistics-plugin) of the [marvin](https://github.com/beolnix/marvin) bot.

## Requirements
* JDK 8
* gradle 
* docker
* gradle 2.8 (or newer)

## Build from sources
To build project from the sources simply run the following command in the checked out repository directory:
```
$ gradle build
```

:collision: You will see failed tests, to pass them please run mongodb locally, it is needed for the integration smoke tests.

:collision: At the last step of the build command execution it will try to deploy the image to the private registry, and if you don't have credentials it will also fails. 


## Dependencies
* mongo database must be available in localhost on 27015 port to pass the tests
* you should set credentials required to publish image you build to the private docker registry
* configuration file `application.yml` 

## Project layout
Project consist of two modules:

* **statistics-api** - is a dependency for the clients. It encapsulates Feign client which can be used to generate REST client automatically.
* **statistics-service** - the service itself.  

If you execute `gradle build` command in the **statistics-service** directory, you will get an executable jar in `build/libs` directory.
 
This executable jar is the service, it is what we put inside the image container.
 
## Launch
I use `src/main/docker/docker-compose.yml` file to launch containers composition which includes: 

* the container of the service
* mongodb
 
There is only one dependency, into the directory where you launch `docker-compose up` you should also put `application.yml`.

Please find below example of the content for this file:
```
server.port: 8080

statistics.mongo.host: mongodb
statistics.mongo.database: statisticsDev
statistics.hostname: localhost
statistics.swagger.hostname: localhost
statistics.swagger.port: 8080

statistics.api.security:
  accessKeys:
    - key: 'ket_to_read'
      secret: 'blablabla_im_read_only_secret'
      roles:
        - 'read'
    - key: 'key_to_write'
      secret: 'blablabla_im_write_secret'
      roles:
        - 'write'
```

Keys provided in this configuration must be used in [statistics frontend](https://github.com/beolnix/statistics-web) (the one for read) and [statistics plugin](https://github.com/beolnix/marvin-statistics-plugin) (another one for write)