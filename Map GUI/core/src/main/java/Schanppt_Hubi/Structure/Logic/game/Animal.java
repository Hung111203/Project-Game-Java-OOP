package Schanppt_Hubi.Structure.Logic.game;
import java.awt.Color;

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


}


