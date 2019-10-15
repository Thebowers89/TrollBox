package TrollBox.TrackingArrows;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

public class TArrow {

    private Player target;
    private Arrow projectile;
    private double speed = 1;

    public TArrow(Arrow p) {
        this.projectile = p;
    }

    public TArrow(Arrow p, double speed) {
        this.projectile = p;
        this.speed = speed;
    }

    public void setTarget(Player p) {
        this.target = p;
    }

    public Player getTarget() {
        return this.target;
    }

    public boolean isOnGround() {
        return this.projectile.isOnGround();
    }

    public void tick() {
        Location p = this.projectile.getLocation();
        p.setY(p.getY() - 1);
        p.setPitch(getPitch(p, this.target.getLocation()));
        p.setYaw(getHeading(p, this.target.getLocation()));
        this.projectile.setVelocity(p.getDirection().multiply(this.speed));
    }

    private float getAngleY(Location pos1, Location pos2) {
        double a = Math.abs(pos2.getY() - pos1.getY());
        double b = pos1.distance(pos2);
        return (float) Math.toDegrees(Math.asin(a/b));
    }

    //Call this method to get the pitch for the projectile
    private float getPitch(Location p, Location target) {
        float angle = getAngleY(p, target);
        if (target.getY() > p.getY()) {
            return -angle;
        } else {
            return angle;
        }
    }

    private float getAngleX(Location pos1, Location pos2) {
        double a = Math.abs(pos2.getX() - pos1.getX());
        double a2 = Math.pow(pos2.getX() - pos1.getX(), 2);
        double b2 = Math.pow(pos2.getZ() - pos1.getZ(), 2);
        double c2 = a2 + b2;
        return (float) Math.toDegrees(Math.acos(a/Math.sqrt(c2)));
    }

    //Call this method to get the heading for the projectile
    private float getHeading(Location p, Location t) {
        float angle = getAngleX(p, t);
        float I = 0;
        float II = -90;
        float III = 180;
        float IV = 90;
        if (t.getZ() > p.getZ()) {
            if (t.getX() > p.getX()) {
                return II + angle;
            } else {
                return IV + -angle;
            }
        } else {
            if (t.getX() > p.getX()) {
                return II + -angle;
            } else {
                return IV + angle;
            }
        }
    }



}