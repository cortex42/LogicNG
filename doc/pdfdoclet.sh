#!/bin/sh

$JAVA_HOME/bin/javadoc -doclet com.tarsec.javadoc.pdfdoclet.PDFDoclet -docletpath pdfdoclet-1.0.3-all.jar -pdf ../target/pdf/logicng.pdf -config pdfdoclet_logicng.properties -sourcepath ../src/main/java -subpackages org.logicng
