package queens;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;


public class CSPSearch implements Search{

	@Override
	public Board search(Board initial) {
		
		Stack<Board> s = new Stack<>();
		TreeSet<Board> visit = new TreeSet<>();
		Board current = initial;
		s.add(current);
		visit.add(current);
		while(s.size() > 0){
			current = s.pop();
			for( Board neighbor : succesorFunction(current) ){
				if( !visit.contains(neighbor) ) {
					if(neighbor.testGoalState())
						return neighbor;
					visit.add(neighbor);	
					s.add(neighbor);
				}
			}
		}
		return null;
	}

	public ArrayList<Board> succesorFunction(Board current){
		ArrayList<Board> succesors= new ArrayList<>();
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
		return succesors;
	}
	
}
