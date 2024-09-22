# Firefighting Robots Simulation

## Objective
Develop a Java application to simulate autonomous firefighting robots in a natural environment.


## Phases
1. **Design**: Implement classes for maps, robots, and fires. Use provided graphical interface code.
2. **Controlled Movement**: Enable robots to move on the map.
3. **Pathfinding**: Implement shortest path algorithms for robot navigation.
4. **Optimization**: Coordinate robots to extinguish all fires efficiently.

## Goals
- Learn object-oriented programming concepts: encapsulation, inheritance, and collections.

## To Do
- ![Static Badge](https://img.shields.io/badge/Fire-SUCCES-green) Fire class: Implement the Fire class to represent fires on the map. Each fire has a position and an intensity (quantity of water needed).
- ![Static Badge](https://img.shields.io/badge/Robot-FAILED-red) Robot class: Implement the Robot class to represent robots on the map. We have to make the movement of the robot class. (Optional: Implement a subclass for flying robots and others.)
- ![Static Badge](https://img.shields.io/badge/Map-NotYet-yellow) Map class: Implement the Map class to represent the environment. The map contains a grid of cells. We also have to make a foundPath method to find the path between two points. And finaly a method to know the next case knowing the direction.
- ![Static Badge](https://img.shields.io/badge/Pathfinding-SUCCES-green) Pathfinding: Implement the A* algorithm to find the shortest path between two points on the map. This works for all robots on the map.

## Javadoc
- To generate the Javadoc, use the following command:
```bash
make docs
```
then you can open it with the following command : 
```bash
firefox docs/index.html
```

## Team
- Designed for groups of three students.
- Significant workload; start early and seek help from instructors if needed.


## Getting Started

You juste need to clone the repository with : 
```bash
git clone git@gitlab.ensimag.fr:adnetw/projet-poo.git
```

## Add your Files

```
cd projet-poo
git status 
git add file1 file2 file3 
git commit -m "Add my files with a good commit message"
git push
```
## Retrives Files
We are going to avoid merge commits issues. So we are going to use rebase.
```bash
git pull --rebase
```
if it is not possible, you can use 
```bash
git pull
```
and see the conflicts then te the files you don't want to keep.

## Testing
We are going to test all our code with the TestRobot.java file. But, in fact there is some objects that are **enum** then to compile we have to do like : 
```bash
javac *.java && java TestRobot
```
Which will launch the tests if and only if the compilation is successful.

But we can also use the makefile to do it. 
```bash
make
make exeLecture
```
If you want to see more things about the makefile you can open it and see the features.

## Useful Unix Command

### Found Files
If you want to search file from your directory with a specific name : 
```bash
find . -name "name-file"
```
name-file mais also be a [**wildcard**](https://tldp.org/LDP/GNU-Linux-Tools-Summary/html/x11655.htm)

### Found Text on Files
If you want to found a specific part of text onto a directory you can use :
```bash
grep "some texte to search" ./directory/to/look/ --exclude-dir="dir-to-exclude" --include="*.java" -rIl
```

## Things to Know
We are good to code with the lower [**Camel Case**](https://en.wikipedia.org/wiki/Camel_case) Format.

## Infos 
### src 
- Contains the classes provided by the teachers :
  - LecteurDonnees.java : 
    - reads all the elements of a data description file (cases, fires and robots) and displays them.
    - You have to MODIFY this class (or write a new one) to create the objects corresponding to your own classes
  - TestLecteurDonnees.java : reads a data file and displays its content
  - TestInvader : creates a "mini Invaders" simulator in a graphical window
### maps
- Some examples of data files
### bin/gui.jar
- Java archive containing the classes of the graphical interface. See an example of use in TestInvader.java
### doc
- The documentation (API) of the classes of the graphical interface contained in gui.jar. Entry point: index.html
### Makefile 
- Some explanations on online compilation, in particular the notion of classpath and the use of gui.jar

## Badges
On some READMEs, you may see small images that convey metadata, such as whether or not all the tests are passing for the project. You can use Shields to add some to your README. Many services also have instructions for adding a badge. I should look how to do it.\
![Static Badge](https://img.shields.io/badge/test1-PASSED-green)\
![Static Badge](https://img.shields.io/badge/test2-FAILED-red)\
![Static Badge](https://img.shields.io/badge/test3-FAILED-red)\
![Static Badge](https://img.shields.io/badge/test4-PASSED-green)\
Link : https://shields.io/badges
