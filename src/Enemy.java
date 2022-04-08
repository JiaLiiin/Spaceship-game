
import java.util.Random;
public class Enemy
{

  int EnemyCoord;
  Random r=new Random();
  int[] list = new int[15];
  
  public int z=0;


  
  public void setEnemyCoord()
  {
    for (int x=0; x<15; x++)
    {
      EnemyCoord=r.nextInt(380);
      list[x]=EnemyCoord;
      
    }
 
  }
  
  

  
  public int[] getList() {
    return list.clone();
}

 
  
  
    
  
  
  
  }
  



