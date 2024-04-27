import com.mygdx.game.Dilemma;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DilemmaSerializer {
    public static void main(String[] args) {
        List<Dilemma> dilemmas = new ArrayList<>();
        // Populate dilemmas...

        try (FileOutputStream fileOut = new FileOutputStream("dilemmas.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(dilemmas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
