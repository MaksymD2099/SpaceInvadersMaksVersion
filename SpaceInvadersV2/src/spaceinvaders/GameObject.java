package spaceinvaders;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import spaceinvaders.Vector2D;

public class GameObject {
    protected Rectangle rectangle;
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private double height;
    private double width;
    
    public GameObject(Vector2D position, Vector2D velocity, Vector2D acceleration, double height, double width)
    {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration; 
        this.height = height;
        this.width = width;
              
        rectangle = new Rectangle(height, width);
        rectangle.setX(position.getX());
        rectangle.setY(position.getY());
        rectangle.setLayoutX(-width*0.5f);
        rectangle.setLayoutY(height*0.5f);
    }
    
    public GameObject(Vector2D position, Vector2D velocity, Vector2D acceleration, double height, double width, int index)
    {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration; 
        
        rectangle = new Rectangle(height, width);        
        rectangle.setX(position.getX());
        rectangle.setY(position.getY());
    }
    
    public Rectangle getRectangle()
    {
        return rectangle;
    }
    
    public void update(double dt)
    {
        // Euler Integration
        // Update velocity
        Vector2D frameAcceleration = getAcceleration().mult(dt);
        velocity = getVelocity().add(frameAcceleration);

        // Update position
        position = getPosition().add(getVelocity().mult(dt));
        rectangle.setX(getPosition().getX());
        rectangle.setY(getPosition().getY());

        
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }
    
    public void setVelocity(Vector2D velocity){
        this.velocity = velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }
    
    public double getRectangleHeight()
    {
        height = rectangle.getHeight();
      
        return height;
    }
    
    public double getRectangleWidth()
    {
          width = rectangle.getWidth();
          
          return width;
    }
    
  /*  public void setRectangleHeight(double height)
    {
        this.height = height;
    }
    
     public void setRectangleWidth(double width)
    {
        this.width = width;
    }*/
    
    public Rectangle getCoordinates()
    {
        return new Rectangle(position.getX(), position.getY(), getRectangleHeight() , getRectangleWidth());
    }
}
