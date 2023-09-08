import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Thread;

public class Aquarium 
{
    public static  boolean isStopped = false;
    public static  int check  = 0;
    static int size= 0;
    static int number_of_fishes = 1000;
    static int number_of_moves = 0;

    static LinkedList<Location> location_list = new LinkedList<Location>();
   
    public static class Location 
    {

    public int x;

    public int y;


    @Override
    public int hashCode() 
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
            Location other = (Location) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
    public Location(int Size)
    {
        setRandomLocation(Size);
        synchronized (Aquarium.location_list) 
        {
            Aquarium.location_list.add(this);
        }
    }
    public Location() 
    {

    }
    public void setRandomLocation(int Size)
    {
       
        int location_exist_ind=0 ;
        
        while(location_exist_ind==0)
        {               

            this.x = (int)(Math.random() * Size);               

            this.y = (int)(Math.random() * Size);       

            location_exist_ind=checkLocation(this); 
        } 
        
    }
    
    
    public  int checkLocation(Location obj)
    { 
        int checkH;
        int temp_ind=0;
       
        synchronized (Aquarium.location_list) 
        {

            if(Aquarium.location_list.size()!=0)
            for(Location loc1 : Aquarium.location_list )
            {
                if(obj.equals(loc1))
                {
                    
                    
                    if (isStopped==false )
                    {   
                    System.out.println(" Щука встретила карпа в точке " + obj.x  + " , "+obj.y+ " карп был съеден. ");
                    location_list.remove(obj);  
                    }
                    
                      
                    isStopped = true;

                    temp_ind=1;
                    
                    try 
                    {
                        
                        Thread.sleep(10);
                        
                    } catch (InterruptedException e) 
                    {
                        
                        e.printStackTrace();
                    }
                    break;          
                }  
                
                else
                
             temp_ind=1;  
                
            }
            else temp_ind=1;
        }
        return temp_ind;
    }

    public void setNextLocation(int x,int y)
    {

        int X_location = 0;
        int Y_location = 0;
        
        int location_exist_ind=0 ;

        while(location_exist_ind==0)
        {               
           
            X_location= Direction_X.getRandom_direction_X(x);
            
            Y_location= Direction_Y.getRandom_direction_Y(y);
             
            Location temp_loc= new Location();

            temp_loc.setX(X_location);      

            temp_loc.setY(Y_location);

            location_exist_ind=checkLocation(temp_loc);

        }
        this.setX(X_location);
        this.setY(Y_location);

    }
    public void setX(int x) 
    {
        this.x = x;
    }
    public void setY(int y) 
    {
        this.y = y;
    }
}
  public static class Fish extends Location implements Runnable
  {

    int moves=0;
    int fishnum=0;
    public Fish(int AquariumSize, int moves , int fishnum) 
    {
        super(AquariumSize);
        
        this.moves=moves;
        this.fishnum=fishnum;
    }
    @Override
    public void run() 
    {
      
       
        for(int i=0;i< moves;i++)
        {
            if(i==0)
            System.out.println(" Щука  " + fishnum + " начинает с точки "+this.x + " , "+ this.y);
            
            this.setNextLocation(x, y);
            
            System.out.println(" Щука " + fishnum + " перешла в точку "+this.x + " , " + this.y);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }  
        } 
        
        
        
    
    }
    
}
  
  
  public static class Carp extends Location implements Runnable
  {

    int moves=0;
    int fishnum=0;
    public Carp(int AquariumSize, int moves , int fishnum) 
    {
        super(AquariumSize);
        this.moves=moves;
        this.fishnum=fishnum;
    }
    @Override
    public void run() {
        for(int i=0;i< moves;i++){
            if(i==0)
            System.out.println(" Карп " + fishnum + " начинает с точки "+this.x + " , "+ this.y);
            
            if(isStopped==false )
            {
            this.setNextLocation(x, y);
            System.out.println(" Карп " + fishnum + " перешёл в точку "+this.x + " , " + this.y);
            }
             
                
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
        }
    }
}
  
  
  
  
  public enum Direction_X 
  {
    
         
    RIGHT(1),
    LEFT(-1);

    public int direction_X_ind;

    Direction_X(int X)
    {
        direction_X_ind = X;
    }

    public static int getRandom_direction_X(int x)
    {
        int X=x;

        if(X!=0 && X!=Aquarium.size-1)
        {
            X = X + values()[(int) (Math.random() * (values().length))].direction_X_ind;

        }
        else if(x>=Aquarium.size-1)
        {
            X = X + values()[ (int) (Math.random() * (values().length-1)) + 1 ].direction_X_ind;
        }
        else
        {
            X = X + values()[(int) (Math.random() * (values().length-1))].direction_X_ind;
        }
        return (X);
    }
}

public enum Direction_Y 
{

    UP(1),
    DOWN(-1);

    public int direction_Y_ind;

    Direction_Y(int Y)
    {
        direction_Y_ind = Y;
    }

    public static int getRandom_direction_Y(int Y)
    {
        if(Y!=0 && Y != Aquarium.size-1)
        {
            Y = Y + values()[ (int) (Math.random() )].direction_Y_ind;       
        }
        else if(Y >= Aquarium.size-1)
        {
            Y = Y + values()[ (int) (Math.random()+ 1 )].direction_Y_ind;
        }
        else
        {
            Y = Y + values()[ (int) (Math.random() -1)].direction_Y_ind;
        }
        return (Y);
    }
}
  
  
  
    public static void main(String[] args) 
    {
        Scanner scn= new Scanner(System.in);

        System.out.print("Введите размер аквариума: ");

        size=scn.nextInt();
        
        int number_of_moves1;
        int number_of_fishes1;
        while(number_of_fishes >= Math.pow(size,2))
        
            number_of_fishes=1; 
        

        System.out.print("Введите количество перемещений щуки :");
        number_of_moves=scn.nextInt();
        System.out.print("Введите количество перемещений карпа:");
        number_of_moves1=scn.nextInt();
        
        Fish[] fishes = new Fish[number_of_fishes];
        Carp[] carps = new Carp[number_of_fishes];

        Thread[] thr = new Thread[number_of_fishes];
        
        Thread[] thr1 = new Thread[number_of_fishes];
        
        
        
        
         for (int i=0;i<number_of_fishes;i++)
        {

            carps[i]= new Carp(size, number_of_moves1 ,i);
            thr1[i]= new Thread(carps[i]);
            
        }

       
        
        for (int i=0;i<number_of_fishes;i++)
        {
             
            fishes[i]= new Fish(size, number_of_moves ,i);
            thr[i]= new Thread(fishes[i]);
            
            
        }
        
            
        for (int i=0;i<number_of_fishes;i++)
        {
           
           thr[i].start();
           
           
           
           thr1[i].start();
           
            
        }
        
        try 
        {
            for (int i=0;i<number_of_fishes;i++)
            thr1[i].join();
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
        try 
        {
            for (int i=0;i<number_of_fishes;i++)
            thr[i].join();
            
        } 
        catch (InterruptedException e) 
        {
            
            e.printStackTrace();
        }
        

        System.out.println("Местоположение рыб после перемещения : ");

        for(Location loc : location_list)
        {
            System.out.println(loc.x + " , " + loc.y);
        }
    
     }

}
