// A class representing the tetris game grid
public class Grid {
	Square[][] squares; // a matrix storing all squares

	// Constructor
	Grid (int n_rows, int n_cols) {
		squares = new Square[n_rows][n_cols];
	}


	public void showGrid() { //Draws the contents of the grid and grid lines on screen
		for(int i=0;i<squares.length;i++) {
			for(int j=0;j<squares[i].length;j++) {
				if(squares[i][j] != null) {
					squares[i][j].draw();
				}
			}
		}
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		for(double i=.5;i<9;i++) 
			StdDraw.line(i,0.5,i,12.5);
		for(double i=.5;i<13;i++) 
			StdDraw.line(.5, i, 8.5, i);
	}

	public boolean insert(Shape s) { // inserts a shape into the grid, returns false if the shape is in an illegal position
		for( int i=0;i<s.squares.length;i++){
			for(int j=0;j<s.squares[i].length;j++) {
				if(s.squares[i][j].y>12)
						return false;
			}
		}
		for( int i=0;i<s.squares.length;i++){
			for(int j=0;j<s.squares[i].length;j++) {
				if(s.squares[i][j].color != StdDraw.GRAY) {
					squares[(int) (s.squares[i][j].x-1)][(int) (s.squares[i][j].y-1)]=s.squares[i][j];
				}

			}
		}
		return true;
	}
	public boolean isOccupied(int x,int y) { //checks if the given point is occupied
		return squares[x-1][y-1] != null; //since the canvas coordinates star from 1 and arrays start from 0 we have to substract 1 from the input coordinates
	}


	public int disappear(int i) {//disappears the given row and fills u the space with the above blocks
		int score = 0;
		for(int j=0;j<squares.length;j++) {
			score+=squares[j][i].value;
			squares[j][i] = null;
		}
		for(;i<squares[0].length-1;i++) {
			for(int j=0;j<squares.length;j++) {
				squares[j][i]=squares[j][i+1];
				if(squares[j][i] != null)
					squares[j][i].y--;
			}
		}
		return score;
	}


	public void merge(int i, int j) { //merges 2 given blocks
		squares[i][j].value += squares[i][j].value;
		squares[i][j+1] = null;
		for(int x=0;x<squares.length;x++) {
			for(int y=0;y<squares[0].length-1;y++) {
				if(squares[x][y] != null) {
					boolean connected = isConnected(x,y); //checks if a block is floating alone in the grid
					while(!connected) {
						squares[x][y].y--;//brings the block down until it has at least 1 neighbour
						squares[x][y-1] = squares[x][y];
						y--;
						connected = isConnected(x,y);
					}
				}

			}

		}

	}

	private boolean isConnected(int i,int j) { //checks if a block has at least one neighbour
		boolean left = false;
		if(i>0)
			left = squares[i-1][j] != null;
		boolean right = false;
		if (i<7)
			right = squares[i+1][j] != null;
		boolean down = true;
		if (j>0)
			down= squares[i][j-1] !=null;
		boolean up = false;
		if (j<11)
			up = squares[i][j+1] !=null;
		return left||right||down||up ;
	}
}