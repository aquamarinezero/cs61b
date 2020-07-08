package creatures;

import edu.princeton.cs.algs4.StdRandom;
import huglife.*;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.Action.ActionType.*;
import static huglife.Action.ActionType.STAY;

public class Clorus extends Creature{

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates plip with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
        color();
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    @Override
    public Color color() {
        return color(34,0,231);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    @Override
    public void attack(Creature c) {
        this.energy = this.energy + c.energy();
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    @Override
    public void move() {
        this.energy = this.energy - 0.03;
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    @Override
    public void stay() {
        this.energy = this.energy - 0.01;
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    @Override
    public Clorus replicate() {
        Clorus c = new Clorus(0.5*this.energy);
        this.energy = 0.5 * this.energy;
        return c;
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        // 使用该链表记录可用的邻居方向
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighhbors = new ArrayDeque<>();
        boolean anyFree = false;
        boolean anyPlip = false;
        // TODO
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        // for () {...}
        for(Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            if(entry.getValue().getClass()== Empty.class) {
                emptyNeighbors.add(entry.getKey());
                anyFree = true;
            }
            if(entry.getValue().getClass()== Plip.class) {
                plipNeighhbors.add(entry.getKey());
                anyPlip = true;
            }
        }

        // Rule 2
        if (anyPlip) {
            double randomNumber = StdRandom.uniform();
            int chooseInt = (int)randomNumber * plipNeighhbors.size();
            for (int i = 0; i < chooseInt; i++)
                plipNeighhbors.removeFirst();
            return new Action(ATTACK, plipNeighhbors.removeFirst());
        }

        if (!anyFree) { // 没有空间就stay
            return new Action(STAY);
        }

        //Rule 3
        // HINT: randomEntry(emptyNeighbors)
        if (this.energy >= 1.0) {
            double randomNumber = StdRandom.uniform();
            int chooseInt = (int)randomNumber * emptyNeighbors.size();
            for (int i = 0; i < chooseInt; i++)
                emptyNeighbors.removeFirst();
            return new Action(REPLICATE, emptyNeighbors.removeFirst());
        }

        // Rule 4
        int chooseInt = (int)StdRandom.uniform() * emptyNeighbors.size();
        for (int i = 0; i < chooseInt; i++)
            emptyNeighbors.removeFirst();
        return new Action(MOVE,emptyNeighbors.removeFirst());
    }
}
