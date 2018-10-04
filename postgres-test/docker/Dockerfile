FROM  hseeberger/scala-sbt

RUN cd /
RUN mkdir java-app
RUN cd java-app
WORKDIR /java-app

COPY ./cds-postgress-test.jar /java-app

RUN apt-get install netcat -y \
    iputils-ping

CMD java -jar cds-postgress-test.jar



