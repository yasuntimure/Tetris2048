public class Shape {
	Square center; //each shape has a center square pre determined as a starting position
	char type; //each shape has a type to determine how we will draw it
	Square [][] squares; //each shape is made out of a square matrix

	public Shape(Square center,String type ) {//constructor to create a shape block
		this.center=center;
		this.type=(type.toUpperCase()).charAt(0);//since the input can be lowercase or uppercase we take a string as input then convert it into uppercase char

		switch (type) {

		case "T" :
			squares = new Square[3][3];
			squares[0][0] = new Square(center.x-1,center.y+1,StdDraw.GRAY);
			squares[0][1] = new Square(center.x,center.y+1,StdDraw.GRAY);
			squares[0][2] = new Square(center.x+1,center.y+1,StdDraw.GRAY);
			squares[1][0] = new Square(center.x-1,center.y);
			squares[1][1] = center;
			squares[1][2] = new Square(center.x+1,center.y);
			squares[2][0] = new Square(center.x-1,center.y-1,StdDraw.GRAY);
			squares[2][1] = new Square(center.x,center.y-1);
			squares[2][2] = new Square(center.x+1,center.y-1,StdDraw.GRAY);
			break;

		case "I" :
			break;

		case "S" :
			break;

		case "O" :
			squares = new Square[2][2];
			squares[0][0] = new Square(center.x-1,center.y);
			squares[0][1] = center;
			squares[1][0] = new Square(center.x-1,center.y-1);
			squares[1][1] = new Square(center.x,center.y-1);
			break;

		case "L" :
			break;

		case "J" :
			break;

			//cases from here on are "small" cases specifically to display the "NEXT" block
		case "TS" :
			squares = new Square[3][3];
			squares[0][0] = new Square(center.x-.4,center.y+.4,StdDraw.GRAY);
			squares[0][1] = new Square(center.x,center.y+.4,StdDraw.GRAY);
			squares[0][2] = new Square(center.x+.4,center.y+.4,StdDraw.GRAY);
			squares[1][0] = new Square(center.x-.4,center.y);
			squares[1][1] = center;
			squares[1][2] = new Square(center.x+.4,center.y);
			squares[2][0] = new Square(center.x-.4,center.y-.4,StdDraw.GRAY);
			squares[2][1] = new Square(center.x,center.y-.4);
			squares[2][2] = new Square(center.x+.4,center.y-.4,StdDraw.GRAY);
			break;

		case "IS" :
			break;

		case "SS" :
			break;

		case "OS" :
			squares = new Square[2][2];
			squares[0][0] = new Square(center.x-.4,center.y);
			squares[0][1] = center;
			squares[1][0] = new Square(center.x-.4,center.y-.4);
			squares[1][1] = new Square(center.x,center.y-.4);
			break;

		case "LS" :
			break;

		case "JS" :
			break;


		}


	}

	public void rotate(Grid grid) { //rotates shapes around their center counterclockwise each shape has unique rotation
		switch (type) {

		case 'T' :
			boolean can = true;
			for( int i=0;i<squares.length;i++){
				for(int j=0;j<squares[i].length;j++) {
					if(squares[i][j].y>12)
						continue;
					if(squares[i][j].color == StdDraw.GRAY &&( squares[i][j].x == 0 || squares[i][j].x == 9 || squares[i][j].y == 0 ||squares[i][j].y == 12))
						can = false;
					if(can) {
						if((center.x-2>0 && grid.isOccupied((int)center.x-1,(int)center.y))|| (center.x<8&&grid.isOccupied((int)center.x+1,(int)center.y)) || (center.y-1>0 &&grid.isOccupied((int)center.x,(int)center.y-1))||(center.y+1<1 &&grid.isOccupied((int)center.x,(int)center.y+1)))
							can = false;
					}
					if (can) {
						if(squares[i][j].color == StdDraw.GRAY && ((squares[i][j].y-1>0 &&grid.isOccupied((int)(squares[i][j].x),(int)(squares[i][j].y-1)))||(squares[i][j].x<8&&grid.isOccupied((int)(squares[i][j].x+1),(int)(squares[i][j].y)))||(squares[i][j].x-1>0&&grid.isOccupied((int)(squares[i][j].x-1),(int)(squares[i][j].y)))))
							can = false;
					}
				}
			}
			if(can) {
				int temp = (int) squares[0][1].x;
				squares[0][1].x = squares[1][0].x;
				squares[1][0].x = squares[2][1].x;
				squares[2][1].x = squares[1][2].x;
				squares[1][2].x = temp;
				temp = (int) squares[0][1].y;
				squares[0][1].y = squares[1][0].y;
				squares[1][0].y = squares[2][1].y;
				squares[2][1].y = squares[1][2].y;
				squares[1][2].y = temp;
			}
			break;

		case 'I' :
			;

		case 'S':

		case 'O' :
			int temp = (int) squares[0][0].x;
			squares[0][0].x = squares[0][1].x;
			squares[0][1].x=squares[1][1].x;
			squares[1][1].x=squares[1][0].x;
			squares[1][0].x = temp;
			temp = (int) squares[0][0].y;
			squares[0][0].y = squares[0][1].y;
			squares[0][1].y=squares[1][1].y;
			squares[1][1].y=squares[1][0].y;
			squares[1][0].y = temp;
			break;

		case 'L' :

		case 'J' :

		}
	}

	public boolean goDown(Grid grid) { //checks if a shape can go down and goes down if it can
		boolean can = true;
		for( int i=0;i<squares.length;i++){
			for(int j=0;j<squares[i].length;j++) {
				if(squares[i][j].color != StdDraw.GRAY && squares[i][j].y == 1)
					can = false;
			}
		}
		if(can) {
			for( int i=0;i<squares.length;i++){
				for(int j=0;j<squares[i].length;j++) {
					if(squares[i][j].y>12)
						continue;
					if(squares[i][j].color != StdDraw.GRAY && grid.isOccupied((int)(squares[i][j].x),(int)(squares[i][j].y-1)))
						can = false;
				}
			}
		}
		if(can) {
			for( int i=0;i<squares.length;i++){
				for(int j=0;j<squares[i].length;j++) {
					squares[i][j].y--;
				}
			}
		}
		return can;
	}
	public void goRight(Grid grid) { //checks if a shape can go right and goes if it can
		boolean can = true;
		for( int i=0;i<squares.length;i++){
			for(int j=0;j<squares[i].length;j++) {
				if(squares[i][j].color != StdDraw.GRAY && squares[i][j].x == 8)
					can = false;
			}
		}
		if(can) {
			for( int i=0;i<squares.length;i++){
				for(int j=0;j<squares[i].length;j++) {
					if(squares[i][j].y>12)
						continue;
					if(squares[i][j].color != StdDraw.GRAY && grid.isOccupied((int)(squares[i][j].x+1),(int)(squares[i][j].y)))
						can = false;
				}
			}
		}
		if(can) {
			for( int i=0;i<squares.length;i++){
				for(int j=0;j<squares[i].length;j++) {
					squares[i][j].x++;
				}
			}
		}
	}
	public void goLeft(Grid grid) { //checks if a shape can go left and goes if it can
		boolean can = true;
		for( int i=0;i<squares.length;i++){
			for(int j=0;j<squares[i].length;j++) {
				if(squares[i][j].color != StdDraw.GRAY && squares[i][j].x == 1)
					can = false;
			}
		}
		if(can) {
			for( int i=0;i<squares.length;i++){
				for(int j=0;j<squares[i].length;j++) {
					if(squares[i][j].y>12)
						continue;
					if(squares[i][j].color != StdDraw.GRAY && grid.isOccupied((int)(squares[i][j].x-1),(int)(squares[i][j].y)))
						can = false;
				}
			}
		}
		if(can) {
			for( int i=0;i<squares.length;i++){
				for(int j=0;j<squares[i].length;j++) {
					squares[i][j].x--;
				}
			}
		}
	}

	public void showShape() { //uses the Square's draw method to display the shape on the screen
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(center.x, center.y, center.length/2.0, center.length/2.0);
		for( int i=0;i<squares.length;i++){
			for(int j=0;j<squares[i].length;j++) {
				if(squares[i][j].color != StdDraw.GRAY) {
					squares[i][j].draw();		
				}
			}
		}
	}
}
