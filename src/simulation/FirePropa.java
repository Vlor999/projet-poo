package simulation;

import fire.*;

public class FirePropa extends Evenement{
    private Fire fire;

    public FirePropa(long date, Fire fire){
        super(date);
        this.fire=fire;
    }

    @Override
    public void execute() {
        // Appel de la méthode propagate() pour propager l'incendie
        fire.propagate(this.getDate());
    }

    @Override
    public String toString() {
        return "[" + this.getDate() + "] Événement de propagation de feu à la position : \n" + fire.getCurrentPosition() + "\n";
    }
}
