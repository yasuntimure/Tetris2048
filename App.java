import java.awt.Font;

public class App {
	public static void main(String[] args) {
		//Canvas Settings
		StdDraw.setCanvasSize(500,600);
		StdDraw.setXscale(0.5,10.5);
		StdDraw.setYscale(0.5,12.5);
		StdDraw.clear(StdDraw.LIGHT_GRAY);
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		StdDraw.enableDoubleBuffering();
		
		Grid grid = new Grid(8,12);//initializing grid
		int totalScore = 0;//initializing score
		Square center = new Square(4,12);//starting square of all shapes
		double random = Math.random()*2;
		Shape current = null;
		if(random<1) { //first shape is initialized randomly
			current = new Shape(center,"T"); 
		}else {
			current = new Shape(center,"O"); 
		}
		boolean play = true; //starts the game
		while(play) {
			if(grid.isOccupied(4, 12)) { //if the starting point is full it means blocks are overlapping and the game is over
				break;
			}
			
			center = new Square(4,12); //sets a new Square with the starting coordinates for the next shape
			random = Math.random()*2;
			Shape next = null;
			if(random<1) { //next shape is initialized randomly
				next = new Shape(center,"T"); 
			}else {
				next = new Shape(center,"O"); 
			}
			
			current.showShape();
			boolean canGoDown = true;
			double start = System.currentTimeMillis(); // starts the clock
			while(canGoDown) {
				if (StdDraw.hasNextKeyTyped()) {//input controls
					char ch = StdDraw.nextKeyTyped();
					if(ch == 'r') {
						current.rotate(grid);
					}
					if(ch == 'a') {
						current.goLeft(grid);
					}
					if(ch == 'd') {
						current.goRight(grid);
					}
					if(ch == 's') {
						canGoDown = current.goDown(grid);
					}
				}
				updateScreen(grid,totalScore,next);
				current.showShape();
				StdDraw.show();
				double end = System.currentTimeMillis(); //ends the clock
				if(end-start>=1000) { //if at least 1000ms (1 second) have passed between last start and end, the shape goes down 1 time
					canGoDown=current.goDown(grid);
					start = System.currentTimeMillis();
				}
			}
			play = grid.insert(current); //inserts the shape at its final position to the grid
			boolean merged = true; //a flag to check consequent merges
			while(merged) {
				merged = false;
				for(int i=0;i<grid.squares.length;i++) {
					for(int j=0;j<grid.squares[0].length-1;j++) {
						if(grid.squares[i][j] != null && grid.squares[i][j+1] != null && grid.squares[i][j].value == grid.squares[i][j+1].value) {
							grid.merge(i,j);//if 2 blocks of same value on top of each other are found, calls the merge method
							merged = true;//and "activates" the flag so the grid is checked again
							updateScreen(grid, totalScore,next);
						}

					}
				}
			}

			for(int i=0;i<grid.squares[0].length;i++) {
				boolean row = true;
				for(int j=0;j<grid.squares.length;j++) {
					if(grid.squares[j][i] == null) //if there is a space in the row it means the row is not complete
						row = false;
				}
				if(row) {//if there are no spaces in a row calls the disappear method
					totalScore+=grid.disappear(i); //add the row values to the total score
					updateScreen(grid,totalScore,next);
					i--; //the pointer goes 1 step back since all the above rows are shifted down
				}

			}

			merged = true; //we check for mergeable blocks again to see if there are any new merges after disappearing the row
			while(merged) {
				merged = false;
				for(int i=0;i<grid.squares.length;i++) {
					for(int j=0;j<grid.squares[0].length-1;j++) {
						if(grid.squares[i][j] != null && grid.squares[i][j+1] != null && grid.squares[i][j].value == grid.squares[i][j+1].value) {
							grid.merge(i,j);
							merged = true;
							updateScreen(grid, totalScore,next);
						}

					}
				}
			}
			current = next;
			System.out.println("Total Score: "+ totalScore);

		}
		
		System.out.println("Game Over");
		Font stringFont = new Font( "SansSerif", Font.PLAIN, 30 );
		StdDraw.setFont(stringFont);
		StdDraw.text(4, 8, "GAME OVER");
		StdDraw.text(4, 6, "SCORE: "+totalScore);
		StdDraw.show();

	}
	public static void updateScreen(Grid grid,int score,Shape next) { //Updates and shows every stationary element on the screen
		StdDraw.clear(StdDraw.LIGHT_GRAY); //background color
		grid.showGrid();
		
		StdDraw.setPenColor(StdDraw.WHITE);
		Font stringFont = new Font( "SansSerif", Font.PLAIN, 18 );
		StdDraw.setFont(stringFont);
		StdDraw.text(9.5, 12, "SCORE:");
		StdDraw.text(9.5, 3, "NEXT");
		Font smallNumberFont = new Font( "SansSerif", Font.PLAIN, 12 );
		StdDraw.setFont(smallNumberFont);
		Square tmp = new Square(next.center.x,next.center.y);//we make a small copy of the "next" block
		tmp.value=next.center.value;
		Shape temp = new Shape(tmp,next.type+"S");
		for(int i=0;i<temp.squares.length;i++) {
			for(int j=0;j<temp.squares[i].length;j++) {
				temp.squares[i][j].value=next.squares[i][j].value;
				temp.squares[i][j].length = .4;
				temp.squares[i][j].y -= 10;
				temp.squares[i][j].x += 5.5;
			}
		}
		temp.showShape();
	
		Font numberFont = new Font( "SansSerif", Font.PLAIN, 22 );
		StdDraw.setFont(numberFont);
		StdDraw.text(9.5, 11, ""+score);
		StdDraw.show();

	}
}
