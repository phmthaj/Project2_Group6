package oop.dsai.project.shape;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public final class Circle {
	
	private static final Font font =  Font.font("Vernada", FontWeight.SEMI_BOLD, 23);
	private static final int RADIUS = 20;
	
	private Integer searchKey;
	
	// The circle attributes
	private Point2D point;
	private Color backgroundColor;
	private Color borderColor;
	private Color fontColor;

	public Circle(Integer searchKey) {
		this.searchKey = searchKey;
		this.backgroundColor = Color.rgb(51, 153, 255);
		this.borderColor = Color.rgb(0, 0, 104);
		this.fontColor = Color.rgb(255, 255, 255);
	}
	
	public Circle(Integer searchKey, Point2D point) {
		this.searchKey = searchKey;
		this.point = point;
		this.backgroundColor = Color.rgb(51, 153, 255);
		this.borderColor = Color.rgb(0, 0, 104);
		this.fontColor = Color.rgb(255, 255, 255);
		
	}

	
	public void draw(GraphicsContext gc) {
		gc.setLineWidth(3); // Sets the width of the lines
		
		// Create a circle 
		gc.setFill(backgroundColor);
		gc.fillOval(point.getX() - RADIUS, point.getY() - RADIUS, 2 * RADIUS, 2 * RADIUS);
		
		// Outline the circle border
		gc.setStroke(borderColor);
		gc.strokeOval(point.getX() - RADIUS, point.getY() - RADIUS, 2 * RADIUS, 2 * RADIUS);

		// Draw the id number inside the circle
		gc.setFont(font);
		gc.setFill(fontColor);
		gc.fillText(this.getKey(),
				point.getX()-12*getKey().length()/2,
				point.getY()+8);
	}

	private String getKey() {
		return Integer.toString(getSearchKey());
	}
	
	public Integer getSearchKey() {
		return this.searchKey;
	}
	
	public void setSearchKey(Integer searchKey) {
		this.searchKey = searchKey;
	}
	
	public void setPoint(Point2D point) {
		this.point = point;
	}
	
	public void setHighlighter(boolean highlight) {
		if (highlight) {
			this.fontColor = Color.rgb(255, 255, 255);
			this.backgroundColor = Color.rgb(228, 235, 31);
			this.borderColor = Color.rgb(0, 0, 104);
	
		} else {
			this.backgroundColor = Color.rgb(51, 153, 255);
			this.borderColor = Color.rgb(0, 0, 104);
			this.fontColor = Color.rgb(255, 255, 255);
		}
	}
}
