package com.example.proiectip;

import com.example.proiectip.controller.GameManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameManagerTest {

    @Test
    public void testSingletonAndProgression() {
        System.out.println("=== TEST LOGICĂ: Progresie Joc ===");

        GameManager gm = GameManager.getInstance();

        // Notă: Testele rulează în ordine aleatorie uneori, deci nu ne bazăm pe valoarea 1.
        // Ne bazăm pe faptul că trebuie să crească.
        int startLevel = gm.getMaxUnlockedLevel();
        System.out.println("  -> Nivel start: " + startLevel);

        // Simulăm câștigarea nivelului curent
        gm.unlockLevel(startLevel + 1);

        assertEquals(startLevel + 1, gm.getMaxUnlockedLevel(), "Nivelul nu a crescut corect");
        System.out.println("  [OK] Nivel deblocat cu succes: " + gm.getMaxUnlockedLevel());

        // Simulăm un salt imposibil (ex: sunt la 2, încerc să deblochez 10 direct?)
        // Deși codul nostru permite (dacă level > max), logic ar trebui să testăm că
        // re-jucarea nivelului 1 nu resetează progresul de la 5.

        int highLevel = gm.getMaxUnlockedLevel();
        gm.unlockLevel(1); // Jucătorul joacă iar nivelul 1

        assertEquals(highLevel, gm.getMaxUnlockedLevel(), "Progresul nu trebuie pierdut la rejucare");
        System.out.println("  [OK] Progresul este salvat (Persistent).");

        System.out.println(">>> TEST TRECUT!\n");
    }
}