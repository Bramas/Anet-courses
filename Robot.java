import jbotsim.Topology;
import jbotsim.ui.JViewer;
import jbotsim.Node;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;


public class Robot extends Node{

	ArrayList<Point2D> locations;
    Point2D target;

    static double EPS = 0.000001;

    @Override
    public void onPreClock() {   
        locations = new ArrayList<Point2D>();
        for (Node node : getTopology().getNodes() )
        {
        	locations.add(node.getLocation());
        }
    }

    @Override
    public void onClock(){   
    	target = locations.get(0);
    	for(Point2D r : locations)
    	{
            if(r.getX() > target.getX() || (r.getX() == target.getX() && r.getY() > target.getY()))
            {
                target = r;
            }
    	}
    }

    @Override
    public void onPostClock(){ 
        setDirection(target);
        move(Math.min(10, distance(target)));
    }

    public static void main(String[] args){
		Topology tp = new Topology(800, 200);
	    tp.setDefaultNodeModel(Robot.class);
	    tp.disableWireless();
	    tp.setSensingRange(0);
	    tp.setClockSpeed(500);
	    for (int i = 0; i < 20; i++)
	        tp.addNode(-1,-1);
	    new JViewer(tp);
	    tp.start();
	    tp.pause();
    }
}