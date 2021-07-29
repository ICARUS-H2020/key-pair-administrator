FROM openjdk:11.0.12-jdk-slim-buster

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y wget build-essential libapr1 libapr1-dev libssl-dev

RUN wget http://apache.forthnet.gr/tomcat/tomcat-connectors/native/1.2.30/source/tomcat-native-1.2.30-src.tar.gz

RUN tar -zxvf tomcat-native-1.2.30-src.tar.gz

WORKDIR tomcat-native-1.2.30-src/native

RUN ./configure --with-apr=/usr/bin/apr-1-config --with-ssl=yes

RUN make && make install

RUN ln -s /usr/local/apr/lib/libtcnative-1.so.0.2.30 /usr/lib/libtcnative-1.so

ENV TZ=Europe/Athens

ADD target/key-pair-administrator-2.0.0.jar key-pair-administrator.jar