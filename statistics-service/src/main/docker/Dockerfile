FROM java:8
VOLUME /application.yml
ADD statistics-service-0.1.3.jar app.jar
ADD run.sh run.sh
ENV API_KEY api_key
ENV API_AUTH api_auth
ENTRYPOINT ["bash", "/run.sh"]