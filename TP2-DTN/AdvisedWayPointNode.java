import jbotsim.Node;
import jbotsim.Topology;
import jbotsim.ui.JViewer;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.awt.*;

public class AdvisedWayPointNode extends Node{

	double step = 1;
	static public SinkNode Sink;
    public double distanceToSink = 0;
    public double traveledDistance = 0;
    public java.util.Vector<Point2D.Double> destinations = new java.util.Vector<Point2D.Double>();

	static public int nbTransmission = 0;
	static public int startingTime = 0;

	public void onStart(){
		this.setProperty("data", true);
		this.traveledDistance = 0.0;
		this.distanceToSink = 0.0;
		this.setProperty("distanceToSink", 0.0);
        this.setColor(Color.green);
		this.setCommunicationRange(30);
		this.destinations.clear();
		this.computeDestinations();
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
		
        double remainingDistance = step;
        while(true)
        {
        	if (destinations.size() == 0){
	            this.computeDestinations();
	        }
        	this.setDirection(destinations.get(0));
        	// If the next destination is close, we move to this destination and we continue to the next destination.
        	if(this.getLocation().distance(destinations.get(0)) < remainingDistance)
        	{
        		double advancedDistance = this.getLocation().distance(destinations.get(0));
        		remainingDistance -= advancedDistance;
        		this.setLocation(destinations.get(0));
        		destinations.remove(0);
        	}
        	else // Else, we move toward the next destination, by the remaining distance
        	{
        		this.move(remainingDistance);
        		break;
        	}
        }        
        traveledDistance += step;
        if(this.traveledDistance + step >= this.distanceToSink)
        {
            this.computeDestinations();
        }
	}


	public static void main(String[] args) {
		Topology tp = new Topology(600,600);
		tp.addClockListener(new Stats(tp));
		tp.setClockSpeed(1);
		new JViewer(tp);
		tp.setDefaultNodeModel(AdvisedWayPointNode.class);
		
		AdvisedWayPointNode.Sink = new SinkNode();
		tp.addNode(300, 300, AdvisedWayPointNode.Sink);
		
		for(int i = 0; i < 100; i++)
		{
			tp.addNode(-1, -1, new AdvisedWayPointNode());
		}
	}


    Point2D projection(Point2D v, Point2D w, Point2D p) {
        // Return minimum distance between line segment vw and point p
        double l2 = v.distanceSq(w);
        if (l2 == 0.0) return v;   // v == w case
        // Consider the line extending the segment, parameterized as v + t (w - v).
        // We find projection of point p onto the line. 
        // It falls where t = [(p-v) . (w-v)] / |w-v|^2
        double t = ((p.getX() - v.getX())*(w.getX() - v.getX()) + 
                    (p.getY() - v.getY())*(w.getY() - v.getY())) / l2;
        if (t < 0.0) return v;       // Beyond the 'v' end of the segment
        if (t > 1.0) return w;  // Beyond the 'w' end of the segment
        
        Point2D.Double projection = new Point2D.Double(v.getX() + t * (w.getX() - v.getX()), 
                                    v.getY() + t * (w.getY() - v.getY()));  // Projection falls on the segment
        return projection;
    }
    void computeDestinations()
    {
        Point2D origin = this.getLocation();
        boolean firstPass = true;
        if(destinations.size() > 0)
        {
            origin = destinations.lastElement();
            firstPass = false;
        }

        while(true)
        {
            Point2D.Double target = new Point2D.Double(Math.random()*600, Math.random()*600);
            destinations.add(target);
            Point2D proj = projection(origin, target, this.Sink.getLocation());
            
            if(proj.distance(this.Sink.getLocation()) < this.getCommunicationRange() - 1.0 
            	&& (!firstPass ||
            		this.getLocation().distance(this.Sink.getLocation()) > this.getCommunicationRange()
            		)
            	)
            {
                this.distanceToSink += origin.distance(proj);
                this.setProperty("distanceToSink", this.distanceToSink);
                return;
            }
            firstPass = false;
            this.distanceToSink += origin.distance(target);
            origin = target;
        }
    }

}