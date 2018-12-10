FROM java:8
COPY . /
WORKDIR /
RUN javac MyApp.java
CMD ["java","-classpath","mysql-connector-java-5.1.34.jar:.","MyApp"]
