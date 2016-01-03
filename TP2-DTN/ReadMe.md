ANET TP Delay-Tolerant Networking
================================


In this tutorial, we will use the _jbotsim_ to study a simple problem, the data aggregation, in a dynamic network.

Problem Definition
-------------------


The network consists in mobile nodes that moves in a squared area following the *random waypoint mobility model*. The time is discrete and at each time instant (every call of the `onClock` event) a node moves by a fixed distance denoted `step`. Additionnaly, one special node, called *the sink*, has a fixed position and does not move during the simulation. Two nodes can communicate if the distance between them is smaller than a given communication range.

At the begining, all the nodes in the networks begin with an input data. A node can transmit its data to neighboring node. The duration of the transmission is one time instant. If a node receives the data of another node, the data is aggregated with its own data. Then, the aggregated data can be sent to another node as its original data, in one time instant.
However, to reduce consumption, a node can tranmit at most once.

The goal is to aggregate the data of every node in the network to the sink node with minimum duration.

For the sake of simplicity, we color a node that has a data in green, a node that does not have a data (i.e. that already transmited its data) in red, and the sink node in blue.
When two green nodes are connected, an algorithm that solves the data aggregation problem has to answer whether one of the node transmit, and which one.

Implementation
---------------

You start with two classes: the SinkNode and the WayPointNode.
The main function (located in the WayPointNode class) creates 100 WayPointNodes, one SinkNode and start the simulation.
Currently, no transmission occurs, and every node has the same color.

**Question 1:** looking at the result of the simulation and at the implementation of the WayPointNode, describe the random waypoint mobility model.

**Question 2:** change the constructor of both classes so that they start with the right color.

A simple solution to the problem can be for a node, to transmit its data directly to the sink node.

**Question 3:** overwrite the `onPreClock` event of WayPointNode. In this event, use the method `getNeighbors()` to check whether the sink node is among the neighbor of the current node. If it is the case, send the data using `send(Node destination, java.lang.Object content)` (use a simple java Object for the simulation) and change its color appropriately.

**Question 4:** look at the result of the previous solution. Propose and implement a better solution.

**Question 5:** use the Stats class to record the evolution of the number of nodes that have transmited (i.e. the number of aggregated data) over the time.


Improvements
------------

One way to reduce the duration of the data aggregation is to suppose the nodes have some knowledge about their future. We want the nodes to be aware of time of the next connection with the sink node.

**Question 6:** modify the onClock method of WayPointNode so that, instead of computing the next destination, a node compute all the next destinations so that it knows when it will be connected to the sink node. This time, called the `meetTime`, must be stored by the node.

**Question 7:** use the `meetTime` to improve the previous algorithm.

**Question 8:** what happen, how can you improve again your algorithm.


