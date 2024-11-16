# Makefile modifié après extraction de gui.jar

all:  testLecture

testLecture:
	javac -d bin -sourcepath src src/Main.java

# Execution:
run: 
	java -classpath bin Main cartes/carteSujet.map

clean:
	rm -rf bin/*

cleanDocs:
	rm -rf docs/* docs

docs:
	javadoc -d docs src/*/*.java 2> /dev/null 

help:
	@echo "Les cibles disponibles sont :"
	@echo "  all: compile le projet"
	@echo "  testLecture: compile le projet"
	@echo "  run: execute le projet"
	@echo "  clean: supprime les fichiers générés"
	@echo "  cleanDocs: supprime la documentation"
	@echo "  docs: génère la documentation"
	@echo "  help: affiche ce message"
