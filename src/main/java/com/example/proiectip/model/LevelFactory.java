package com.example.proiectip.model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class LevelFactory {

    public static char[][] getLevelMap(int level) {
        if (level == 1) {
            // NIVEL 1 (6x6)
            return new char[][]{
                    {'W', 'W', 'W', 'W', 'W', 'W'},
                    {'L', '_', '_', '_', '_', 'R'},
                    {'L', '_', '_', '_', '_', 'R'},
                    {'L', '_', '_', '_', '_', 'R'},
                    {'L', '_', '_', '_', '_', 'R'},
                    {'W', 'W', 'W', 'W', 'W', 'W'}
            };
        }
        else if (level == 2) {
            // NIVEL 2 (7x7)
            return new char[][]{
                    {'W', 'W', 'P', 'P', 'P', 'W', 'W'},
                    {'W', '_', '_', '_', '_', '_', 'W'},
                    {'W', '_', '_', '_', '_', '_', 'W'},
                    {'W', '_', '_', '_', '_', '_', 'W'},
                    {'W', '_', '_', '_', '_', '_', 'W'},
                    {'W', '_', '_', '_', '_', '_', 'W'},
                    {'W', 'L', 'L', 'W', 'R', 'R', 'W'}
            };
        }
        else if (level == 3) {
            // NIVEL 3 (8x8)
            return new char[][]{
                    {'W', 'W', 'W', 'L', 'L', 'W', 'W', 'W'},
                    {'W', '_', '_', '_', '_', '_', '_', 'W'},
                    {'W', '_', '_', '_', '_', '_', '_', 'W'},
                    {'P', '_', '_', '_', '_', '_', '_', 'R'},
                    {'P', '_', '_', '_', '_', '_', '_', 'R'},
                    {'W', '_', '_', '_', '_', '_', '_', 'W'},
                    {'W', '_', '_', '_', '_', '_', '_', 'W'},
                    {'W', 'W', 'W', 'G', 'G', 'W', 'W', 'W'}
            };
        }
        else if (level == 4) {
            // NIVEL 4 (9x9)
            return new char[][]{
                    {'W', 'W', 'O', 'O', 'O', 'O', 'O', 'W', 'W'},
                    {'W', 'R', '_', '_', '_', '_', '_', 'L', 'W'},
                    {'W', 'R', '_', '_', '_', '_', '_', 'L', 'W'},
                    {'W', 'W', '_', '_', '_', '_', '_', 'W', 'W'},
                    {'W', 'W', '_', '_', '_', '_', '_', 'W', 'W'},
                    {'W', 'W', '_', '_', '_', '_', '_', 'W', 'W'},
                    {'W', 'P', '_', '_', '_', '_', '_', 'G', 'W'},
                    {'W', 'P', '_', '_', '_', '_', '_', 'G', 'W'},
                    {'W', 'W', 'O', 'O', 'O', 'O', 'O', 'W', 'W'}
            };
        }
        else if (level == 5) {
            // NIVEL 5 - FINAL (8x8)
            // C = Cyan/Blue (Sus), G = Verde, P = Roz, R = Galben, L = Albastru, U = Mov, O = Portocaliu

            return new char[][]{
                    // R0: Sus - 3 Pereți, 2 Blue/Cyan (C), 3 Pereți (Colturi de 3)
                    {'W', 'W', 'W', 'C', 'C', 'W', 'W', 'W'},

                    // R1: Zona de joc
                    {'W', '_', '_', '_', '_', '_', '_', 'W'},

                    // R2: Stânga Galben (R - 2 buc), Dreapta Mov (U - 2 buc)
                    {'R', '_', '_', '_', '_', '_', '_', 'U'},
                    {'R', '_', '_', '_', '_', '_', '_', 'U'},

                    // R4: Stânga Albastru (L - 1 buc), Dreapta Portocaliu (O - 1 buc)
                    {'L', '_', '_', '_', '_', '_', '_', 'O'},

                    // R5-R6: Zona de joc
                    {'W', '_', '_', '_', '_', '_', '_', 'W'},
                    {'W', '_', '_', '_', '_', '_', '_', 'W'},

                    // R7: Jos - 2 Pereți, 2 Verde, 2 Roz, 2 Pereți (Colturi de 2)
                    {'W', 'W', 'G', 'G', 'P', 'P', 'W', 'W'}
            };
        }
        return new char[0][0];
    }

    public static List<GamePiece> getLevelPieces(int level) {
        List<GamePiece> pieces = new ArrayList<>();

        if (level == 1) {
            pieces.add(GamePiece.createSquare2x2(1, 1, Color.DODGERBLUE));
            pieces.add(GamePiece.createSquare2x2(3, 3, Color.GOLD));
        }
        else if (level == 2) {
            pieces.add(GamePiece.createVertical3x1(2, 2, Color.DODGERBLUE));
            pieces.add(GamePiece.createVertical3x1(2, 3, Color.MAGENTA));
            pieces.add(GamePiece.createVertical3x1(2, 4, Color.GOLD));
        }
        else if (level == 3) {
            pieces.add(GamePiece.createSmallLTopLeft(1, 1, Color.DODGERBLUE));
            pieces.add(GamePiece.createSmallLTopRight(1, 5, Color.GOLD));
            pieces.add(GamePiece.createSmallLBottomLeft(5, 1, Color.MAGENTA));
            pieces.add(GamePiece.createSmallLBottomRight(5, 5, Color.LIMEGREEN));
            pieces.add(GamePiece.createSquare2x2(3, 3, Color.LIMEGREEN));
        }
        else if (level == 4) {
            pieces.add(GamePiece.createPlusShape(3, 3, Color.ORANGE));
            pieces.add(GamePiece.createHorizontal2x1(2, 2, Color.DODGERBLUE));
            pieces.add(GamePiece.createSquare2x2(2, 5, Color.GOLD));
            pieces.add(GamePiece.createSquare2x2(5, 2, Color.MAGENTA));
            pieces.add(GamePiece.createHorizontal2x1(6, 5, Color.LIMEGREEN));
        }
        else if (level == 5) {
            // NIVEL 5 - PIESELE (Conform imaginii)
            // Playable Grid: Cols 1-6, Rows 1-6

            // 1. Pătrat Roz/Roșu (Sus-Stânga)
            pieces.add(GamePiece.createSquare2x2(1, 1, Color.MAGENTA));

            // 2. Pătrat Verde (Sus-Dreapta)
            pieces.add(GamePiece.createSquare2x2(1, 5, Color.LIMEGREEN));

            // 3. Vertical Mic Verde (Mijloc-Sus Stânga, între pătrate)
            pieces.add(GamePiece.createVertical1x2(1, 3, Color.LIMEGREEN));

            // 4. Vertical Mic Roz (Mijloc-Sus Dreapta, între pătrate)
            pieces.add(GamePiece.createVertical1x2(1, 4, Color.MAGENTA));

            // 5. Bara Mov Lungă (Mijloc, sub cele de sus)
            pieces.add(GamePiece.createHorizontal4x1(3, 2, Color.PURPLE));

            // 6. Orizontal Portocaliu (Jos-Stânga Sus)
            pieces.add(GamePiece.createHorizontal2x1(5, 1, Color.ORANGE));

            // 7. Orizontal Cyan (Jos-Stânga Jos)
            pieces.add(GamePiece.createHorizontal2x1(6, 1, Color.CYAN));

            // 8. Orizontal Albastru (Jos-Dreapta Sus)
            pieces.add(GamePiece.createHorizontal2x1(5, 5, Color.DODGERBLUE));

            // 9. Orizontal Galben (Jos-Dreapta Jos)
            pieces.add(GamePiece.createHorizontal2x1(6, 5, Color.GOLD));
        }

        return pieces;
    }
}