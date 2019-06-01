/*******************************************************************************
*    file: CS445_Quarter_Project.java
*    author: Amanda Garcia, John Abdou, Jaeseung Lee, John Vincent Canalita 
*    class: CS 445 – Computer Graphics
*
*    assignment: Final Checkpoint for Quarter Project
*    date last modified: 5/28/2018
*
*    purpose: This class launches the program through the main method. 
*    The program draws out a cube in 3D space with each side a different 
*    color on a 640 x 480 black window centered in the screen with the ability to 
*    manipulate the camera by moving the mouse and the ability to navigate the 
*    environment with the use of the w,a,s,d keys to move around, the space bar 
*    to move up, and the left shift to move down. 
*    
*******************************************************************************/ 

package cs445_quarter_project;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class CS445_Quarter_Project {

    private FPCameraController fp;
    private DisplayMode displayMode;
    
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;

   // method: start
   // purpose: this method is used to call the methods that create the window, initialize openGL, and render 
   public void start() {
        try {
            createWindow();
            initGL();
            fp = new FPCameraController(0f, 0f, 0f);
            fp.gameLoop();   //render();
        } catch (Exception e) {
        }
    }
    
    // method: createWindow
    // purpose: used to create a 640 x 480 window  
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (DisplayMode d1 : d) {
            if (d1.getWidth() == 640 && d1.getHeight() == 480 && d1.getBitsPerPixel() == 32) {
                displayMode = d1;
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("Team Grafix: Final Project");
        Display.create();
    }
    
    // method: initGL
    // purpose: used to initialize the necessary openGL tools
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);   //need this so that you do not see inside the cube the whole time
        glEnable(GL_TEXTURE_2D);
        glEnableClientState (GL_TEXTURE_COORD_ARRAY);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float)displayMode.getWidth()/(float)displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        
        initLightArrays();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition); //sets our light’s position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);//sets our specular light
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);//sets our diffuse light
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);//sets our ambient light
        glEnable(GL_LIGHTING);//enables our lighting
        glEnable(GL_LIGHT0);//enables light0
    }
    
    // method: initLightArrays
    // purpose: this method initializes the light feature in terms of position and color
    private void initLightArrays() {
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
    }
    
    // method: main
    // purpose: main method launches the program
    public static void main(String[] args) {
        CS445_Quarter_Project draw = new CS445_Quarter_Project();
        draw.start();
    }
    
    
}
