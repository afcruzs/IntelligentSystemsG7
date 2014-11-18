package unalcol.agents.examples.squares.grupo7;

import java.util.ArrayList;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.squares.Squares;

public class Grupo7BoxesAgent implements AgentProgram {

	Matrix matrix;
	String color;

	public Grupo7BoxesAgent(String color) {
		this.color = color;
	}

	@Override
	public Action compute(Percept p) {
		if(matrix == null)
			matrix = new Matrix(Integer.parseInt(p.getAttribute(Squares.SIZE).toString()));
		//}else matrix.initBoardOnly();
		
		/*
		 * try{ Thread.sleep(1000); }catch(Exception e){}
		 */
		if (p.getAttribute(Squares.TURN).equals(color)) {
			for (int i = 0; i < matrix.n; i++)
				for (int j = 0; j < matrix.n; j++) {
					
					matrix.colorIfNeeded((String) p.getAttribute(i+":"+j+":color"));
					if ( i < matrix.n - 1 && j < matrix.n - 1 ) {
						if (((String) p.getAttribute(i + ":" + j + ":"
								+ Squares.BOTTOM)).equals(Squares.TRUE)) {
							matrix.addLine(i, j, Squares.BOTTOM);
							matrix.addLine(i + 1, j, Squares.TOP);
						}
						
						if (((String) p.getAttribute(i + ":" + j + ":"
								+ Squares.RIGHT)).equals(Squares.TRUE)) {
							matrix.addLine(i, j, Squares.RIGHT);
							matrix.addLine(i, j + 1, Squares.LEFT);
						}
					} else if ( j < matrix.n - 1 ) {
						if (((String) p.getAttribute(i + ":" + j + ":"
								+ Squares.RIGHT)).equals(Squares.TRUE)) {
							matrix.addLine(i, j, Squares.RIGHT);
							matrix.addLine(i, j + 1, Squares.LEFT);
						}
					} else if ( i < matrix.n - 1 ) {
						if (((String) p.getAttribute(i + ":" + j + ":"
								+ Squares.BOTTOM)).equals(Squares.TRUE)) {
							matrix.addLine(i, j, Squares.BOTTOM);
							matrix.addLine(i + 1, j, Squares.TOP);
						}
					}					
				}
		}

		Line line = matrix.getRandomLine();
		if( line != null ){
			matrix.addLine(line);
			return new Action(line.i+":"+line.j+":"+line.getStringSide());
		}
		
		/*
		else{
			Random r = new Random();
			//JOptionPane.showMessageDialog(null, "lolol");
			// TODO: Minimax
			int i = r.nextInt(matrix.n);
			int j = r.nextInt(matrix.n);
			return new Action(i + ":" + j + ":left");
		}
		
		
		return null;*/
		
		System.out.println(color+" Lines: "+matrix.evaluationLines());
		//try{ Thread.sleep(300000); }catch(Exception e){}
		
		
		System.out.println("We are gonna minimax!! ASLKJDKJSLA");
		MiniMaxValue value = miniMaxWithAlphaBeta(matrix, color, 0,Integer.MIN_VALUE, Integer.MAX_VALUE);
		Line optimalLine = value.line;
		return new Action(optimalLine.i+":"+optimalLine.j+":"+optimalLine.getStringSide());
	}
	
	public MiniMaxValue miniMaxWithAlphaBeta(Matrix matrix, String player ,int depth, int alpha, int beta){
		System.out.println(matrix.white + " " +matrix.black);
		depth++;		
		if( matrix.isOver() || matrix.evaluationLines().size() == 0 )
			return new MiniMaxValue( null, evaluateFunction(matrix, depth), depth );
		
		ArrayList<ExpandingLine> possibleLines = matrix.evaluationLines();
		
		if( possibleLines.get(0).expandedBoxes <= 4 ) return new MiniMaxValue(
				possibleLines.get(0), evaluateFunction(matrix.newState(possibleLines.get(0), player), depth), depth
				);
		
		System.out.println("asdsd");
		MiniMaxValue best = null;
		for( Line t : possibleLines ){
			MiniMaxValue miniMaxValue = miniMaxWithAlphaBeta(matrix.newState(t, player), 
					Matrix.opposite(player), depth, alpha, beta);
			
			miniMaxValue.line = new Line( t.i, t.j, t.side );
			
			if( best == null )
				best = miniMaxValue;
			else if( (player.equals(color) && miniMaxValue.value > best.value) || 
						(!player.equals(color) && miniMaxValue.value < best.value) )
							best = miniMaxValue;
			
			if( player.equals(color) ){
				alpha = Math.max(alpha, miniMaxValue.value);
				if( beta <= alpha ){
					break;
				}
			}else{
				beta = Math.min(beta, miniMaxValue.value);
				if(beta <= alpha){
					break;
				}
				
			}
				
		}
		
		return best;
	}
	
	private int evaluateFunction(Matrix matrix, int depth) {
		//TODO: Calcular el puntaje maximo y cuadrar con la profunidad
		//para que se demore mas en caso de que perder sea iniminente.
		if( color.equals(Squares.WHITE) )
			return matrix.white - matrix.black;
		else
			return matrix.black - matrix.white;
	}

	class MiniMaxValue implements Comparable<MiniMaxValue>{
		Line line;
		int value;
		int depth;
		
		public MiniMaxValue(Line line, int v,int depth){
			this.line = line;
			this.value = v;
			this.depth = depth; 
		}

		@Override
		public int compareTo(MiniMaxValue o) {
			return value-o.value;
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
}
