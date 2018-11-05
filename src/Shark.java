public class Shark extends MyEnt {
    int maxRange;

    int dx, dy;
    int tackleRange;
    boolean isTackling = false;

    public Shark(int gameWidth) {
        super(250, "shark.png");

        maxRange = (int) (gameWidth*1.5);
        dx = 0;
        dy = 0;
        tackleRange = maxRange/3;
    }

    public void update(Location locPlr){
        if(System.currentTimeMillis() - lastActionTime > actionDelay) {
            lastActionTime = System.currentTimeMillis();

            int sqrRange = getSqrRange(locPlr);
            int sqrMax = sqr(tackleRange);

            //initialize tackle when in range and only stop when off screen
            if(sqrRange < tackleRange)
                isTackling = true;
            else if(isTackling && sqrRange > sqrMax)
                isTackling = false;

            //stop tracking when tackling
            if(!isTackling){
                dx = getSpeed(x, locPlr.x);
                dy = getSpeed(y, locPlr.y);
            }

            //always need to move
            x += dx;
            y += dy;
        }
    }

    private int getSqrRange(Location target){
        return sqr(target.x - x) + sqr(target.y - y);
    }

    private int sqr(int a){
        return a*a;
    }

    private int getSpeed(int a, int target){
        double rate = maxRange*5;
        int da = (int) Math.round(( rate / (target - a) ));

        return da;
    }
}
