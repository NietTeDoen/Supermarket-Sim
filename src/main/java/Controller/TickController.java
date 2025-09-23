package Controller;

public class TickController {
    final double TICKS_PER_SECOND = 30.0; //The amount it updates every second, so 30 means 30fps
    final double SKIP_TICKS = 1000 /  TICKS_PER_SECOND;
    double nextTick = System.currentTimeMillis();
    boolean running = true;

    int count = 0;

    public void start() throws InterruptedException {
        System.out.println("Starting Tick Controller");
        while (running) {
            while (System.currentTimeMillis() > nextTick) {
                tick();
                nextTick += SKIP_TICKS;
            }
            Thread.sleep(1);
        }

    }
    private void tick(){
        if(count < 30)
            count++;
        else {
            System.out.println("Tick " + (System.currentTimeMillis() - nextTick));
            count = 0;
        }
    }
}
