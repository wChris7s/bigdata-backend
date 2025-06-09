FROM amazoncorretto:21.0.7-alpine
LABEL app.author="cw.aranibar.solaligue@gmail.com" \
      app.github="https://github.com/wChris7s" \
      app.version="1.0.0"

WORKDIR .

ARG JAR_FILE=""

ENV SPRING_PROFILE=""

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=${SPRING_PROFILE}"]