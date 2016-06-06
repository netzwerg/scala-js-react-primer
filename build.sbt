import Settings.versions

lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := Settings.name,
    scalaVersion := Settings.versions.scala,
    version := Settings.version,
    scalacOptions += "-feature"
  )

libraryDependencies ++= Seq(
  "com.github.japgolly.scalajs-react" %%% "extra" % versions.scalaJsReact
)

skip in packageJSDependencies := false
jsDependencies ++= Seq(
  "org.webjars.bower" % "react" % versions.react / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
  "org.webjars.bower" % "react" % versions.react / "react-dom.js" minified "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM",
  "org.webjars" % "jquery" % versions.jQuery / "jquery.js" minified "jquery.min.js",
  "org.webjars" % "bootstrap" % versions.bootstrap / "bootstrap.js" minified "bootstrap.min.js" dependsOn "jquery.js"
)

persistLauncher in Compile := true

persistLauncher in Test := false

relativeSourceMaps := true
