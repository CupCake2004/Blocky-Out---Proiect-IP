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

    public static GamePiece createSquare2x2(int r, int c, Color color) {
        GamePiece p = new GamePiece(r, c, color);
        p.structure.add(new Point(0, 0));
        p.structure.add(new Point(0, 1));
        p.structure.add(new Point(1, 0));
        p.structure.add(new Point(1, 1));
        return p;
    }
}