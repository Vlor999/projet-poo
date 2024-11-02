package simulation;

public abstract class Evenement {
    private long date;


    public Evenement(long date) {
        this.date = date;
    }

    public long getDate() {
        return this.date;
    }

    public void execute()
    { 
        System.out.println("["+this.date+"] " + this.toString()+"\n");
    }
}
