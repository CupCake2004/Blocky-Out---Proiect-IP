package com.example.proiectip.controller;

public class GameManager {
    // 1. Instanța unică (statică)
    private static GameManager instance;

    // Datele jocului
    private int currentLevel = 1;
    private int score = 0;

    // 2. Constructor privat (nimeni nu poate face "new GameManager")
    private GameManager() {}

    // 3. Metoda publică de acces
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
        System.out.println("Nivel selectat: " + level);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}