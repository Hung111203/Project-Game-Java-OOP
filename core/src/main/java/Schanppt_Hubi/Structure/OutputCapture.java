package Schanppt_Hubi.Structure;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class OutputCapture {

    private ByteArrayOutputStream baos;
    private PrintStream originalOut;
    public OutputCapture() {
        baos = new ByteArrayOutputStream();
        originalOut = System.out;
    }

    public void startCapture() {
        System.setOut(new PrintStream(baos));
    }

    public void stopCapture() {
        System.setOut(originalOut);
    }

    public String getFirstLine() {
        String[] lines = baos.toString().split(System.lineSeparator());
        return (lines.length > 0) ? lines[0] : "No output captured.";
    }
    public boolean isEmpty() {
        return baos.size() == 0;
    }
    public void clear() {
        baos.reset();
    }
    public int getSize() {
        return baos.toString().split(System.lineSeparator()).length;
    }
    public String getLine(int i) {
        String[] lines = baos.toString().split(System.lineSeparator());
        if (i >= 0 && i < lines.length) {
            return lines[i];
        } else {
            return "Index out of bounds.";
        }
    }
}