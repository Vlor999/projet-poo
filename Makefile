# Makefile modifié après extraction de gui.jar

all:  testLecture

testLecture:
	javac -d bin -sourcepath src src/Main.java

# Execution:
exeLecture: 
	java -classpath bin Main cartes/carteSujet.map

clean:
	rm -rf bin/*

cleanDocs:
	rm -rf docs/* docs

docs:
	javadoc -d docs src/*/*.java
