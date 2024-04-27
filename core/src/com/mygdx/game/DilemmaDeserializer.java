package com.mygdx.game;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class DilemmaDeserializer {
    public static void main(String[] args) {
        List<Dilemma> dilemmas;

        try (FileInputStream fileIn = new FileInputStream("dilemmas.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            dilemmas = (List<Dilemma>) in.readObject();
            // Use dilemmas...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
