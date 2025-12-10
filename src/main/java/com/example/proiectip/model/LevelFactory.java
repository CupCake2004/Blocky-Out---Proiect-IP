package com.example.proiectip.model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class LevelFactory {

    public static char[][] getLevelMap(int level) {
        if (level == 1) {
            // Matrice 8x8 (Zona albă din mijloc este 6x6)
            // L = Lama Albastră, R = Lama Galbenă, W = Perete Gri
            return new char[][]{
                    // Rând 0: Perete sus
                    {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'},

                    // Rând 1: LAMA Albastră (1/4) ................. Perete (W)
                    {'L', '_', '_', '_', '_', '_', '_', 'W'},

                    // Rând 2: LAMA Albastră (2/4) ................. Perete (W)
                    {'L', '_', '_', '_', '_', '_', '_', 'W'},

                    // Rând 3: LAMA Albastră (3/4) ................. LAMA Galbenă (1/4)
                    {'L', '_', '_', '_', '_', '_', '_', 'R'},

                    // Rând 4: LAMA Albastră (4/4) ................. LAMA Galbenă (2/4)
                    {'L', '_', '_', '_', '_', '_', '_', 'R'},

                    // Rând 5: Perete (W) .......................... LAMA Galbenă (3/4)
                    {'W', '_', '_', '_', '_', '_', '_', 'R'},

                    // Rând 6: Perete (W) .......................... LAMA Galbenă (4/4)
                    {'W', '_', '_', '_', '_', '_', '_', 'R'},

                    // Rând 7: Perete jos
                    {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'}
            };
        }
        return new char[0][0];
    }

    public static List<GamePiece> getLevelPieces(int level) {
        List<GamePiece> pieces = new ArrayList<>();
        if (level == 1) {
            // Piesa ALBASTRĂ -> Sus-Dreapta (Coloana 5 din zona albă)
            pieces.add(GamePiece.createSquare2x2(1, 5, Color.DODGERBLUE));

            // Piesa GALBENĂ -> Jos-Stânga (Coloana 1, Rândul 5)
            pieces.add(GamePiece.createSquare2x2(5, 1, Color.GOLD));
        }
        return pieces;
    }
}