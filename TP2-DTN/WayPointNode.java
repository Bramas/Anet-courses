import jbotsim.Node;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Queue;

public class WayPointNode extends Node{
	double step = 1;
	Point2D destination = new Point2D.Double(Math.random()*400, Math.random()*400);

	public WayPointNode(){

	}

	public void onClock() {
		if (! waypoint.isEmpty()) {
			setDirection(destination);
			if (distance(destination) > step)
				move(step);
			else {
				move(distance(destination));
				destination = new Point2D.Double(Math.random()*400, Math.random()*400);
			}
		}
	}


	public static void main(String[] args) {
		Topology tp = new Topology(400,400);
		
		tp.addNode(200, 200, new SinkNode());
		
		for(int i = 0; i < 100; i++)
		{
			tp.addNode(-1, -1, new WayPointNode());
		}
		new JViewer(tp);
		tp.start();
	}
}