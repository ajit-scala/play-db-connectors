resolvers += Resolver.url("bintray-sbt-plugins", url("https://dl.bintray.com/eed3si9n/sbt-plugins/"))(Resolver.ivyStylePatterns)
resolvers += Resolver.sonatypeRepo("public")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.7")