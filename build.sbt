

name := "pdf-search"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "net.sourceforge.tess4j" % "tess4j" % "4.0.0"

libraryDependencies += "org.apache.pdfbox" % "jbig2-imageio" % "3.0.2"
libraryDependencies += "org.apache.pdfbox" % "pdfbox" % "2.0.16"

libraryDependencies += "org.apache.opennlp" % "opennlp-tools" % "1.9.1"

showSuccess := false