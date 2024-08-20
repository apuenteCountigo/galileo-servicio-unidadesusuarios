FROM openjdk:17-alpine
VOLUME /tmp
ADD ./servicio-unidades-usuarios.jar servicio-unidades-usuarios.jar
ENTRYPOINT ["java","-jar","/servicio-unidades-usuarios.jar"]