name := """postgres-test"""

version := "1.0"

scalaVersion := "2.11.6"

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

libraryDependencies ++= Seq(
  // Uncomment to use Akka
  //"com.typesafe.akka" % "akka-actor_2.11" % "2.3.9",
  "junit"             % "junit"           % "4.12"  % "test",
  "com.novocode"      % "junit-interface" % "0.11"  % "test"
  //"org.postgresql" % "postgresql" % "42.2.5"
  // https://mvnrepository.com/artifact/postgresql/postgresql
)

libraryDependencies += "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
