/*******************************************************************************
*    file: Chunk.java
*    author: Amanda Garcia, John Abdou, Jaeseung Lee, John Vincent Canalita 
*    class: CS 445 – Computer Graphics
*
*    assignment: Final Checkpoint for Quarter Project
*    date last modified: 5/28/2018
*
*    purpose: This class creates a 30x30 chunk of blocks with varying heights 
*    from the noise generation. It also adds the textures to each block using
*    the terrain.png file.
*    
*******************************************************************************/ 

package cs445_quarter_project;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Chunk {
    
    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    private Block[][][] Blocks;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int StartX, StartY, StartZ;
    private Random r;
    private int VBOTextureHandle;
    private Texture texture;
    
    // method: render
    // purpose: used to draw the chunk correctly in opengl
    public void render(){
        glPushMatrix();
        glTranslatef(-10.0f, -50.0f, -30.0f);   //fixed translation to show correct lighting
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glColorPointer(3,GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBindTexture(GL_TEXTURE_2D, 1);
        glTexCoordPointer(2,GL_FLOAT,0,0L);
        glDrawArrays(GL_QUADS, 0, CHUNK_SIZE *CHUNK_SIZE*CHUNK_SIZE * 24);
        glPopMatrix();
    }
    
    // method: rebuildMesh
    // purpose: used to create varying heights of blocks in the chunk using SimplexNoise 
    public void rebuildMesh(float startX, float startY, float startZ) {
        float height;
        int seed = r.nextInt() * 50;
        SimplexNoise noise = new SimplexNoise(CHUNK_SIZE, 0.045f, seed);
        
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers(); 
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE *CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer((CHUNK_SIZE* CHUNK_SIZE *CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexTextureData = BufferUtils.createFloatBuffer((CHUNK_SIZE*CHUNK_SIZE *CHUNK_SIZE)* 6 * 12);
    
        for (float x = 0; x < CHUNK_SIZE; x += 1) {
            for (float z = 0; z < CHUNK_SIZE; z += 1) {
                
                int i = (int) (startX + x * ((320 - startX) / 640));
                int j = (int) (startZ + z * ((320 - startZ) / 480));        
                height = 20 + (Math.abs((startY + (int) (100 * noise.getNoise(i, j))* CUBE_LENGTH)));   //normalize to 0 to 30 from -1 to 1 of getNoise
                
                for(float y = 0; y <= height; y++){
                    VertexPositionData.put(createCube((float) (startX + x* CUBE_LENGTH),(float)(y*CUBE_LENGTH+(int)(CHUNK_SIZE*.8)),(float) (startZ + z *CUBE_LENGTH)));
                    VertexColorData.put(createCubeVertexCol(getCubeColor(Blocks[(int) x][(int) y][(int) z])));
            
                    Block currentBlock = Blocks[(int)(x)][(int) (y)][(int) (z)];
                    
                    if(y == height){     //grass, water, or sand
                        float rand = r.nextFloat();
                        if(rand > 0.7f){
                            currentBlock.SetID(0);
                            VertexTextureData.put(createTexCube((float) 0, (float)0, currentBlock));
                        }else if ((rand < 0.7) && (rand > 0.3)){
                            currentBlock.SetID(1);
                            VertexTextureData.put(createTexCube((float) 0, (float)0, currentBlock));
                        }else{
                            currentBlock.SetID(2);
                            VertexTextureData.put(createTexCube((float) 0, (float)0, currentBlock));
                        }
                    }else if(y == 0){        //bedrock at bottom
                        currentBlock.SetID(5);
                        VertexTextureData.put(createTexCube((float) 0, (float)0, currentBlock));
                    }else{                   //stone or dirt in the middle
                        float random = r.nextFloat();
                        if(random > 0.5){
                            currentBlock.SetID(3);
                            VertexTextureData.put(createTexCube((float) 0, (float)0, currentBlock));
                        }else{
                            currentBlock.SetID(4);
                            VertexTextureData.put(createTexCube((float) 0, (float)0, currentBlock));
                        }
                    }
                }
            }
        }
        VertexTextureData.flip();
        VertexColorData.flip();
        VertexPositionData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    // method: createCubeVertexCol
    // purpose: used to create the cube colors vertex 
    private float[] createCubeVertexCol(float[] CubeColorArray) {
        float[] cubeColors = new float[CubeColorArray.length * 4 * 6];
        for (int i = 0; i < cubeColors.length; i++) {
            cubeColors[i] = CubeColorArray[i %
            CubeColorArray.length];
        }
        return cubeColors;
    }
    
    // method: createCube
    // purpose: used to create a cube of length 2
    public static float[] createCube(float x, float y, float z) {
        int offset = CUBE_LENGTH / 2;
        return new float[] {
        // TOP QUAD
        x + offset, y + offset, z,
        x - offset, y + offset, z,
        x - offset, y + offset, z - CUBE_LENGTH,
        x + offset, y + offset, z - CUBE_LENGTH,
        // BOTTOM QUAD
        x + offset, y - offset, z - CUBE_LENGTH,
        x - offset, y - offset, z - CUBE_LENGTH,
        x - offset, y - offset, z,
        x + offset, y - offset, z,
        // FRONT QUAD
        x + offset, y + offset, z - CUBE_LENGTH,
        x - offset, y + offset, z - CUBE_LENGTH,
        x - offset, y - offset, z - CUBE_LENGTH,
        x + offset, y - offset, z - CUBE_LENGTH,
        // BACK QUAD
        x + offset, y - offset, z,
        x - offset, y - offset, z,
        x - offset, y + offset, z,
        x + offset, y + offset, z,
        // LEFT QUAD
        x - offset, y + offset, z - CUBE_LENGTH,
        x - offset, y + offset, z,
        x - offset, y - offset, z,
        x - offset, y - offset, z - CUBE_LENGTH,
        // RIGHT QUAD
        x + offset, y + offset, z,
        x + offset, y + offset, z - CUBE_LENGTH,
        x + offset, y - offset, z - CUBE_LENGTH,
        x + offset, y - offset, z };
    }
    
    // method: getCubeColor
    // purpose: makes the color painted on top of block white to show correct texture
    private float[] getCubeColor(Block block) {
        return new float[] { 1, 1, 1 };
    }
    
    // method: creatTexCube
    // purpose: used to texture all of the 6 different blocks
    public static float[] createTexCube(float x, float y, Block block) {
        float offset = (1024f/16)/1024f;
        switch (block.GetID()) {
            case 0:     //grass
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*3, y + offset*10,
                x + offset*2, y + offset*10,
                x + offset*2, y + offset*9,
                x + offset*3, y + offset*9,
                // TOP!
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // FRONT QUAD
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                x + offset*4, y + offset*1, 
                x + offset*3, y + offset*1,
                // BACK QUAD
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1,
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                // LEFT QUAD
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1,
                // RIGHT QUAD
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1};
            case 1:       //sand
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*15, y + offset*9,
                x + offset*14, y + offset*9,
                x + offset*14, y + offset*8,
                x + offset*15, y + offset*8,
                // TOP!
                x + offset*15, y + offset*9,
                x + offset*14, y + offset*9,
                x + offset*14, y + offset*8,
                x + offset*15, y + offset*8,
                // FRONT QUAD
                x + offset*14, y + offset*8,
                x + offset*15, y + offset*8,
                x + offset*15, y + offset*9, 
                x + offset*14, y + offset*9,
                // BACK QUAD
                x + offset*15, y + offset*9,
                x + offset*14, y + offset*9,
                x + offset*14, y + offset*8,
                x + offset*15, y + offset*8,
                // LEFT QUAD
                x + offset*14, y + offset*8,
                x + offset*15, y + offset*8,
                x + offset*15, y + offset*9,
                x + offset*14, y + offset*9,
                // RIGHT QUAD
                x + offset*14, y + offset*8,
                x + offset*15, y + offset*8,
                x + offset*15, y + offset*9,
                x + offset*14, y + offset*9};
            case 2:         //water
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*2, y + offset*14,
                x + offset*1, y + offset*14,
                x + offset*1, y + offset*13,
                x + offset*2, y + offset*13,
                // TOP!
                x + offset*2, y + offset*14,
                x + offset*1, y + offset*14,
                x + offset*1, y + offset*13,
                x + offset*2, y + offset*13,
                // FRONT QUAD
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1, 
                x + offset*2, y + offset*1,
                // BACK QUAD
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // LEFT QUAD
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                // RIGHT QUAD
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1};
            case 3:    //dirt
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*15, y + offset*8,
                x + offset*14, y + offset*8,
                x + offset*14, y + offset*7,
                x + offset*15, y + offset*7,
                // TOP!
                x + offset*15, y + offset*8,
                x + offset*14, y + offset*8,
                x + offset*14, y + offset*7,
                x + offset*15, y + offset*7,
                // FRONT QUAD
                x + offset*14, y + offset*7,
                x + offset*15, y + offset*7,
                x + offset*15, y + offset*8, 
                x + offset*14, y + offset*8,
                // BACK QUAD
                x + offset*15, y + offset*8,
                x + offset*14, y + offset*8,
                x + offset*14, y + offset*7,
                x + offset*15, y + offset*7,
                // LEFT QUAD
                x + offset*14, y + offset*7,
                x + offset*15, y + offset*7,
                x + offset*15, y + offset*8,
                x + offset*14, y + offset*8,
                // RIGHT QUAD
                x + offset*14, y + offset*7,
                x + offset*15, y + offset*7,
                x + offset*15, y + offset*8,
                x + offset*14, y + offset*8};
            case 4:   //stone
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2,
                x + offset*0, y + offset*1,
                x + offset*1, y + offset*1,
                // TOP!
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2,
                x + offset*0, y + offset*1,
                x + offset*1, y + offset*1,
                // FRONT QUAD
                x + offset*0, y + offset*1,
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*2, 
                x + offset*0, y + offset*2,
                // BACK QUAD
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2,
                x + offset*0, y + offset*1,
                x + offset*1, y + offset*1,
                // LEFT QUAD
                x + offset*0, y + offset*1,
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2,
                // RIGHT QUAD
                x + offset*0, y + offset*1,
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2};
            case 5:      //bedrock
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                // TOP!
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                // FRONT QUAD
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2, 
                x + offset*1, y + offset*2,
                // BACK QUAD
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                // LEFT QUAD
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                // RIGHT QUAD
                x + offset*1, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2};
            case 6:      //default
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*1, y + offset*5,
                x + offset*0, y + offset*5,
                x + offset*0, y + offset*4,
                x + offset*1, y + offset*4,
                // TOP!
                x + offset*1, y + offset*5,
                x + offset*0, y + offset*5,
                x + offset*0, y + offset*4,
                x + offset*1, y + offset*4,
                // FRONT QUAD
                x + offset*0, y + offset*4,
                x + offset*1, y + offset*4,
                x + offset*1, y + offset*5, 
                x + offset*0, y + offset*5,
                // BACK QUAD
                x + offset*1, y + offset*5,
                x + offset*0, y + offset*5,
                x + offset*0, y + offset*4,
                x + offset*1, y + offset*4,
                // LEFT QUAD
                x + offset*0, y + offset*4,
                x + offset*1, y + offset*4,
                x + offset*1, y + offset*5,
                x + offset*0, y + offset*5,
                // RIGHT QUAD
                x + offset*0, y + offset*4,
                x + offset*1, y + offset*4,
                x + offset*1, y + offset*5,
                x + offset*0, y + offset*5};
            default:
                System.out.println("Error");
                return null;
        }
    }
    
    // method: Chunk
    // purpose: the chunk constructor that loads in the terrain.png image and is
    // used to randomly select blocks to be placed in chunk
    public Chunk(int startX, int startY, int startZ) {
        try{texture = TextureLoader.getTexture("PNG",
            ResourceLoader.getResourceAsStream("terrain.png"));
        }catch(IOException e){
            System.out.print("ER-ROAR!");
        }
        r = new Random();
        Blocks = new
        Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    if(r.nextFloat()>0.8f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Grass);
                    }else if(r.nextFloat()>0.6f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Dirt);
                    }else if(r.nextFloat()>0.4f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Water);
                    }else if(r.nextFloat()>0.2f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Stone);
                    }else if(r.nextFloat()>0.1f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Bedrock);
                    }else if(r.nextFloat()>0.0f){
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Sand);
                    }else{
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Default);
                    }
                }
            }
        }
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        StartX = startX;
        StartY = startY;
        StartZ = startZ;
        rebuildMesh(startX, startY, startZ);
    }
    
        

}
