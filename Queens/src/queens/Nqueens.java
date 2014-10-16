package queens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;


public class Nqueens {
	
	public static int[][] constraints ;
	public static int[] possibleMoves ;
	public static int size;
	
	public Nqueens(int size) {
		this.size = size;
		constraints = new int[size][size];
		possibleMoves = new int[size];
	}
	
	//Inicializar constraint cada vez que se pruebe un nuevo camino
	//de solucion en la primera columna
	public static void initConstraints(int row){
		//ArrayList<Integer> colConstraint = new ArrayList<Integer>(Collections.nCopies(size, 1));
		possibleMoves[0]=0;
		for(int i=1;i<size;i++){
			possibleMoves[i]=size-2;
			constraints[i][row]=-1;
			if(row+i < size)
				constraints[i][row+i]=-1;
			if(row-i >= 0)
				constraints[i][row-i]=-1;
		}
		
	}

	//Actualizar contraint a medida que se coloca una reina en alguna columna
	public static void updateConstraints(int depth, int queen){
		int value = (-1*depth)-1;
		
		int cont = 1;
		for (int i = depth+1; i < size; i++,cont++) {
			for (int j = 0; j < size; j++) {
				if(constraints[i][j] <= value){
					constraints[i][j] = 0;
					possibleMoves[i]++;
				}
				if(constraints[i][j] == 0){
					if(queen+cont == j || queen-cont == j || j==queen){
						constraints[i][j]= value;
						possibleMoves[i]--;
					}
				}
			}
		}/*
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(constraints[i][j] <= value){
					constraints[i][j] = 0;
					possibleMoves[i]++;
				}
				if(constraints[i][j] == 0)
					if(Math.abs(depth - i) == Math.abs(queen - j) || j == queen){
						constraints[i][j]= value;
						possibleMoves[i]--;
					}
			}
		}*/
	}
	
	public static int getConstraintCount(int depth,int row){
		int cont = 1,res = 0;
		for (int i = depth+1; i < size; i++,cont++) {
			for (int j = 0; j < size; j++) {
				if(constraints[i][j] < 0){
					res++;
				}
				else if(constraints[i][j] == 0){
					if(row+cont == j || row-cont == j || j==row){
						res++;
					}
				}
			}
		}
		return res;
	}
	
	//Si en el dominio de constraint hay varias opciones este metodo
	//decide cual tomar en base a heuristicas
	public static int takeQueen(){
		return 0;
	}
	
	public static ArrayList<Integer> succesorFunction(int current, int depth){
		ArrayList<Integer> res = new ArrayList<>();
		int min = 999999,temp=0;
		for (int j = 0; j < size; j++) {
			if(constraints[depth+1][j] == 0){
				temp = getConstraintCount(depth+1,j);
				if(temp<min){
					min= temp;
					res = new ArrayList<>();
					res.add(j);
				}
				else if(temp == min)
					res.add(j);
			}
		}
		return res;
	}
	
	public static int[] searchSolution(){
		int[] sol ;
		int index;
		ArrayList<Integer> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		for (int i = 0; i < size; i++) {
			index = list.get(i);
			initConstraints(index);
			sol = searchWithRoot(index);
			if(sol!=null)
				return sol;
		}
		return null;
	}
	
	//Haciendo DFS se encuentra la solucion, expandiendo nodos de acuerdo a una 
	//funcion sucesor 
	public static int[] searchWithRoot(int root){
		
		System.out.println("ROOT : "+root);
		
		Stack<Integer> s = new Stack<>();
		TreeMap<Integer, Stack<Integer>> depths = new TreeMap<>(); //PROBLEMA MULTISET
		int[] solution = new int[size];
		ArrayList<Integer> neighbors;
		//Inicializar variables
		int current = root, depth = 0,currentDepth,neighbor;
		s.add(current);
		depths.put(root, new Stack<Integer>());
		depths.get(root).add(depth);
		
		while(s.size() > 0){
			
			//System.out.println(s);
			current = s.pop();
			currentDepth = depths.get(current).pop();
			if(depths.get(current).isEmpty())
				depths.remove(current);
			solution[currentDepth] = current;
			updateConstraints(currentDepth,current);
			if(currentDepth == size-1)
				return solution;
			
			neighbors = succesorFunction(current,currentDepth);
			if(neighbors.size()==0)
				continue;
			
				
			for(int i = 0;i < neighbors.size();i++){
					neighbor = neighbors.get(i);
					depth = currentDepth+1;	
					s.add(neighbor);
					if(depths.get(neighbor)==null){
						depths.put(neighbor, new Stack<Integer>());
					}
					depths.get(neighbor).add(depth);
			}
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		
		new Nqueens(50);
		long time = System.currentTimeMillis();
		System.out.println(Arrays.toString(searchSolution()));
		System.out.println("Elapsed time : "+ (System.currentTimeMillis()-time)+ " ms.");
	}
	
	public static void toString(int [][] array){
		System.out.println();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(constraints[j][i] == 0)
					System.out.print("  "+constraints[j][i]);
				else
					System.out.print(" "+constraints[j][i]);
			}
			System.out.println();
		}
	}
}
