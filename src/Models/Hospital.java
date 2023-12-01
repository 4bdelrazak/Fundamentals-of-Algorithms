package Models;

import java.awt.*;
import java.util.Random;

public class Hospital {
    public Point location ;

    public Priority priority ;

    public Hospital(Point location ){
        this.location=location;
        this.priority=getRandomPriority();
    }
    private Priority getRandomPriority() {
        Random random = new Random();
        Priority[] priorities = Priority.values();
        return priorities[random.nextInt(priorities.length)];
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "("+location.x +","+ location.y+")"+"priority "+this.priority;
    }
}


