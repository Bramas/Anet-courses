ANET TP Delay-Tolerant Networking
================================


In this tutorial, we will use the _jbotsim_ simulator to study the data aggregation problem, in a dynamic network.

Problem Definition
-------------------


The network consists in mobile nodes that moves in a squared area following the *random waypoint mobility model*. The time is discrete and at each time instant (every call of the `onClock` event) a node moves by a fixed distance denoted `step`. Additionnaly, one special node, called *the sink*, has a fixed position and does not move during the simulation. Two nodes can communicate if the distance between them is smaller than a given communication range.

At the begining, all the nodes in the networks begin with an input data. A node can transmit its data to a neighboring node. The transmission is considered atomic. If a node receives the data of another node, the data is aggregated with its own data. Then, the aggregated data can be sent to another node as its original data.
However, to reduce consumption, a node can tranmit at most once.

The goal is to aggregate the data of every node in the network to the sink node with minimum duration.

For the sake of simplicity, we color a node that has a data in green, a node that does not have a data (i.e. that already transmited its data) in red, and the sink node in blue.
When two green nodes are connected, **an algorithm that solves the data aggregation problem has to answer whether one of the node transmit, and which one.**

Implementation
---------------

You start with two classes: the SinkNode and the WayPointNode.
The main function (located in the WayPointNode class) creates 100 WayPointNodes, one SinkNode and start the simulation.
Currently, no transmission occurs.

**Question 1:** looking at the result of the simulation and at the implementation of the WayPointNode, describe the random waypoint mobility model.

**The method `doWeTransmitToNode(Node node)` is called every time the current node can transmit to a node. To implement a data aggregation algorithm, you just have to complete this method and return true when you want to trasmit to the node given in argument and return false otherwise.**

**Question 2:** Implement the simplest solution that consist in transmiting the data **only to the sink node**.

**Question 3:** look at the result of the previous solution. Propose and implement a better solution.

Adding Some Knowledge
--------------------

One way to reduce the duration of the data aggregation is to suppose the nodes have some knowledge about their future. The class AdvisedWayPointNode use the same mobility model but is aware of the distance before the next connection with the sink node. The distance needed, from the begining of the execution, to meet the sink node is stored in the property `distanceToSink`.

**Question 4:** How you can use the property `distanceToSink` to improve the data aggregation time.

**Question 5:** how can you improve again your algorithm.

The Stats class is a ClockListener whose method onClock is called every time instant. This class can be used to study the simulation in a centralized manner.

**Question 6:** use the Stats class to record the evolution of the number of nodes that have transmited (i.e. the number of aggregated data) over the time.

