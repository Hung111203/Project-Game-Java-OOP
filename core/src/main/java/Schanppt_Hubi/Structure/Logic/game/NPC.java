package Schanppt_Hubi.Structure.Logic.game;
import java.awt.Color;
import java.util.Random;
public class NPC extends Animal{
    private String answer;
    private String level;
    private Random random;
    private String answerType;

    public NPC(Color color, String type){
        super(color, type);
    }
}
