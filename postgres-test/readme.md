## connecting postgress from java
https://www.tutorialspoint.com/postgresql/postgresql_java.htm

## Creating a fat Assembly jar file with all the dependencies
1. In root of the project /project directory create an assembly.sbt with content
```scala
resolvers += Resolver.url("bintray-sbt-plugins", url("https://dl.bintray.com/eed3si9n/sbt-plugins/"))(Resolver.ivyStylePatterns)
resolvers += Resolver.sonatypeRepo("public")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.7")
```
2. In build.sbt in root of the project 
```scala
lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.example",
  scalaVersion := "2.11.6",
  test in assembly := {}
)

lazy val app = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("com.example.PostgresTest"),
    assemblyJarName in assembly := "cds-postgress-test.jar"

    // more settings here ...
  )
 ```
 
 4. run sbt assembly
 
 5. java -jar executable.jar
 
 6. more details 
 https://alvinalexander.com/scala/sbt-how-build-single-executable-jar-file-assembly
 https://github.com/sbt/sbt-assembly

## run jar from docker
```
FROM java:8
WORKDIR /
ADD HelloWorld.jar HelloWorld.jar
EXPOSE 8080
CMD java - jar HelloWorld.jar
```
https://dzone.com/articles/run-simple-jar-application-in-docker-container-1
 
## getting sbt version and not of the project
sbt sbtversion
https://www.scala-sbt.org/1.0/docs/Basic-Def.html

## running a load test against a web wpp
it provides a config and weight to each test to run
https://artillery.io/docs/basic-concepts/#the-example-script
 
## Postgres json querying ans persisting 
1. jsonb datatype
2. http://www.postgresqltutorial.com/postgresql-json/ 


## Some common postgres commands
select * from information_schema.tables where table_name = 'emp'
creating indexes
CREATE INDEX index_name
ON table_name (column1_name, column2_name);

## Note when creating database using sql in code 
autocommit must be set to true for the connection.

## todos
 1. create user in init part of project
 2. Create docker images using sbt 
 https://github.com/marcuslonnberg/sbt-docker
 
## misc


