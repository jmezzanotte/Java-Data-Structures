

/**
 *
 * @author JohnMezzanotte
 */
public class EdgeNode {
    
    private int vertexNum; 
        
    public EdgeNode(int vertexNum){
        this.vertexNum = vertexNum;
    }
   
    public void setvnum(int vertexNum){
        this.vertexNum = vertexNum;
    }
  
    
    public int getvnum(){
        return vertexNum;
    }
        
    @Override
    public boolean equals(Object obj){
        if(obj instanceof EdgeNode){
            EdgeNode temp = (EdgeNode) obj;
            boolean sameVertex = this.vertexNum == temp.getvnum();
            return sameVertex;      
        }else{
            return false;
        }
    }
       
}
