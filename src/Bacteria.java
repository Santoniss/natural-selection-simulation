public class Bacteria {
    private int x;
    private int y;
    private boolean alive;
    private double deltaX;
    private double deltaY;
    private int aliveTime;

    public void setAliveTime(int aliveTime) {
        this.aliveTime = aliveTime;
    }

    public int getAliveTime() {
        return aliveTime;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDeltaX(double deltaX) { // Changed from float to double
        this.deltaX = deltaX;
    }

    public void setDeltaY(double deltaY) { // Changed from float to double
        this.deltaY = deltaY;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    private int Color;

    public Bacteria(int x, int y, double deltaX, double deltaY, int aliveTime) {
        this.x = x;
        this.y = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.alive = true;
        this.aliveTime = aliveTime;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
