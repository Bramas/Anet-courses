# ANET TP Scattering


For this tutorial, you will use the simulator _jbotsim_ developed by Arnaud Casteigts, to implement an algorithm that scatters a set of robots.
In the sequel we assumes the robots have a common coordinate system, orientation and unit length and all the robots are synchronized.

## Getting Started

First download the boilerplate project to start with a simple running example.
Open a terminal and execute the following command:

    git clone https://github.com/Bramas/Anet-courses.git
    cd Anet-courses/TP1-Scattering

Open the file Robot.java that contains the class representing a robot.
The class robots extends from the default Node class. By default a Node does nothing, but receives a lot of event from the simulator. Check out the doc at [http://jbotsim.sourceforge.net/javadoc/index.html](http://jbotsim.sourceforge.net/javadoc/index.html) to see the list of methods. Events have the prefix 'on'. For instance `onMove()` is called each time the node moves.
Our class Robot overwrite three methods to handle the `onPreClock()`,  `onClock()` and `onPostClock()` that are triggered before, upon and after each clock pulse.

__Question 1:__ Looking at the implementation, can you guess what phase of the execution of a robot each method is related to?

__Question 2:__ Can you guess what a group of robots implementing this algorithm will do?

The simulation is initiated in the main function (for simplicity, the static main function is located in the same class). Look at the comments to see how the simulation is created. To start the simulation (build the java class and launch it) use the `start.sh` by running the command:

    sh start.sh

__Then right click on the window to resume the simulation.__
You can also left click to add some robots.

__Question 3:__ What is happening?

## Your Own Algorithm

Now we want to implement an algorithm that scatter the robots that are initially located at the same position i.e., we want to reach a configuration where no two robots share the same location.

* Modify the main function so that the robots all start at the same position you can choose arbitrarily.
* Modify the `onPreClock` method to compute and store (in an attribut called `myMultiplicity`) how many robots share the same location as the robots executing the function (be careful that you are on a computer and positions are represented by a couple of floating point real number). Also, remove from the array `locations` the location of the robots that share my position.

__Question 6:__ To scatter the robot, can you modify the `onClock` method to choose a destination only by using the attributes `locations` and `myMultiplicity`?

Now we assume that the robots may not all share the same location. For a given robot r, a safe destination is a destination such that, the robots that do not share r's location cannot choose the same destination.

__Question 7:__ create a method `generateDestinations` that takes an integer `n` and return a list of `n` safe destinations (that depends only on the attribute `locations` and on the position of the current robot).

__Question 8:__ modify the `onClock` method to choose a safe destination. You are allowed to use at most one random bit (obtained with the instruction `(new Random()).nextInt(2)`)

__Question 9:__ start the simulation. In the best case, how much time it takes to scatter all the robots?

__Question 10:__ why was it a good choice to assume fully synchronized robots with common coordinate system?

__Question 11:__ how you can improve the algorithm? Explain, and implement your solution.

__Question 12:__ go to question 11.
