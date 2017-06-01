package AStar;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class BadGuy {
	
	Image myImage;
	int x=0,y=0;
	Stack<Node> path = new Stack<Node>();
	ArrayList<Node> open = new ArrayList<Node>();
	ArrayList<Node> closed = new ArrayList<Node>();
	boolean hasPath=false;
	
	// anonymous class to sort by f cost
	private Comparator<Node> fComparator = new Comparator<Node>(){

		@Override
		public int compare(Node n1, Node n2) {
			if(n2.getF() < n1.getF())
				return 1;
			if(n2.getF() > n1.getF())
				return -1;
			
			return 0;
		}
		
	};

	public BadGuy( Image i ) {
		myImage=i;
		x = 30;
		y = 10;
	}

	public void reCalcPath(boolean map[][],int targx, int targy) {
		// TO DO: calculate A* path to targx,targy, taking account of walls defined in map[][]
		open.clear();
		path.clear();
		closed.clear();
		hasPath = false;
		Node[][] nodeMap = new Node[map.length][map.length];
		
		// initialise map of nodes
		for(int x =0;x<map.length;x++){
			for(int y=0;y<map.length;y++){
				nodeMap[x][y] = new Node(x,y);	
			}
		}
		
		//set up the start or begining node
		Node start = nodeMap[x][y];
		start.setH(heuristic(start,nodeMap[targy][targx]));
		start.setG(0);
		start.setF(start.getH());
		open.add(start);

		 while(open.size()>0 && !closed.contains(nodeMap[targx][targy])){
		
			 // simple sort of open list by rank of lowest f value using comparator defined at start of class.
					Collections.sort(open, fComparator);
					Node current = open.get(0);
			
			// remove current node form open to closed
				open.remove(current);
				closed.add(current);
			// if we have reached the goal node we stop and retrace our steps, placing steps into path list
				if(current == nodeMap[targx][targy]){
					Node temp = current;
					while(temp != null){
						path.push(temp);
						temp = temp.getParent();	
					}
					hasPath = true;
					
					break;
				}
					
				int cx =current.getX();
				int cy = current.getY();
				
				// check all nodes around current node
				
				for (int xx=-1;xx<=1;xx++) {
					for (int yy=-1;yy<=1;yy++) {
						// if we go over the boundaries, skip iteration
						if(xx+cx >=40 || xx+cx >=40 || xx+cx <0 || xx+cx <0 || (yy == 0 && xx == 0))
							continue;
						// as long as node we check is not true (alive) and not already in closed list we evaluate it.
						if(!closed.contains(nodeMap[xx+cx][yy+cy])&& !map[xx+cx][yy+cy]){
								
							int G = ((xx == 1 || xx == 1) && (yy == -1 || yy ==1))? 14:10;	
							int tempG  = current.getG() + G;
							if(!open.contains(nodeMap[xx+cx][yy+cy])){
								open.add(nodeMap[xx+cx][yy+cy]);
							}	
							if(nodeMap[xx+cx][yy+cy].getG() > tempG){
								nodeMap[xx+cx][yy+cy].setG(tempG);
							}
							nodeMap[xx+cx][yy+cy].setH(heuristic(nodeMap[xx+cx][yy+cy],nodeMap[targx][targy]));	
							nodeMap[xx+cx][yy+cy].setF( nodeMap[xx+cx][yy+cy].getH() + nodeMap[xx+cx][yy+cy].getG());
							nodeMap[xx+cx][yy+cy].setParent(current);
						}
						
					}
				}
		
				
		 }
			
		}


	// gets value h using the absolute distance to the given x,y.
	public int heuristic(Node src, Node dest){
		int distance = (Math.abs(src.getX() - dest.getX()) + Math.abs(src.getY() - dest.getY()));
		return distance;
	}
	
	
	public void move(boolean map[][],int targx, int targy) {
		reCalcPath(map,targx,targy);
		if (hasPath) {
		if(!path.isEmpty()){
				Node a = path.pop();
				a = path.pop();
				System.out.println(a.toString());
				x = a.getX();
				y = a.getY();
		}
		}
		else {
			// no path known, so just do a dumb 'run towards' behaviour
			int newx=x, newy=y; 	
			if (targx<x)
				newx--;
			else if (targx>x)
				newx++;
			if (targy<y)
				newy--;
			else if (targy>y)
				newy++;
			if (!map[newx][newy]) {
				x=newx;
				y=newy;
			}
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(myImage, x*20, y*20, null);
	}
	
}


