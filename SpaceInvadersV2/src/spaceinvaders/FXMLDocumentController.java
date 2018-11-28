/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;


import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


/**
 *
 * @author Family Desktop
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    AnchorPane pane;
  
    //@FXML
    //private ImageView mainSpaceShip;
    
    @FXML
    private ImageView projectileLaser;
    
    @FXML
    private Label MouseXvalue;
    
    private double lastFrameTime = 0.0;
    
    private ArrayList<GameObject> objectList = new ArrayList<>();
    private ArrayList<Projectile> projectileList = new ArrayList<>();
    private Enemies[][] enemies = new Enemies[5][10];        
    private ArrayList<Shield> shield = new ArrayList<>();
    private SpaceShip_Main SpaceShip = null;
   
    //Projectile number in the list
    int projectile_count = -1;
    
    public void addToPane(Node node)
    {
        pane.getChildren().add(node);
    }
    
    
    @FXML
    public void mouseClickedShoot(MouseEvent event)
    {      
      
      projectile_count++;
      Vector2D init_position = SpaceShip.getPosition();
      Vector2D velocity = new Vector2D(0,-600);
     //velocity.normalize();
     //velocity = velocity.mult(PROJECTILE_SPEED);
      
      
      projectileList.add(new Projectile(init_position , velocity, 10, 30));      
      addToPane(projectileList.get(projectile_count).getRectangle());     
      objectList.add(projectileList.get(projectile_count));
      
      
    }
    
    
    @FXML
    public void mouseMoved(MouseEvent event)
    {
        double x = event.getSceneX();
        MouseXvalue.setText(x+ ""); //Debug
         
        SpaceShip.setPosition(new Vector2D(event.getX(),500));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     lastFrameTime = 0.0f;
     long initialTime = System.nanoTime();
      
    AssetManager.preloadAllAssets();
     
    //Create and Initialize all objects
    SpaceShip = new SpaceShip_Main(new Vector2D(350,500),new Vector2D(0,0), 100, 100);  
    shield.add(new Shield(new Vector2D( 70, 420), 120, 40));
    shield.add(new Shield(new Vector2D( 370, 420), 120, 40));
    shield.add(new Shield(new Vector2D( 670, 420), 120, 40));
    
    int x_pos = 10;
    int y_pos = 70;
    
       for (int i = 0; i < 4; i++) {           
           x_pos = 10;
           y_pos += 80;            
            for (int j = 0; j < 8; j++) {
                
                enemies[i][j] = new Enemies(new Vector2D(x_pos += 70, y_pos ), new Vector2D(20,0), 40, 40, i);                
            }          
        }

    
    
    //Add background
    pane.setBackground(AssetManager.getBackgroundImage());
    
    //Add to the AnchorPane
    addToPane(SpaceShip.getRectangle());
    addToPane(shield.get(0).getRectangle());
    addToPane(shield.get(1).getRectangle());
    addToPane(shield.get(2).getRectangle());
    
    for (int i = 0; i < 4; i++) {
        for(int j = 0; j < 8;j++)
        {
          addToPane(enemies[i][j].getRectangle());       
        }
    }
    
    //Add to the list of all Objects in the scene
    objectList.add(SpaceShip);
    objectList.add(shield.get(0));
    objectList.add(shield.get(1));
    objectList.add(shield.get(2));
    
     
     for (int i = 0; i < 4; i++) {
        for(int j = 0; j < 8;j++)
        {
          objectList.add(enemies[i][j]);
        }
    }
    
    
    new AnimationTimer()
        {
            @Override
            public void handle(long now) {
                // Time calculation                
                double currentTime = (now - initialTime) / 1000000000.0;
                double  frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                for(GameObject obj : objectList)
                {
                    obj.update(frameDeltaTime);
                }
          
    //Animate Enemy Movement
        if(enemies[3][7].getPosition().getX() >= 750 )    
        {
             for (int i = 0; i < 4; i++) {
                 
                for(int j = 0; j < 8;j++)
                {
                enemies[i][j].setVelocity(new Vector2D(-20,0));
                }
            } 
             
        } else if(enemies[0][0].getPosition().getX() <= 10 )
        {
            for (int i = 0; i < 4; i++) {
                 
                for(int j = 0; j < 8;j++)
                {
                enemies[i][j].setVelocity(new Vector2D(20,0));
                }
            } 
        }
    //************
        
    
                /*
                // Collision detection and response
               for(int i=0; i<objectList.size(); i++)
                {
                    for (int j=i+1; j<objectList.size(); j++)
                    {
                        if (objectList.get(i) instanceof Planet && 
                            objectList.get(i) instanceof Planet)
                        {
                            Circle circle1 = objectList.get(i).getCircle();
                            Circle circle2 = objectList.get(j).getCircle();

                            Vector2D c1 = new Vector2D(circle1.getCenterX(), circle1.getCenterY());
                            Vector2D c2 = new Vector2D(circle2.getCenterX(), circle2.getCenterY());

                            Vector2D n = c2.sub(c1);
                            double distance = n.magnitude();

                            if (distance < circle1.getRadius() + circle2.getRadius())
                            {
                                // Compute normal and tangent vectors
                                n.normalize();
                                Vector2D t = new Vector2D(-n.getY(), n.getX());

                                // Separate circles - Compute new positions and assign to circles
                                double overlap = distance - (circle1.getRadius() + circle2.getRadius());
                                c1 = c1.add(n.mult(overlap/2));
                                c2 = c2.sub(n.mult(overlap/2));
                                objectList.get(i).setPosition(c1);
                                objectList.get(j).setPosition(c2);
                                
                                // Decompose velocities, project them on n and t
                                Vector2D v1 = objectList.get(i).getVelocity();
                                Vector2D v2 = objectList.get(j).getVelocity();

                                Vector2D v1N = n.mult(v1.dot(n));
                                Vector2D v2N = n.mult(v2.dot(n));

                                Vector2D v1T = t.mult(v1.dot(t));
                                Vector2D v2T = t.mult(v2.dot(t));

                                // Change velocities
                                v1.set(v1T.add(v2N));
                                v2.set(v2T.add(v1N));                                
                            }
                            
                        }
                    }
                }*/
            }
        }.start();      
    }   

   }    
    

