package queens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;


public class CSPSearch implements Search{
	
	public Board recSearch(Board current, int n){
		current.computeFitness();
		if( n == 1 ){
			if( current.isPerfect() ) return current;
		}else{
			for (int i = 0; i < n; i++) {
				Board result = recSearch(current, n-1);
				if( result != null ) return result;
				if( n % 2 != 0 ){
					current.swapBits(0, n-1);
				}else{
					current.swapBits(i, n-1);
				}
			}
		}
		
		
		return null;
		
	}
	
	public Board backtrackingSearch(Board init){
		return recSearch(init, init.queens.length);
	}
	
	public Board search(Board init) {
		return backtrackingSearch(init);
	}

	/*@Override
	public Board search(Board initial) {
		
		if( initial.testGoalState() )
			return initial;
		
		Stack<Board> s = new Stack<>();
		//TreeSet<Board> visit = new TreeSet<>();
		Board current = initial;
		s.add(current);
		//visit.add(current);
		while(s.size() > 0){
			
			current = s.pop();
			System.out.println(current);
			for( Board neighbor : succesorFunction(current) ){
				//if( !visit.contains(neighbor) ) {
					if(neighbor.testGoalState())
						return neighbor;
				//	visit.add(neighbor);	
					s.add(neighbor);
				//}
			}
		}
		return null;
	}*/

	public ArrayList<Board> succesorFunction(Board current){
		ArrayList<Board> children = new ArrayList<>();
		for (int i = 0; i < current.size; i++) {
			current.computeFitness();
			double prevFitness = current.fitness();
			int prevValue = current.queens[i];
			for (int j = 0; j < current.size; j++) {
				//if( j == prevValue ) continue;
				current.queens[i] = j;
				current.computeFitness();
				double newfitness = current.fitness();
				System.out.println(prevFitness + " " +newfitness);
				if( newfitness > prevFitness )
					children.add( current.clone() );
					
			}
			
			current.queens[i] = prevValue;
			current.computeFitness();
			
		}
		//JOptionPane.showMessageDialog(null, current+"\n"+children);
		return children;
		/*ArrayList<Board> succesors= new ArrayList<>();
		ArrayList<Integer> MRV = new ArrayList<>();
		int min = 999999,temp,index,MRVsize;
		ArrayList<ArrayList<Integer>> remValuesList = current.remainingValues();
		
		for (int i = 0; i < current.size; i++) {
			temp = remValuesList.get(i).size();
			if(temp<min){
				min = temp;
				MRV = new ArrayList<>();
				MRV.add(i);		
			}
			else if(temp == min)
				MRV.add(i);
		}
		
		// Bullshitness
		MRVsize = MRV.size();
		if(MRVsize == 1){
			index = MRV.get(0);
			succesors.add(current.moveQueen(index, remValuesList.get(index).get(0)));
			return succesors;
		}
		min = 999999;
		int[] constArray = current.countConstraints();
		for (int i = 0; i < MRVsize; i++) {
			temp = constArray[MRV.get(i)];
			index = MRV.get(i);
			if(temp<min){
				min = temp;
				//succesors.add(current.moveQueen(index, remValuesList.get(index).get(0))
			}
		}
		return succesors;*/
	}
	
}
