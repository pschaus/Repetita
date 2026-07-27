enablePlugins(PackPlugin)


packMain := Map("repetita" -> "edu.repetita.main.Main")


name := "Repetita"

version := "0.1.0"

scalaVersion := "3.3.8"


resolvers += "Mvn" at "https://cogcomp.seas.upenn.edu/m2repo/"

resolvers += "jitpack" at "https://jitpack.io"

// Adding a library dependency for ScalaTest
libraryDependencies ++= Seq(
    "com.google.ortools" % "ortools-java" % "9.8.3296",
    "org.maxicp" % "maxicp" % "0.0.3",
    "org.scalatest" %% "scalatest" % "3.2.16" % Test,
    "org.apache.commons" % "commons-lang3" % "3.13.0",
    "net.sourceforge.collections" % "collections-generic" % "4.01",
    "net.sf.jung" % "jung-algorithms" % "2.1.1",
    "net.sf.jung" % "jung-visualization" % "2.1.1",
    "net.sf.jung" % "jung-graph-impl" % "2.1.1",
    "org.scala-lang.modules" %% "scala-xml" % "2.2.0",
    "junit" % "junit" % "4.13.2" % Test,
    "com.github.sbt" % "junit-interface" % "0.13.3" % Test
)




// Some common options for the Scala compiler
scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-feature"
)

Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oF")
Test / testOptions += Tests.Argument(TestFrameworks.JUnit, "-v", "-a")
Test / fork := true
