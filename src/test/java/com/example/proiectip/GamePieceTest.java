package com.example.proiectip;

import com.example.proiectip.model.GamePiece;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GamePieceTest {

    // Helper: Verifică dacă un punct (r, c) există în structura piesei
    private boolean hasPoint(GamePiece piece, int r, int c) {
        for (GamePiece.Point p : piece.structure) {
            if (p.r == r && p.c == c) return true;
        }
        return false;
    }

    @Test
    public void testPlusShapeStructure() {
        System.out.println("=== TEST COMPLEX: Geometria Formei PLUS ===");

        GamePiece plus = GamePiece.createPlusShape(3, 3, Color.ORANGE);

        // 1. Verificăm mărimea (5 blocuri)
        assertEquals(5, plus.structure.size(), "Forma PLUS trebuie să aibă 5 blocuri");

        // 2. Verificăm punctele exacte (Relativ la colțul stânga-sus al formei)
        // Trebuie să aibă centru la (1,1) și brațe sus, jos, stânga, dreapta
        assertTrue(hasPoint(plus, 1, 1), "Lipsește centrul (1,1)");
        assertTrue(hasPoint(plus, 0, 1), "Lipsește brațul de sus (0,1)");
        assertTrue(hasPoint(plus, 2, 1), "Lipsește brațul de jos (2,1)");
        assertTrue(hasPoint(plus, 1, 0), "Lipsește brațul stâng (1,0)");
        assertTrue(hasPoint(plus, 1, 2), "Lipsește brațul drept (1,2)");

        System.out.println("  [OK] Forma PLUS este geometric corectă.");
        System.out.println(">>> TEST TRECUT!\n");
    }

    @Test
    public void testHorizontal4x1Structure() {
        System.out.println("=== TEST COMPLEX: Bara Lungă 4x1 ===");

        GamePiece longBar = GamePiece.createHorizontal4x1(0, 0, Color.PURPLE);

        assertEquals(4, longBar.structure.size());

        // Verificăm că toate sunt pe rândul 0 și coloanele 0,1,2,3
        assertTrue(hasPoint(longBar, 0, 0));
        assertTrue(hasPoint(longBar, 0, 1));
        assertTrue(hasPoint(longBar, 0, 2));
        assertTrue(hasPoint(longBar, 0, 3));

        System.out.println("  [OK] Bara lungă este generată corect.");
        System.out.println(">>> TEST TRECUT!\n");
    }

    @Test
    public void testInitialState() {
        System.out.println("=== TEST STATE: Starea Inițială ===");
        GamePiece p = GamePiece.createSquare2x2(5, 5, Color.RED);

        assertFalse(p.isDestroyed, "Piesa nu trebuie să fie distrusă la început");
        assertEquals(5, p.row, "Coordonata Row inițială incorectă");
        assertEquals(5, p.col, "Coordonata Col inițială incorectă");

        System.out.println("  [OK] Starea inițială este validă.");
        System.out.println(">>> TEST TRECUT!\n");
    }
}