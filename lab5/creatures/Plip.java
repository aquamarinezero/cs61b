package creatures;

import edu.princeton.cs.algs4.StdRandom;
import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.Action.ActionType.*;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

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
    public Plip(double e) {
        super("plip");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
        energyHelper();
        color();
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    @Override
    public Color color() {
        g = (int)(96 * energy + 63);
        return color(99, g, 76);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    @Override
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    @Override
    public void move() {
        this.energy = this.energy - 0.15;
        energyHelper();
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    @Override
    public void stay() {
        this.energy = this.energy + 0.2;
        energyHelper();
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    @Override
    public Plip replicate() {
        Plip p = new Plip(0.5*this.energy);
        this.energy = 0.5 * this.energy;
        this.color();
        return p;
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
        boolean anyFree = false;
        boolean anyClorus = false;
        // TODO
        // (Google: Enhanced for-loop over keys of NEIGHBORS?)
        // for () {...}
        for(Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            if(entry.getValue().getClass()== Empty.class) {
                emptyNeighbors.add(entry.getKey());
                anyFree = true;
            }
            if(entry.getValue().getClass() == Clorus.class) {
                anyClorus = true;
            }
        }
        if (!anyFree) { // 没有空间就stay
            return new Action(STAY);
        }

        // Rule 2
        // HINT: randomEntry(emptyNeighbors)
        if (this.energy >= 1.0) {
            double randomNumber = StdRandom.uniform();
            int chooseInt = (int)randomNumber * emptyNeighbors.size();
            for (int i = 0; i < chooseInt; i++)
                emptyNeighbors.removeFirst();
            return new Action(REPLICATE, emptyNeighbors.removeFirst());
        }

        // Rule 3
        if (anyClorus){
            double randomNumber = StdRandom.uniform();
            if (randomNumber > 0.5) {
                int chooseInt = (int)StdRandom.uniform() * emptyNeighbors.size();
                for (int i = 0; i < chooseInt; i++)
                    emptyNeighbors.removeFirst();
                return new Action(MOVE,emptyNeighbors.removeFirst());
            }
        }
        // Rule 4
        return new Action(STAY);
    }

    private void energyHelper() {
        if (this.energy < 0) {
            this.energy = 0;
        }
        if (this.energy > 2) {
            this.energy = 2;
        }
    }
}
