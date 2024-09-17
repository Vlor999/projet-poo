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

## Badges
On some READMEs, you may see small images that convey metadata, such as whether or not all the tests are passing for the project. You can use Shields to add some to your README. Many services also have instructions for adding a badge. I should look how to do it.\
![Static Badge](https://img.shields.io/badge/test1-PASSED-green)\
![Static Badge](https://img.shields.io/badge/test2-FAILED-red)\
![Static Badge](https://img.shields.io/badge/test3-FAILED-red)\
![Static Badge](https://img.shields.io/badge/test4-PASSED-green)\
Lien : https://shields.io/badges
