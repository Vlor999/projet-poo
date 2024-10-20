# Makefile modifié après extraction de gui.jar

all:  testLecture

testLecture:
	javac -d bin -sourcepath src src/TestLecteurDonnees.java

# Execution:
exeLecture: 
	java -classpath bin TestLecteurDonnees cartes/carteSujet.map

exeLectureAll:
	clear && java -classpath bin TestLecteurDonnees cartes/*.map

clean:
	rm -rf bin/*

cleanDocs:
	rm -rf docs/* docs

docs:
	javadoc -d docs src/*/*.java
