/*******************************************************************************
*    file: FPCameraController.java
*    author: Amanda Garcia, John Abdou, Jaeseung Lee, John Vincent Canalita  
*    class: CS 445 – Computer Graphics
*
*    assignment: Final Checkpoint for Quarter Project
*    date last modified: 5/28/2018
*
*    purpose: This class sets up the first person camera controller to allow 
*    manipulation of the camera using the mouse and navigation using the 
*    w,a,s,d keys to move around, the space bar to move up, and the left
*    shift to move down. Also, the only way to close the window is by
*    clicking the Escape key.
*    
*******************************************************************************/ 

package cs445_quarter_project;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class FPCameraController {
    
    private Vector3f position = null;
    private Vector3f lPosition = null;

    private float yaw = 0.0f;  //the rotation around the Y axis of the camera
    private float pitch = 0.0f;  //the rotation around the X axis of the camera
    private final Chunk chunk = new Chunk(0,0,0);
    
    // method: FPCameraController
    // purpose: constructor to instatntiate the position Vector3f using the parameters
    public FPCameraController(float x, float y, float z){
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x,y,z);
        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
    }
    
    // method: yaw
    // purpose: used to increment the camera's current yaw rotation
    public void yaw(float amount){
        yaw += amount;
    }
    
    // method: pitch
    // purpose: increment the camera's current pitch rotation
    public void pitch(float amount){
        pitch -= amount;
    }
    
    // method: walkForward
    // purpose: moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance){
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
      
        position.x -= xOffset;
        position.z += zOffset;
        
    }
    
    // method: walkBackwards
    // purpose: moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance){
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
  
        position.x += xOffset;
        position.z -= zOffset;
    }
    
    // method: strafeLeft
    // purpose: strafes the camera left relative to its current rotation (yaw)
    public void strafeLeft(float distance){
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));

        position.x -= xOffset;
        position.z += zOffset;
    }
    
    // method: strafeRight
    // purpose: strafes the camera right relative to its current rotation (yaw)
    public void strafeRight(float distance){
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));

        position.x -= xOffset;
        position.z += zOffset;
    }
    
    // method: moveUp
    // purpose: moves the camera up 
    public void moveUp(float distance){
        position.y -= distance;
    }
    
    // method: moveDown
    // purpose: moves the camera down 
    public void moveDown(float distance){
        position.y += distance;
    }
    
    // method: lookThrough
    // purpose: translates and rotates the matrix so that it looks through the camera
    public void lookThrough(){
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);   //roatate the pitch around the X axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);     //roatate the yaw around the Y axis
        glTranslatef(position.x, position.y, position.z);   //translate to the position vector's location
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x).put(lPosition.y).put(lPosition.z).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    // method: gameLoop
    // purpose: this method is what controls all the movements the user can perform when program is executed
    // This includes how the camera moves with the mouse and the directional movements 
    public void gameLoop(){
        FPCameraController camera = new FPCameraController(-20.0f, -40.0f, -50.0f);    //adjust the camera to show lighting
        float dx;
        float dy;
    
        float mouseSensitivity = 0.09f;
        float movementSpeed = .35f;
        
        Mouse.setGrabbed(true);     //hide the mouse cursor
        
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            dx = Mouse.getDX();   //distance in mouse movement 
            dy = Mouse.getDY();   //distance in mouse movement
            
            camera.yaw(dx * mouseSensitivity);   //control camera yaw from x movement fromt the mouse
            camera.pitch(dy * mouseSensitivity);   //control camera pitch from y movement fromt the mouse
            
            if (Keyboard.isKeyDown(Keyboard.KEY_W)){  //move forward
                camera.walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)){  //move backwards
                camera.walkBackwards(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)){  //strafe left 
                camera.strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)){  //strafe right 
                camera.strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){  //move up 
                camera.moveUp(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {  //move down
                camera.moveDown(movementSpeed);
            }
            
            glLoadIdentity();
   
            camera.lookThrough();   //look through the camera before you draw anything
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
           
            chunk.render();
          
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
    
}
