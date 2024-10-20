package gui;

class DefaultSimulator
implements Simulable
{
    public void next() {
        message("next()");
    }


    public void restart() {
        message("restart()");
    }

    private void message(String paramString) {
        System.out.println("Methode " + paramString + ": pas encore implementee! (default simulator)");
    }
}

