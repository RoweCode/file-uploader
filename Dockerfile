FROM openjdk:20-ea-17-jdk

COPY target/file-uploader-0.0.1.jar file-uploader-0.0.1.jar
ENTRYPOINT ["java","-jar","/file-uploader-0.0.1.jar"]