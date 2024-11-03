package simulation;

import fire.*;
import map.*;
import java.util.List;

public class FirePropa extends Evenement{
    private Fire fire;

    public FirePropa(long date, Fire fire){
        super(date);
        this.fire=fire;
    }

    @Override
    public void execute() {
        // Appel de la méthode propagate() pour propager l'incendie
        System.out.println("Propagation du feu en cours à la date : " + this.getDate());
        fire.propagate(this.getDate());
    }

    @Override
    public String toString() {
        return "Événement de propagation de feu à la position : " + fire.getCurrentPosition();
    }
}
