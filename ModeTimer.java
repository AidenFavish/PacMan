
public class ModeTimer
{
    private int modesPassed;
    private int progress;
    private int lvl;
    private int[][] pattern;
    
    public ModeTimer(int lvl) {
        if (lvl >= 7)
            this.lvl = 7;
        else
            this.lvl = lvl;
        
        modesPassed = 0;
        progress = 0;
        pattern = new int[][] {
            {7, 20, 7, 20, 5, 20, 5},
            {7, 20, 7, 20, 5, 17, 1},
            {7, 20, 7, 20, 5, 13, 1},
            {7, 20, 7, 20, 5, 14, 1},
            {5, 20, 5, 20, 5, 17, 1},
            {5, 20, 5, 20, 5, 17, 1},
            {5, 20, 5, 20, 5, 14, 1},
        };
    }
    
    public void next() {
        if (modesPassed >= pattern[lvl - 1].length)
            return;
        
        if (progress >= pattern[lvl - 1][modesPassed] * 60) {
            progress = 0;
            modesPassed++;
        } else {
            progress++;
        }
    }
    
    public Status getMode() {
        return modesPassed % 2 == 0 ? Status.SCATTER : Status.CHASE;
    }
}
