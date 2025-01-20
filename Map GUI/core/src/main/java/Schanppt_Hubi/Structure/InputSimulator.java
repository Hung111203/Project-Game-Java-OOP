package Schanppt_Hubi.Structure;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InputSimulator {
    private InputStream originalIn;
    private ByteArrayInputStream simulatedInputStream;

    public InputSimulator() {
        this.originalIn = System.in;
    }

    public void setInput(String input) {
        this.simulatedInputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(this.simulatedInputStream);
    }

    public void resetInput() {
        System.setIn(this.originalIn);
    }


}
