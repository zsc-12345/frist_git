FROM adoptopenjdk/openjdk11

VOLUME /home/apps

ADD ./target/lk-exam-service-1.0.0.jar lk-exam-service-1.0.0.jar

ENTRYPOINT ["java","-jar","/lk-exam-service-1.0.0.jar"]
