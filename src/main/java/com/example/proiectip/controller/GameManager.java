package com.example.proiectip.controller;

public class GameManager {
    // 1. Instanța statică unică (Singleton) - Aici e cheia șablonului
    private static GameManager instance;

    // Variabile de stare globală (Progresul jucătorului)
    private int maxUnlockedLevel = 1;

    // 2. Constructor privat (ca să nu poată fi instanțiat cu 'new' din alte părți)
    private GameManager() {}

    // 3. Metoda publică de acces global
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // --- Logica de Progres ---

    // Returnează cel mai mare nivel la care a ajuns jucătorul
    public int getMaxUnlockedLevel() {
        return maxUnlockedLevel;
    }

    // Deblochează un nivel nou (dacă l-am terminat pe cel anterior)
    public void unlockLevel(int level) {
        if (level > maxUnlockedLevel) {
            maxUnlockedLevel = level;
        }
    }
}