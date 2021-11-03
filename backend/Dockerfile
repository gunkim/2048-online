FROM adoptopenjdk:11-jre-hotspot

EXPOSE 8080

# 언어 설정
RUN locale-gen ko_KR.UTF-8
ENV LANG ko_KR.UTF-8
ENV LANGUAGE ko_KR.UTF-8
ENV LC_ALL ko_KR.UTF-8

# TimeZone 설정
ENV TZ Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir /opt/app
ARG JAR_FILE_PATH=module-api/build/libs/*.jar

COPY ${JAR_FILE_PATH} module-api-1.0-plain.jar

ARG PASSWORD
ENV PASSWORD ${PASSWORD}
ARG PORT
ENV PORT ${PORT}

CMD ["java","-jar", \
"-Dspring.profiles.active=prod", \
"-Dspring.port=${PORT}", \
"-Djasypt.encryptor.password=${PASSWORD}", \
"/module-api-1.0-plain.jar"]