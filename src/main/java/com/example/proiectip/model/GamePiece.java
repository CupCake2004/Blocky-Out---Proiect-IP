package com.example.proiectip.model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class GamePiece {
    public int row;
    public int col;
    public Color color;
    public boolean isDestroyed = false;

    public static class Point {
        public int r, c;
        public Point(int r, int c) { this.r = r; this.c = c; }
    }

    public List<Point> structure = new ArrayList<>();

    public GamePiece(int startRow, int startCol, Color color) {
        this.row = startRow;
        this.col = startCol;
        this.color = color;
    }

    // --- FORME STANDARD ---
    public static GamePiece createSquare2x2(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0)); p.structure.add(new Point(0, 1));
        p.structure.add(new Point(1, 0)); p.structure.add(new Point(1, 1));
        return p;
    }

    public static GamePiece createVertical3x1(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0));
        p.structure.add(new Point(1, 0));
        p.structure.add(new Point(2, 0));
        return p;
    }

    // --- FORME NIVEL 3 (L-uri) ---
    public static GamePiece createLargeLTopLeft(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0)); p.structure.add(new Point(0, 1)); p.structure.add(new Point(0, 2));
        p.structure.add(new Point(1, 0)); p.structure.add(new Point(2, 0));
        return p;
    }
    public static GamePiece createLargeLTopRight(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0)); p.structure.add(new Point(0, 1)); p.structure.add(new Point(0, 2));
        p.structure.add(new Point(1, 2)); p.structure.add(new Point(2, 2));
        return p;
    }
    public static GamePiece createLargeLBottomLeft(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0)); p.structure.add(new Point(1, 0));
        p.structure.add(new Point(2, 0)); p.structure.add(new Point(2, 1)); p.structure.add(new Point(2, 2));
        return p;
    }
    public static GamePiece createLargeLBottomRight(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 2)); p.structure.add(new Point(1, 2));
        p.structure.add(new Point(2, 0)); p.structure.add(new Point(2, 1)); p.structure.add(new Point(2, 2));
        return p;
    }
    // L-uri Mici
    public static GamePiece createSmallLTopLeft(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0)); p.structure.add(new Point(0, 1)); p.structure.add(new Point(1, 0));
        return p;
    }
    public static GamePiece createSmallLTopRight(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0)); p.structure.add(new Point(0, 1)); p.structure.add(new Point(1, 1));
        return p;
    }
    public static GamePiece createSmallLBottomLeft(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0));
        p.structure.add(new Point(1, 0)); p.structure.add(new Point(1, 1));
        return p;
    }
    public static GamePiece createSmallLBottomRight(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 1));
        p.structure.add(new Point(1, 0)); p.structure.add(new Point(1, 1));
        return p;
    }

    // --- FORME NIVEL 4 ---
    public static GamePiece createPlusShape(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 1));
        p.structure.add(new Point(1, 0)); p.structure.add(new Point(1, 1)); p.structure.add(new Point(1, 2));
        p.structure.add(new Point(2, 1));
        return p;
    }

    public static GamePiece createHorizontal2x1(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0));
        p.structure.add(new Point(0, 1));
        return p;
    }

    // --- FORME NOI NIVEL 5 ---

    // Vertical Mic (1 latime, 2 inaltime)
    public static GamePiece createVertical1x2(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0));
        p.structure.add(new Point(1, 0));
        return p;
    }

    // Orizontal Lung (4 latime, 1 inaltime)
    public static GamePiece createHorizontal4x1(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0)); p.structure.add(new Point(0, 1));
        p.structure.add(new Point(0, 2)); p.structure.add(new Point(0, 3));
        return p;
    }
}