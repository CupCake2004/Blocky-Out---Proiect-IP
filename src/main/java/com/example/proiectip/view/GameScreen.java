package com.example.proiectip.view;

import com.example.proiectip.model.GamePiece;
import com.example.proiectip.model.LevelFactory;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
    private static final int TILE_SIZE = 60;

    private Map<GamePiece, Group> pieceVisuals = new HashMap<>();

    private double startMouseX, startMouseY;
    private double startPieceX, startPieceY;
    private Group selectedVisual = null;
    private GamePiece selectedPiece = null;
    private boolean isDragging = false;

    public GameScreen(Stage stage, int level) {
        this.stage = stage;
        this.currentLevel = level;
        this.terrainMap = LevelFactory.getLevelMap(level);
        this.pieces = LevelFactory.getLevelPieces(level);
    }

    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #4FB3E8;");

        Label title = new Label("Level " + currentLevel);
        title.setStyle("-fx-font-size: 30px; -fx-text-fill: white; -fx-font-weight: bold;");

        int cols = terrainMap[0].length;
        int rows = terrainMap.length;

        gamePane = new Pane();
        gamePane.setPrefSize(cols * TILE_SIZE, rows * TILE_SIZE);
        gamePane.setMaxSize(cols * TILE_SIZE, rows * TILE_SIZE);
        gamePane.setStyle("-fx-background-color: #2c3e50; -fx-border-color: #1a252f; -fx-border-width: 5;");
        gamePane.setEffect(new DropShadow(15, Color.BLACK));

        drawTerrain();
        spawnPieces();

        Button btnBack = new Button("Meniu");
        btnBack.setOnAction(e -> new LevelSelect(stage).show());

        root.getChildren().addAll(title, gamePane, btnBack);
        stage.setScene(new Scene(root, 900, 750));
    }

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
                } else if (type == 'L') {
                    drawBlade(x, y, Color.DODGERBLUE);
                } else if (type == 'R') {
                    drawBlade(x, y, Color.GOLD);
                } else if (type == 'P') {
                    drawBlade(x, y, Color.MAGENTA);
                } else if (type == 'G') {
                    drawBlade(x, y, Color.LIMEGREEN);
                } else if (type == 'O') {
                    drawBlade(x, y, Color.ORANGE);
                } else if (type == 'C') { // --- NOU: CYAN ---
                    drawBlade(x, y, Color.CYAN);
                } else if (type == 'U') { // --- NOU: PURPLE ---
                    drawBlade(x, y, Color.PURPLE);
                }
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
            startMouseX = e.getSceneX();
            startMouseY = e.getSceneY();
            startPieceX = visual.getLayoutX();
            startPieceY = visual.getLayoutY();

            selectedVisual = visual;
            selectedPiece = piece;
            isDragging = true;
            visual.toFront();
        });

        visual.setOnMouseDragged(e -> {
            if (!isDragging || selectedVisual != visual) return;

            double dx = e.getSceneX() - startMouseX;
            double dy = e.getSceneY() - startMouseY;

            double newX = startPieceX + dx;
            double newY = startPieceY + dy;

            if (canMoveTo(visual, piece, newX, startPieceY)) {
                visual.setLayoutX(newX);
            }
            if (canMoveTo(visual, piece, visual.getLayoutX(), newY)) {
                visual.setLayoutY(newY);
            }

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
            isDragging = false;
            selectedVisual = null;
            selectedPiece = null;
        });
    }

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

        if (cell == 'L' || cell == 'R' || cell == 'P' || cell == 'G' || cell == 'O' || cell == 'C' || cell == 'U') {
            if (!canDestroy(currentPiece.color, cell)) return true;
        }

        for (GamePiece other : pieces) {
            if (other == currentPiece) continue;
            for (GamePiece.Point p : other.structure) {
                int otherBlockR = other.row + p.r;
                int otherBlockC = other.col + p.c;
                if (r == otherBlockR && c == otherBlockC) {
                    return true;
                }
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
                if ((cell == 'L' || cell == 'R' || cell == 'P' || cell == 'G' || cell == 'O' || cell == 'C' || cell == 'U') && canDestroy(piece.color, cell)) {
                    return true;
                }
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

        // --- CULORI NOI ---
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

            if (absRow < 0 || absRow >= terrainMap.length || absCol < 0 || absCol >= terrainMap[0].length) {
                isValid = false; break;
            }
            char cell = terrainMap[absRow][absCol];
            if (cell == 'W') isValid = false;
            if ((cell == 'L' || cell == 'R' || cell == 'P' || cell == 'G' || cell == 'O' || cell == 'C' || cell == 'U') && !canDestroy(piece.color, cell)) isValid = false;

            for (GamePiece other : pieces) {
                if (other == piece) continue;
                for (GamePiece.Point op : other.structure) {
                    if ((other.row + op.r) == absRow && (other.col + op.c) == absCol) {
                        isValid = false;
                        break;
                    }
                }
            }
            if (!isValid) break;
        }

        if (isValid) {
            piece.col = targetCol;
            piece.row = targetRow;
            animateMove(visual, targetCol * TILE_SIZE, targetRow * TILE_SIZE);
        } else {
            animateMove(visual, piece.col * TILE_SIZE, piece.row * TILE_SIZE);
        }
    }

    private void animateMove(Group visual, double destX, double destY) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(200), visual);
        tt.setToX(0); tt.setToY(0);

        double startX = visual.getLayoutX();
        double startY = visual.getLayoutY();

        visual.setLayoutX(destX);
        visual.setLayoutY(destY);
        visual.setTranslateX(startX - destX);
        visual.setTranslateY(startY - destY);

        tt.play();
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
            Button nextLevel = new Button("NEXT LEVEL >>");
            nextLevel.setStyle("-fx-font-size: 24px; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 10 30;");
            nextLevel.setLayoutX(gamePane.getPrefWidth()/2 - 100);
            nextLevel.setLayoutY(gamePane.getPrefHeight()/2 - 30);

            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), nextLevel);
            ft.setFromValue(0.5); ft.setToValue(1);
            ft.setAutoReverse(true); ft.setCycleCount(10);
            ft.play();

            nextLevel.setOnAction(e -> {
                int nextLvl = currentLevel + 1;
                if (LevelFactory.getLevelMap(nextLvl).length > 0) {
                    new GameScreen(stage, nextLvl).show();
                } else {
                    new LevelSelect(stage).show();
                }
            });
            gamePane.getChildren().add(nextLevel);
        }
    }
}