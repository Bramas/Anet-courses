import java.awt.*;
import java.util.*;
import java.util.List;

import jbotsim.event.*;
import jbotsim.*;

public class Stats implements ClockListener {
	
	private Topology tp;

	public Stats(Topology tp) {
		this.tp = tp;
	}

    @Override
    public void onClock() {
        List<Node> nodes = tp.getNodes();
        for(Node node : nodes)
        {
        	
        }
    }

}