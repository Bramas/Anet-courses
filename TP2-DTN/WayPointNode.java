import jbotsim.*;
import jbotsim.event.*;
import jbotsim.ui.JViewer;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.*;

public class WayPointNode extends Node {
	double step = 1;
	Point2D destination = new Point2D.Double(Math.random()*400, Math.random()*400);
	static public int nbTransmission = 0;
	static public int startingTime = 0;

	public void onStart(){
		this.setProperty("data", true);
        this.setColor(Color.green);
		this.setCommunicationRange(30);
	}

	private boolean doWeTransmitToNode(Node node)
	{
		return false;
	}

	public void onClock()
	{
		//If we don't have data, we cannot do anything
		if(!(boolean)this.getProperty("data"))
		{
			return;
		}

		java.util.List<Node> neigList = getNeighbors();
        for(Node node: neigList) {
            if((boolean)node.getProperty("data"))
            {
                if(doWeTransmitToNode(node))
                {
                	setProperty("data", false);
                	setColor(Color.red);
                	nbTransmission++;
                	break;
                }
            } 
        }


        if(nbTransmission == this.getTopology().getNodes().size() - 1)
        {
        	System.out.println("Aggregation Done in "+(this.getTopology().getTime() - startingTime)+" time unit ");
        	nbTransmission = 0;
        	this.getTopology().restart();
        	startingTime = this.getTopology().getTime();
        }

	}

	public void onPreClock() {
		
		setDirection(destination);
		if (distance(destination) > step)
			move(step);
		else {
			move(distance(destination));
			destination = new Point2D.Double(Math.random()*600, Math.random()*600);
		}

	}


	public static void main(String[] args) {
		Topology tp = new Topology(600,600);
		tp.addClockListener(new Stats(tp));
		tp.setClockSpeed(1);
		new JViewer(tp);
		tp.setDefaultNodeModel(WayPointNode.class);
		
		tp.addNode(300, 300, new SinkNode());
		
		for(int i = 0; i < 100; i++)
		{
			tp.addNode(-1, -1, new WayPointNode());
		}
	}
}