FROM adoptopenjdk/maven-openjdk8

WORKDIR /tonality/tonality-soap

COPY . .

CMD ["sh", "-c", "mvn clean package && java -jar ./target/tonality-soap-1.0-SNAPSHOT-shaded.jar"]
