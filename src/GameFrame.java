import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
public class GameFrame extends JFrame {
    private Panel panel = new Panel();
    private boolean isRunning;
    private int fps = 60;
    private long targetTime = 1000 / fps;
    int numberOfBacteria = 50;
    int numberOfFood = 10;
    int bacteriaSize = 10;
    int foodSize = 10;
    ArrayList<Bacteria> bacteriaList = new ArrayList<>(numberOfBacteria);
    ArrayList<Food> foodList = new ArrayList<>(numberOfFood);
    int screenWidth;
    int screenHeight;

    public GameFrame(String title) {
        super(title);
        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        screenWidth = screenSize.width - insets.left - insets.right;  // Assigning to class-level variables
        screenHeight = screenSize.height - insets.top - insets.bottom;  // Assigning to class-level variables
        initializeFood(numberOfFood);
        initializeBacteria(numberOfBacteria);
        setVisible(true);
    }

    public void startGameLoop() {
        isRunning = true;

        long start, elapsed, wait;

        while (isRunning) {
            start = System.nanoTime();

            update();
            draw();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }

            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        int targetX; // Координата X ближайшей еды
        int targetY; // Координата Y ближайшей еды
        int dx; // Разница между координатами еды и бактерии по оси X
        int dy; // Разница между координатами еды и бактерии по оси Y
        double distance; // Расстояние до ближайшей еды
        double unitX; // Нормализованный вектор dx
        double unitY; // Нормализованный вектор dy
        int newX; // Следующая координата X после 1 кадра
        int newY; // Следующая координата Y после 1 кадра

        for (Bacteria b : bacteriaList) {
            Food nearestFood = findNearestFood(b);
            if (nearestFood != null) {
                targetX = nearestFood.getxCordinate();
                targetY = nearestFood.getyCordinate();
                dx = targetX - b.getX();
                dy = targetY - b.getY();
                distance = Math.sqrt(dx * dx + dy * dy);
                if (distance >=0) {
                    unitX = dx / distance;
                    unitY = dy / distance;
                    newX = (int) (b.getX() + unitX * b.getDeltaX());
                    newY = (int) (b.getY() + unitY * b.getDeltaY());




                        b.setX(newX);
                        b.setY(newY);

                }
            }
        }
    }


    private void draw() {
        panel.repaint();
    }

    public class Panel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawInitialBacteria(g);
            drawInitialFood(g);
        }

        private void drawInitialBacteria(Graphics g) {
            for (int i = 0; i < bacteriaList.size(); i++) {

                g.setColor(new Color(67, 0, 255));

                g.fillOval(bacteriaList.get(i).getX(), bacteriaList.get(i).getY(), bacteriaSize, bacteriaSize);
            }
        }


        private void drawInitialFood(Graphics g) {
            for (int i = 0; i < foodList.size(); i++) {
                if (foodList.get(i).getValue() == 1) {
                    g.setColor(new Color(246, 233, 11));
                } else if (foodList.get(i).getValue() == 2) {
                    g.setColor(new Color(71, 250, 11));
                } else {
                    g.setColor(new Color(250, 11, 11));
                }
                g.fillOval(foodList.get(i).getxCordinate(), foodList.get(i).getyCordinate(), foodSize, foodSize);
            }


        }


    }

    //Initialize the starting population of bacteria that are alive for 1200 frames and add them to bacteriaList
    private void initializeBacteria(int numberOfBacteria) {
        double deltaX;
        double deltaY;

        Random r = new Random();
        for (int i = 0; i < numberOfBacteria; i++) {
            int x = r.nextInt(screenWidth - 20 + 1);
            int y = r.nextInt(screenHeight - 20 + 1);

            deltaX = 1.1;
            deltaY = 1.1;
            int aliveTimeFrames = 20 * fps;
            Bacteria bacteria = new Bacteria(x, y, deltaX, deltaY, aliveTimeFrames);
            bacteriaList.add(bacteria);
        }
    }

    //Initialize the starting number of Food and stores them in foodList
    private void initializeFood(int numberOfFood) {
        int[] color = new int[5];
        Random r = new Random();
        int temp;
        int value;
        for (int i = 0; i < numberOfFood; i++) {
            int x = r.nextInt(screenWidth - 20 + 1);
            int y = r.nextInt(screenHeight - 20 + 1);
            temp = r.nextInt(3); // Generates a random integer between 0 and 2
            if (temp == 0) {
                value = 1;
            } else if (temp == 1) {
                value = 2;
            } else {
                value = 3;
            }

            Food food = new Food(x, y, value);
            foodList.add(food);
        }
    }

    private Food findNearestFood(Bacteria b) {
        Food nereastFood = null;
        int dx;
        int dy;
        double distance;
        double tempDistance = Double.MAX_VALUE;
        for (Food f : foodList) {
            dx = b.getX() - f.getxCordinate();
            dy = b.getY() - f.getyCordinate();
            distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < tempDistance) {
                tempDistance = distance;
                nereastFood = f;
            }

        }
        return nereastFood;

    }



}
