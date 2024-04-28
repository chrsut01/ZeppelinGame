/*
import com.mygdx.game.DilemmaStuff.Dilemma;

import java.io.*;

public class DilemmaSerializationExample {

    public static void main(String[] args) {
        Dilemma dilemma = new Dilemma();
        // Set dilemma properties...

        // Serialize dilemma to a file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sample2.json"))) {
            oos.writeObject(dilemma);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize dilemma from the file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("sample2.json"))) {
            Dilemma deserializedDilemma = (Dilemma) ois.readObject();
            // Use deserializedDilemma...
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
*/