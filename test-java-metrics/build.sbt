name := """test-java-metrics"""

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  // Uncomment to use Akka
  //"com.typesafe.akka" % "akka-actor_2.11" % "2.3.9",
  "junit"             % "junit"           % "4.12"  % "test",
  "com.novocode"      % "junit-interface" % "0.11"  % "test"
)

libraryDependencies += "io.prometheus" % "simpleclient_dropwizard" % "0.4.0"
libraryDependencies += "org.eclipse.jetty" % "jetty-server" % "9.4.11.v20180605"

libraryDependencies += "org.eclipse.jetty" % "jetty-servlet" % "9.4.11.v20180605"
libraryDependencies += "io.prometheus" % "simpleclient_hotspot" % "0.4.0"
libraryDependencies += "io.prometheus" % "simpleclient_servlet" % "0.4.0"
