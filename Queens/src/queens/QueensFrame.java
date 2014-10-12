package queens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

import queens.Board.QueenPiece;

public class QueensFrame extends JFrame {
	
	public QueensFrame(Board ans,String title) {
		super(title);
		QueensPanel qp = new QueensPanel(ans);
		add( qp );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Dimension screenDimension = env.getMaximumWindowBounds().getSize();
		int w = (int) screenDimension.getWidth();
		int h = (int) screenDimension.getHeight();
		while( w % ans.size != 0 )
			w--;
		while( h % ans.size != 0 )
			h--;
		int theSize = Math.min(w, h);
		setSize(theSize,theSize);
		setVisible(true);
	}

	class QueensPanel extends JPanel{
		
		Board board;
		
		public QueensPanel(Board board){
			this.board = board;
		}
		
		@Override
		public void paintComponent(Graphics g){
			
			super.paintComponent(g);
			
			setBackground(Color.WHITE);
			int n = (int) Math.ceil((double)getWidth()/(double)board.size);
			int m = (int) Math.ceil((double)getHeight()/(double)board.size);
			for(QueenPiece qp : board.getPairs()){
				g.setColor(Color.red);
				g.fillOval(qp.col*n, qp.row*m, n, m);
			}
			
			g.setColor(Color.BLACK);
			for(int i=0; i<board.size; i++){
				g.drawLine(i*n, 0, i*n, getHeight());
			}
			
			for (int i = 0; i < board.size; i++) {
				g.drawLine(0, i*m, getWidth(), i*m);
			}
			
			
		}
		
	}
}
