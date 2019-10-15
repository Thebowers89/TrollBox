package TrollBox.TrackingArrows;

import java.util.ArrayList;

public class ArrowRunnable implements Runnable {

    public static ArrayList<TArrow> arrows = new ArrayList<>();

    @Override
    public void run() {
        for (TArrow a : arrows) {
            a.tick();
        }
        ArrayList<TArrow> temp = new ArrayList<>();
        for (TArrow a : arrows) {
            if (a.isOnGround()) {
                temp.add(a);
            }
        }
        for (TArrow a : temp) {
            arrows.remove(a);
        }
    }
}