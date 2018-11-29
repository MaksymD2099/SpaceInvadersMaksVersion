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
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Rectangle2D;
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
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Enemies[][] enemies = new Enemies[5][9];        
    private ArrayList<Shield> shield = new ArrayList<>();
    private SpaceShip_Main SpaceShip = null;
   
    //Projectile number in the list
    int projectile_count = -1;
    
    boolean collison = false;
    
    public void addToPane(Node node)
    {
        pane.getChildren().add(node);
    }
    
    
    @FXML
    public void mouseClickedShoot(MouseEvent event)
    {      
      
      projectile_count++;
      
      //Center the Projectile
      double prj_posX = SpaceShip.getPosition().getX() + 10;
      double prj_posY = SpaceShip.getPosition().getY() + 50;
      
      Vector2D init_position = new Vector2D(prj_posX,prj_posY);
      Vector2D velocity = new Vector2D(0,-600);
     //velocity.normalize();
     //velocity = velocity.mult(PROJECTILE_SPEED);
      
      AssetManager.getShootingSound().play();
      projectiles.add(new Projectile(init_position, velocity, 10, 30));      
      addToPane(projectiles.get(projectile_count).getRectangle());     
      objectList.add(projectiles.get(projectile_count));    
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
    shield.add(new Shield(new Vector2D( 70, 410), 120, 40));
    shield.add(new Shield(new Vector2D( 370, 410), 120, 40));
    shield.add(new Shield(new Vector2D( 670, 410), 120, 40));
    
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
    
 
                //Collison Shield/Projectile            
                for (int i = 0; i < projectiles.size(); i++) {
                    
                  
                    
                    Rectangle prj_rec = projectiles.get(i).getRectangle();
                    Bounds prj_rec_Bounds = prj_rec.getBoundsInParent();
                    
                    
                    Rectangle shield_rec1 = shield.get(0).getRectangle();
                    Bounds shield_rec_Bounds1 = shield_rec1.getBoundsInParent();
                    
                    Rectangle shield_rec2 = shield.get(1).getRectangle();
                    Bounds shield_rec_Bounds2 = shield_rec2.getBoundsInParent();
                    
                    Rectangle shield_rec3 = shield.get(2).getRectangle();
                    Bounds shield_rec_Bounds3 = shield_rec3.getBoundsInParent();
                        
                    if(prj_rec_Bounds.intersects(shield_rec_Bounds1) || prj_rec_Bounds.intersects(shield_rec_Bounds2) || prj_rec_Bounds.intersects(shield_rec_Bounds3))
                    {
                      System.out.println("COLLISION");                    
                      AssetManager.getEnnemyHitSound().play();
                      //prj_rec.setFill(AssetManager.getExplosionOnShield());
                      
                    }

                }      
                
                
                //Collision Projectile/Enemy
                for (int i = 0; i < projectiles.size(); i++) {
                    
                    Rectangle prj_rec = projectiles.get(i).getRectangle();
                    Bounds prj_rec_Bounds = prj_rec.getBoundsInParent();
                    
                    
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 8; k++) {
                            
                            Rectangle enemy_rec = enemies[j][k].getRectangle();
                            Bounds enemy_rec_Bounds = enemy_rec.getBoundsInParent();
                            
                            if(prj_rec_Bounds.intersects(enemy_rec_Bounds))
                            {
                                System.out.println("COLLISION!!!!!!!!");
                                prj_rec.setVisible(false);
                                enemy_rec.setVisible(false);
                            }
                        }
                    }
                }
            }
       }.start();      
      }
    }
       
    

