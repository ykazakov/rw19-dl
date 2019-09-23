# Description Logic Hackathon @ RW 2019
This repository contains the source code used for the Description Logic Hackathon at the 
[15th Reasoning Web Summer School (RW 2019)](https://rulemlrr19.inf.unibz.it/rw2019/)

See the [proceeding paper](https://doi.org/10.1007/978-3-030-31423-1_1) for the theoretical material used in the code.

## Prerequisites
Please install the following tools for the Hackathon:
1. Java Development Kit (version 8 or higher). Can be obtained from [Oracle](https://www.oracle.com/technetwork/java/javase/downloads) or [OpenJDK](https://openjdk.java.net/install/)
2. Eclipse IDE. Please [download](https://www.eclipse.org/downloads/) Eclipse IDE for Java Developers (we will need Java IDE, a Git client, and Maven integration).
3. [Apache Maven](https://maven.apache.org/) version 3.1.0 or above.

## Checkout the Source Code
Follow the following steps to install the code locally:
1. Create an Eclipse Workspace
2. If you know how to use git, checkout the code from this repository: `https://github.com/ykazakov/rw19-dl.git`.
  If not:
   1. Open the git perspective in Eclipse (Window > Perspective > Open Perspective > Other... > Git)
   2. Press on the `Clone a Git Repository` icon in the `Git Repositories` window and enter the above repository URI.
   3. Press next and select `master` in the "Branch Selection" dialog (if not already selected)
   4. Press next and choose a suitable location on your computer where the files should be placed
3. Switch back to Java perspective in Eclipse (Window > Perspective > Open Perspective > Other... > Java)
4. Select File > Import > Maven > Existing Maven Projects, 
5. Click "Next". In the "Maven Projects" dialog, press `Browse..`, navigate the local directory of the source code, and press `Open`.
6. Select the project in the list (or press `Select All`) and press `Finish`.

## Test the Code
Eclipse will automatically compile all java code in the background. 
To test if everything works, select the package `src/test/java` in the package explorer and run the tests (Run > run > JUnit Test).

## Easy Assignments
The provided implementation has missing code that is marked with TODO (Window > Show View > Tasks).
Provide implementation for the missing part, working in the following order of the packages: semantics, problems, tableau.
Some tests are ignored because of the missing implementation (the tag @Ignore). Make sure that these tests work (after removing @Ignore).

## For more Adventurous
If you feel that the previous assignments are too easy, consider the following more challenging problems:
1. Implement the normalizaition of ontologies as described in Section 3.2 of the [paper](https://doi.org/10.1007/978-3-030-31423-1_1).
2. Extend the tableau solver so that it can output a model when the satisfiability was proven. For this, construct the interpretation from the expanded clash-free tableau according to Definition 3 of the [paper](https://doi.org/10.1007/978-3-030-31423-1_1). Add unit tests that verify that the returned models satisfy concepts.
4. Create a random generator for DL concepts and axioms. Add tests that verify correctness of the tableau procedure on random inputs.
5. Extend the tableau procedure to other reasoning task. Implement the blocking condition formulated in Definitions 8 and 9 of the [paper](https://doi.org/10.1007/978-3-030-31423-1_1).
6. Experiment with different strategies of rule applications by using a priority queue for fule applications instead of an ordinary (FIFO) queue.
7. Extend the tableau procedure to the language ALCH described in Exercise 10 of the [paper](https://doi.org/10.1007/978-3-030-31423-1_1).

## Some Crazy Ideas
Here are few ideas that go beyond the above exercises that can be a basis of student projects.
1. Implement a visualization tool for tableau so that one can graphically monitor the steps of the tableau procedure.
2. Parallelize the application of the tableau rules so that one can take advantage of multi-core processors. For example, one can apply rules to different nodes independently.
