
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author Family Desktop
 */
public class AssetManager {
    
    static private Background backgroundImage = null;
    static private ArrayList<ImagePattern> aliens = new ArrayList<>();  
    static private ImagePattern projectileLaser;
    static private ImagePattern MainSpaceShip;
    static private ImagePattern shield;
    static private ImagePattern explosion_alien;
    static private ImagePattern explosion_proj_on_Shield;
    
    static private Media backgroundMusic = null;
    static private AudioClip ennemyHitSound = null;
    static private AudioClip shootingSound = null;
    
    static private String fileURL(String relativePath)
    {
        return new File(relativePath).toURI().toString();
    }
    
    static public void preloadAllAssets()
    {
        Image background = new Image(fileURL("./assets/images/background.gif"));
        
        backgroundImage = new Background(
                                    new BackgroundImage(background,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.DEFAULT,
                                    new BackgroundSize(1, 1, true, true, true, true)));
        
        
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/alien_1.png"))));
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/alien_2.png"))));
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/alien_3.png"))));
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/alien_4.png"))));
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/alien_5.png"))));
        
        projectileLaser = new ImagePattern(new Image(fileURL("./assets/images/Projectile.png")));
        MainSpaceShip = new ImagePattern(new Image(fileURL("./assets/images/spaceship.png")));
        
        shield = new ImagePattern(new Image(fileURL("./assets/images/shield.png")));
        
        explosion_alien = new ImagePattern(new Image(fileURL("./assets/images/explosion_clear.gif")));
        explosion_proj_on_Shield = new ImagePattern(new Image(fileURL("./assets/images/explosion_shield.gif")));
        
           
        // Preload all music tracks
        backgroundMusic = new Media(fileURL("./assets/music/Bumblebee.mp3"));
        
         // Preload all sound effects
        shootingSound = new AudioClip(fileURL("./assets/soundfx/Laser_Gun.mp3"));
        ennemyHitSound = new AudioClip(fileURL("./assets/soundfx/hit.mp3"));
        
    }
    static public ImagePattern getExplosionAlien(){
        
        return explosion_alien;
    }
    
    static public ImagePattern getExplosionOnShield()
    {
        return explosion_proj_on_Shield;
    }
    
    static public Background getBackgroundImage()
    {
        return backgroundImage;
    }
    
    static public ImagePattern getSpecificEnnemy(int levelOfEnnemy)
    {
        return aliens.get(levelOfEnnemy);
    }
    
    static public ImagePattern getProjectileImage()
    {
        return projectileLaser;
    }
    
    static public ImagePattern getSpaceShipImage()
    {
        return MainSpaceShip;
    }

    static public AudioClip getShootingSound()
    {
        return shootingSound;
    }
    
    static public AudioClip getEnnemyHitSound()
    {
        return ennemyHitSound;
    }
    
    static public AudioClip getHitSound()
    {
        return ennemyHitSound;
    }
    
    public static ImagePattern getShield(){
        return shield;       
    }
}
