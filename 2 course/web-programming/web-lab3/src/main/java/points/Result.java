package points;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Results")
public class Result implements Serializable {
    @Column(name = "X")
    private float x;
    @Column(name = "Y")
    private float y;
    @Column(name = "R")
    private float r;
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "jpaSequence", sequenceName = "JPA_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private long number;
    @Column(name = "HIT_RESULT")
    private float time;
    @Column(name = "EXECUTION_TIME")
    private boolean hitValue;

    public Result(float x, float y, float r, float time, boolean hitValue) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.time = time;
        this.hitValue = hitValue;
    }

    public Result() {

    }

    public boolean isHitValue() {
        return hitValue;
    }

    public void setHitValue(boolean hitValue) {
        this.hitValue = hitValue;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
