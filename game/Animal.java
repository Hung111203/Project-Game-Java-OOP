package game;
import java.awt.Color;
import java.lang.reflect.AccessFlag.Location;

public class Animal{
    private final String type;
	private final Color color;
    
    public Animal(Color color, String type){
        this.color = color;
        this.type = type;
    }
    
    public Color getColor(){
        return color;
    }

    public String getType(){
        return type;
    }

    public void Print(){
        System.out.println("Hello World");
    }

}


