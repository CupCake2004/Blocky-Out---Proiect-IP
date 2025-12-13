package com.example.proiectip.view;

import com.example.proiectip.controller.GameManager;
import com.example.proiectip.model.GamePiece;
import com.example.proiectip.model.LevelFactory;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameScreen {
    private Stage stage;
    private int currentLevel;
    private char[][] terrainMap;
    private List<GamePiece> pieces;

    private Pane gamePane;
    private StackPane rootStack; // Folosim StackPane ca rădăcină pentru a putea pune Overlay-uri
    private static final int TILE_SIZE = 60;

    private Map<GamePiece, Group> pieceVisuals = new HashMap<>();

    // Variabile Drag
    private double startMouseX, startMouseY;
    private double startPieceX, startPieceY;
    private Group selectedVisual = null;
    private GamePiece selectedPiece = null;
    private boolean isDragging = false;

    // --- CRONOMETRU ---
    private Label timerLabel;
    private Timeline timeline;
    private int timeSeconds;

    public GameScreen(Stage stage, int level) {
        this.stage = stage;
        this.currentLevel = level;
        this.terrainMap = LevelFactory.getLevelMap(level);
        this.pieces = LevelFactory.getLevelPieces(level);

        // --- LOGICĂ TIMP DINAMIC ---
        // Nivel 1: 60s, Nivel 2: 75s ... Nivel 5: 120s
        this.timeSeconds = 45 + (level * 15);
    }

    public void show() {
        // Folosim StackPane ca să putem pune ferestre peste joc (Overlay)
        rootStack = new StackPane();
        rootStack.setStyle("-fx-background-color: #2c3e50;");

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        // Fundal gradient frumos
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #4ca1af, #2c3e50);");

        // Header cu Nivel și Cronometru
        HBox header = new HBox(50);
        header.setAlignment(Pos.CENTER);

        Label title = new Label("NIVELUL " + currentLevel);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        title.setTextFill(Color.WHITE);
        title.setEffect(new DropShadow(5, Color.BLACK));

        // Configurare Cronometru Vizual
        timerLabel = new Label(formatTime(timeSeconds));
        timerLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 28));
        timerLabel.setTextFill(Color.web("#f1c40f")); // Galben
        timerLabel.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-padding: 5 15; -fx-background-radius: 10;");

        header.getChildren().addAll(title, timerLabel);

        int cols = terrainMap[0].length;
        int rows = terrainMap.length;

        gamePane = new Pane();
        gamePane.setPrefSize(cols * TILE_SIZE, rows * TILE_SIZE);
        gamePane.setMaxSize(cols * TILE_SIZE, rows * TILE_SIZE);
        gamePane.setStyle("-fx-background-color: #34495e; -fx-border-color: #ecf0f1; -fx-border-width: 4; -fx-background-radius: 5; -fx-border-radius: 5;");
        gamePane.setEffect(new DropShadow(20, Color.BLACK));

        drawTerrain();
        spawnPieces();
        startTimer(); // Pornim timpul!

        Button btnBack = new Button("Meniu Principal");
        btnBack.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-cursor: hand;");
        btnBack.setOnAction(e -> {
            stopTimer();
            new LevelSelect(stage).show();
        });

        mainLayout.getChildren().addAll(header, gamePane, btnBack);

        rootStack.getChildren().add(mainLayout); // Adăugăm jocul în stack
        stage.setScene(new Scene(rootStack, 900, 750));
    }

    // Formatare timp mm:ss
    private String formatTime(int totalSeconds) {
        int m = totalSeconds / 60;
        int s = totalSeconds % 60;
        return String.format("%02d:%02d", m, s);
    }

    // --- LOGICA TIMER ---
    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeSeconds--;
            timerLabel.setText(formatTime(timeSeconds));

            // Schimbăm culoarea în roșu și pulsăm dacă mai sunt 10 secunde
            if (timeSeconds <= 10) {
                timerLabel.setTextFill(Color.RED);
                if (timeSeconds % 2 == 0) timerLabel.setStyle("-fx-background-color: rgba(255,0,0,0.3); -fx-padding: 5 15; -fx-background-radius: 10;");
                else timerLabel.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-padding: 5 15; -fx-background-radius: 10;");
            }

            if (timeSeconds <= 0) {
                stopTimer();
                showGameOverScreen();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    // --- ECRANE FINALE (MODERNIZATE) ---

    private void showGameOverScreen() {
        // Blur pe fundal
        gamePane.setEffect(new GaussianBlur(10));

        VBox overlay = new VBox(20);
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85);");
        overlay.setMaxSize(400, 300);
        overlay.setEffect(new DropShadow(20, Color.BLACK));

        Label msg = new Label("TIMP EXPIRAT!");
        msg.setTextFill(Color.RED);
        msg.setFont(Font.font("Arial", FontWeight.BOLD, 36));

        Button btnRetry = new Button("Încearcă din nou");
        btnRetry.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10 20; -fx-background-radius: 10;");
        btnRetry.setOnAction(e -> new GameScreen(stage, currentLevel).show());

        Button btnMenu = new Button("Meniu");
        btnMenu.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 10; -fx-padding: 8 20;");
        btnMenu.setOnAction(e -> new LevelSelect(stage).show());

        overlay.getChildren().addAll(msg, btnRetry, btnMenu);
        rootStack.getChildren().add(overlay); // Punem peste joc
    }

    private void showVictoryScreen(boolean isLastLevel) {
        // Blur pe fundal
        gamePane.setEffect(new GaussianBlur(10));

        VBox overlay = new VBox(20);
        overlay.setAlignment(Pos.CENTER);
        // Fundal semi-transparent elegant
        overlay.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 20;");
        overlay.setMaxSize(500, 350);
        overlay.setEffect(new DropShadow(20, Color.BLACK));

        Label title = new Label(isLastLevel ? "JOC COMPLET!" : "NIVEL COMPLET!");
        title.setTextFill(isLastLevel ? Color.web("#8e44ad") : Color.web("#27ae60")); // Mov pt final, Verde pt nivel
        title.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        title.setEffect(new DropShadow(2, Color.GRAY));

        Label score = new Label("Timp rămas: " + timeSeconds + " secunde");
        score.setFont(Font.font("Arial", 20));
        score.setTextFill(Color.DARKGRAY);

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);

        if (isLastLevel) {
            // ECRAN FINAL JOC
            Label congrats = new Label("Felicitări! Ai terminat toate nivelele!");
            congrats.setStyle("-fx-font-size: 16px; -fx-font-style: italic;");

            Button btnMenu = new Button("Meniu Principal");
            btnMenu.setStyle("-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10 30; -fx-background-radius: 15; -fx-cursor: hand;");
            btnMenu.setOnAction(e -> new LevelSelect(stage).show());

            overlay.getChildren().addAll(title, congrats, score, btnMenu);
        } else {
            // ECRAN NIVEL URMĂTOR
            Button btnNext = new Button("Nivelul Următor >>");
            btnNext.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10 30; -fx-background-radius: 15; -fx-cursor: hand;");
            btnNext.setOnAction(e -> {
                int nextLvl = currentLevel + 1;
                new GameScreen(stage, nextLvl).show();
            });

            Button btnMenu = new Button("Meniu");
            btnMenu.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 15;");
            btnMenu.setOnAction(e -> new LevelSelect(stage).show());

            buttons.getChildren().addAll(btnMenu, btnNext);
            overlay.getChildren().addAll(title, score, buttons);
        }

        // Animație de intrare (Pop-up)
        overlay.setScaleX(0); overlay.setScaleY(0);
        FadeTransition ft = new FadeTransition(Duration.millis(300), overlay);
        ft.setFromValue(0); ft.setToValue(1);

        javafx.animation.ScaleTransition st = new javafx.animation.ScaleTransition(Duration.millis(300), overlay);
        st.setFromX(0); st.setFromY(0); st.setToX(1); st.setToY(1);

        ParallelTransition pt = new ParallelTransition(ft, st);
        pt.play();

        rootStack.getChildren().add(overlay);
    }

    // --- LOGICA GRAFICĂ ---

    private void drawTerrain() {
        for (int r = 0; r < terrainMap.length; r++) {
            for (int c = 0; c < terrainMap[r].length; c++) {
                char type = terrainMap[r][c];
                double x = c * TILE_SIZE;
                double y = r * TILE_SIZE;

                if (type == 'W') {
                    Rectangle rect = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
                    rect.setFill(Color.web("#7f8c8d"));
                    rect.setStroke(Color.web("#2c3e50"));
                    gamePane.getChildren().add(rect);
                } else if (type == '_') {
                    Rectangle rect = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
                    rect.setFill(Color.WHITE);
                    rect.setStroke(Color.web("#ecf0f1"));
                    gamePane.getChildren().add(rect);
                } else if (type == 'L') drawBlade(x, y, Color.DODGERBLUE);
                else if (type == 'R') drawBlade(x, y, Color.GOLD);
                else if (type == 'P') drawBlade(x, y, Color.MAGENTA);
                else if (type == 'G') drawBlade(x, y, Color.LIMEGREEN);
                else if (type == 'O') drawBlade(x, y, Color.ORANGE);
                else if (type == 'C') drawBlade(x, y, Color.CYAN);
                else if (type == 'U') drawBlade(x, y, Color.PURPLE);
            }
        }
    }

    private void drawBlade(double x, double y, Color c) {
        StackPane stack = new StackPane();
        stack.setLayoutX(x); stack.setLayoutY(y);
        stack.setPrefSize(TILE_SIZE, TILE_SIZE);

        Rectangle bg = new Rectangle(TILE_SIZE, TILE_SIZE, c.darker());
        Circle s1 = new Circle(6, Color.WHITE); s1.setTranslateY(-20);
        Circle s2 = new Circle(6, Color.WHITE);
        Circle s3 = new Circle(6, Color.WHITE); s3.setTranslateY(20);

        stack.getChildren().addAll(bg, s1, s2, s3);
        gamePane.getChildren().add(stack);
    }

    private void spawnPieces() {
        for (GamePiece piece : pieces) {
            Group visualGroup = new Group();
            for (GamePiece.Point p : piece.structure) {
                Rectangle block = new Rectangle(p.c * TILE_SIZE, p.r * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                block.setFill(piece.color);
                block.setArcWidth(15); block.setArcHeight(15);
                block.setStroke(Color.BLACK); block.setStrokeWidth(1.5);

                // Efect 3D simplu
                block.setEffect(new DropShadow(5, Color.BLACK));

                visualGroup.getChildren().add(block);
            }
            visualGroup.setLayoutX(piece.col * TILE_SIZE);
            visualGroup.setLayoutY(piece.row * TILE_SIZE);

            addMouseLogic(visualGroup, piece);
            gamePane.getChildren().add(visualGroup);
            pieceVisuals.put(piece, visualGroup);
        }
    }

    private void addMouseLogic(Group visual, GamePiece piece) {
        visual.setOnMousePressed(e -> {
            if (timeline.getStatus() != Animation.Status.RUNNING) return; // Nu poți muta dacă timpul e oprit
            startMouseX = e.getSceneX();
            startMouseY = e.getSceneY();
            startPieceX = visual.getLayoutX();
            startPieceY = visual.getLayoutY();

            selectedVisual = visual;
            selectedPiece = piece;
            isDragging = true;
            visual.toFront();
            visual.setOpacity(0.8); // Puțin transparent când îl tragi
        });

        visual.setOnMouseDragged(e -> {
            if (!isDragging || selectedVisual != visual) return;

            double dx = e.getSceneX() - startMouseX;
            double dy = e.getSceneY() - startMouseY;

            double newX = startPieceX + dx;
            double newY = startPieceY + dy;

            if (canMoveTo(visual, piece, newX, startPieceY)) visual.setLayoutX(newX);
            if (canMoveTo(visual, piece, visual.getLayoutX(), newY)) visual.setLayoutY(newY);

            if (checkDestroy(visual, piece)) {
                explodePiece(piece, visual);
                isDragging = false;
                selectedVisual = null;
                selectedPiece = null;
            }
        });

        visual.setOnMouseReleased(e -> {
            if (!isDragging || selectedVisual != visual) return;
            snapToGrid(visual, piece);
            visual.setOpacity(1.0);
            isDragging = false;
            selectedVisual = null;
            selectedPiece = null;
        });
    }

    // --- VERIFICĂRI ---

    private boolean canMoveTo(Group visual, GamePiece piece, double proposedX, double proposedY) {
        double margin = 1.0;
        for (GamePiece.Point p : piece.structure) {
            double minX = proposedX + (p.c * TILE_SIZE) + margin;
            double maxX = proposedX + ((p.c + 1) * TILE_SIZE) - margin;
            double minY = proposedY + (p.r * TILE_SIZE) + margin;
            double maxY = proposedY + ((p.r + 1) * TILE_SIZE) - margin;

            if (isBlocked(minX, minY, piece) || isBlocked(maxX, minY, piece) ||
                    isBlocked(minX, maxY, piece) || isBlocked(maxX, maxY, piece)) {
                return false;
            }
        }
        return true;
    }

    private boolean isBlocked(double x, double y, GamePiece currentPiece) {
        int c = (int) (x / TILE_SIZE);
        int r = (int) (y / TILE_SIZE);

        if (r < 0 || r >= terrainMap.length || c < 0 || c >= terrainMap[0].length) return true;
        char cell = terrainMap[r][c];

        if (cell == 'W') return true;
        if ("LRPGO CU".indexOf(cell) != -1 && !canDestroy(currentPiece.color, cell)) return true;

        for (GamePiece other : pieces) {
            if (other == currentPiece) continue;
            for (GamePiece.Point p : other.structure) {
                if ((other.row + p.r) == r && (other.col + p.c) == c) return true;
            }
        }
        return false;
    }

    private boolean checkDestroy(Group visual, GamePiece piece) {
        double currentX = visual.getLayoutX();
        double currentY = visual.getLayoutY();

        for (GamePiece.Point p : piece.structure) {
            double centerX = currentX + (p.c * TILE_SIZE) + (TILE_SIZE / 2.0);
            double centerY = currentY + (p.r * TILE_SIZE) + (TILE_SIZE / 2.0);

            int c = (int) (centerX / TILE_SIZE);
            int r = (int) (centerY / TILE_SIZE);

            if (r >= 0 && r < terrainMap.length && c >= 0 && c < terrainMap[0].length) {
                char cell = terrainMap[r][c];
                if ("LRPGO CU".indexOf(cell) != -1 && canDestroy(piece.color, cell)) return true;
            }
        }
        return false;
    }

    private boolean canDestroy(Color color, char blade) {
        if (color.equals(Color.GOLD) && blade == 'R') return true;
        if (color.equals(Color.DODGERBLUE) && blade == 'L') return true;
        if (color.equals(Color.MAGENTA) && blade == 'P') return true;
        if (color.equals(Color.LIMEGREEN) && blade == 'G') return true;
        if (color.equals(Color.ORANGE) && blade == 'O') return true;
        if (color.equals(Color.CYAN) && blade == 'C') return true;
        if (color.equals(Color.PURPLE) && blade == 'U') return true;
        return false;
    }

    private void snapToGrid(Group visual, GamePiece piece) {
        int targetCol = (int) Math.round(visual.getLayoutX() / TILE_SIZE);
        int targetRow = (int) Math.round(visual.getLayoutY() / TILE_SIZE);

        boolean isValid = true;

        for (GamePiece.Point p : piece.structure) {
            int absCol = targetCol + p.c;
            int absRow = targetRow + p.r;
            if (absRow < 0 || absRow >= terrainMap.length || absCol < 0 || absCol >= terrainMap[0].length) { isValid = false; break; }

            char cell = terrainMap[absRow][absCol];
            if (cell == 'W') isValid = false;
            if ("LRPGO CU".indexOf(cell) != -1 && !canDestroy(piece.color, cell)) isValid = false;

            for (GamePiece other : pieces) {
                if (other == piece) continue;
                for (GamePiece.Point op : other.structure) {
                    if ((other.row + op.r) == absRow && (other.col + op.c) == absCol) { isValid = false; break; }
                }
            }
            if (!isValid) break;
        }

        double destX = (isValid ? targetCol : piece.col) * TILE_SIZE;
        double destY = (isValid ? targetRow : piece.row) * TILE_SIZE;

        if (isValid) { piece.col = targetCol; piece.row = targetRow; }

        TranslateTransition tt = new TranslateTransition(Duration.millis(200), visual);
        tt.setToX(0); tt.setToY(0);
        visual.setLayoutX(destX);
        visual.setLayoutY(destY);
        visual.setTranslateX(visual.getLayoutX() - destX); // Hack pt animație smooth
        visual.setTranslateY(visual.getLayoutY() - destY);
        visual.setTranslateX(visual.getTranslateX()); // Reset
        visual.setTranslateY(visual.getTranslateY());
        tt.setFromX(startPieceX - destX); // Simplificare: doar punem la loc
        tt.stop();
        // Mai simplu:
        visual.setTranslateX(0); visual.setTranslateY(0);
        visual.setLayoutX(destX); visual.setLayoutY(destY);
    }

    private void explodePiece(GamePiece piece, Group visual) {
        gamePane.getChildren().remove(visual);
        pieces.remove(piece);
        pieceVisuals.remove(piece);

        Random rand = new Random();
        double currentX = visual.getLayoutX();
        double currentY = visual.getLayoutY();

        for (GamePiece.Point p : piece.structure) {
            double baseX = currentX + p.c * TILE_SIZE;
            double baseY = currentY + p.r * TILE_SIZE;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    double size = TILE_SIZE / 4.0;
                    Rectangle part = new Rectangle(baseX + j*size, baseY + i*size, size-1, size-1);
                    part.setFill(piece.color);
                    gamePane.getChildren().add(part);

                    TranslateTransition fly = new TranslateTransition(Duration.seconds(0.6), part);
                    fly.setByX(rand.nextInt(150) - 75);
                    fly.setByY(rand.nextInt(150) - 75);

                    FadeTransition fade = new FadeTransition(Duration.seconds(0.6), part);
                    fade.setToValue(0);

                    ParallelTransition pt = new ParallelTransition(fly, fade);
                    pt.setOnFinished(e -> gamePane.getChildren().remove(part));
                    pt.play();
                }
            }
        }
        checkWin();
    }

    private void checkWin() {
        if (pieces.isEmpty()) {
            stopTimer(); // OPRIM TIMPUL

            // --- SALVAM PROGRESUL IN SINGLETON ---
            GameManager.getInstance().unlockLevel(currentLevel + 1);

            // Verificăm dacă este ULTIMUL nivel (5)
            boolean isLastLevel = (currentLevel >= 5);

            // Afișăm Overlay-ul corespunzător
            showVictoryScreen(isLastLevel);
        }
    }
}