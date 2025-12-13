package com.example.proiectip;

import com.example.proiectip.model.GamePiece;
import com.example.proiectip.model.LevelFactory;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LevelFactoryTest {

    @Test
    public void testLevel5MapIntegrity() {
        System.out.println("=== TEST PRO: Integritatea Hărții Nivelului 5 ===");

        char[][] map = LevelFactory.getLevelMap(5);

        // 1. Verificăm dimensiunea 8x8
        assertEquals(8, map.length, "Înălțimea hărții trebuie să fie 8");
        assertEquals(8, map[0].length, "Lățimea hărții trebuie să fie 8");

        // 2. Verificăm Colțurile (trebuie să fie Pereți 'W')
        assertEquals('W', map[0][0], "Colțul Stânga-Sus trebuie să fie Perete");
        assertEquals('W', map[0][7], "Colțul Dreapta-Sus trebuie să fie Perete");
        assertEquals('W', map[7][0], "Colțul Stânga-Jos trebuie să fie Perete");
        assertEquals('W', map[7][7], "Colțul Dreapta-Jos trebuie să fie Perete");

        // 3. Verificăm existența Porților specifice
        // Căutăm poarta Cyan ('C') pe rândul de sus (row 0)
        boolean foundCyanGate = false;
        for(char c : map[0]) {
            if (c == 'C') foundCyanGate = true;
        }
        assertTrue(foundCyanGate, "Harta trebuie să conțină poarta Cyan (C) pe rândul de sus");

        System.out.println("  [OK] Harta are pereți și porți corecte.");
        System.out.println(">>> TEST TRECUT!\n");
    }

    @Test
    public void testLevel5PiecesContent() {
        System.out.println("=== TEST PRO: Validare Piese Nivel 5 ===");

        List<GamePiece> pieces = LevelFactory.getLevelPieces(5);

        // 1. Verificăm numărul total de piese (FIX 9 PIESE)
        assertEquals(9, pieces.size(), "Nivelul 5 trebuie să aibă fix 9 piese");

        // 2. Verificăm că prima piesă există și are o culoare validă
        assertNotNull(pieces.get(0), "Prima piesă nu trebuie să fie null");

        // Verificăm că avem piese de culori diferite (măcar 2-3 exemple)
        boolean hasMagenta = false;
        boolean hasGreen = false;
        boolean hasPurple = false;

        for (GamePiece p : pieces) {
            if (p.color.equals(Color.MAGENTA)) hasMagenta = true;
            if (p.color.equals(Color.LIMEGREEN)) hasGreen = true;
            if (p.color.equals(Color.PURPLE)) hasPurple = true;
        }

        assertTrue(hasMagenta, "Trebuie să existe cel puțin o piesă MAGENTA");
        assertTrue(hasGreen, "Trebuie să existe cel puțin o piesă VERDE");
        assertTrue(hasPurple, "Trebuie să existe cel puțin o piesă MOV");

        System.out.println("  [OK] Cele 9 piese sunt prezente și au culorile necesare.");
        System.out.println(">>> TEST TRECUT!\n");
    }
}