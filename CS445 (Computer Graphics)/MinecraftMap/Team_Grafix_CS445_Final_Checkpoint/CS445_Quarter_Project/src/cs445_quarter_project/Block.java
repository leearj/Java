/*******************************************************************************
*    file: Block.java
*    author: Amanda Garcia, John Abdou, Jaeseung Lee, John Vincent Canalita 
*    class: CS 445 – Computer Graphics
*
*    assignment: Final Checkpoint for Quarter Project
*    date last modified: 5/25/2018
*
*    purpose: This class creates a block to be used in the chunk class and
*    contains the details necessary for the block like location and if
*    it is active or not. It classifies the 6 different types of block textures 
*    needed.
*    
*******************************************************************************/ 

package cs445_quarter_project;


public class Block {
    
    private boolean IsActive;
    private final BlockType Type;
    private float x,y,z;
    
    // method: BlockType
    // purpose: This class classifies the 6 different block types 
    public enum BlockType{
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5),
        BlockType_Default(6);
        private int BlockID; 

        BlockType(int i) {
            BlockID=i;
        }
    
        // method: GetID
        // purpose: used to get the block ID
        public int GetID(){
            return BlockID;
        }
        
        // method: SetID
        // purpose: used to set the block ID
        public void SetID(int i){
            BlockID = i;
        }
    }
    
    // method: Block
    // purpose: this is the Block constructor used to initialize the type of block
    public Block(BlockType type){
        Type= type;
    }
    
    // method: setCoords
    // purpose: used to set the coordinates of the position of the block
    public void setCoords(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    // method: IsActive
    // purpose: used to check if the block is active or not
    public boolean IsActive() {
        return IsActive;
    }
    
    // method: SetActive
    // purpose: used to set the block to active
    public void SetActive(boolean active){
        IsActive=active;
    }
    
    // method: GetID
    // purpose: used to get the block type ID
    public int GetID(){
        return Type.GetID();
    }
    
    // method: SetID
    // purpose: used to set the block type ID
    // this method is used to correctly organize the different textures in the chunk class
    public void SetID(int i){
        this.Type.SetID(i);
    }

}
