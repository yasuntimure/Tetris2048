import java.awt.Color;

public class Square {
	int value; //each square has a value, dummy squres have a value of 0
	double x; //x coordinate of where the square is located on the canvas
	double y; //y coordinate of where the square is located on the canvas
	double length = 1.0; //length of the square
	Color color; //color of the square determined by it's value

	public Square(double x,double y) { //The main constructor we use to create squares
		double random = Math.random()*2;
		if(random<1) {
			this.value =2;
			this.color= new Color(255,223,196);
		}else {
			this.value =4;
			this.color = StdDraw.ORANGE;
		}
		this.x=x;
		this.y=y;
		this.length=1;		
	}
	public Square(double x, double y,Color color) { //dummy square constructor
		this.value=0;
		this.x=x;
		this.y=y;
		this.length=1;
		this.color = color;

	}
	public void draw() { //draws the square with color based on its value

		switch (value) {

		case 2:
			color = new Color(255,223,196);
			break;

		case 4:
			color = StdDraw.ORANGE;
			break;

		case 8:
			color = new Color(255,132,83);
			break;

		case 16:
			color = new Color(255,83,83);
			break;

		case 32:
			color = new Color(255,40,50);
			break;

		case 64:
			color = new Color(255,0,0);
			break;

		case 128:
			color = new Color(255,255,155);
			break;

		case 256:
			color = new Color(255,255,100);
			break;

		case 512:
			color = new Color(255,255,50);
			break;

		case 1024:
			color = new Color(255,255,0);
			break;

		case 2048:
			color = new Color(202,0,138);
			break;
		}
		StdDraw.setPenColor(color);
		StdDraw.filledRectangle(x, y, length/2.0, length/2.0);
		StdDraw.setPenColor(StdDraw.GRAY);
		StdDraw.text(x, y, ""+ value);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(x, y, length/2.0, length/2.0);//draws a black rectangle for specifying the edges 
	}
}
