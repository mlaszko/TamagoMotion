package tamagomotion;

import java.awt.event.KeyEvent;

public class Pet extends Actor {

    protected static final int PLAYER_SPEED = 4;
    protected float vx;
    protected float vy;
    
    private int jedzenie;
    private int milosc;
    private int zabawa;
    
    private int i;
    
    private boolean up,down,left,right;
    
    public boolean zrobKupe;

    public Pet(Stage stage) {
        super(stage);
        setSpriteNames( new String[] {"pikachu.png"} );
        setFrameSpeed(50);
        jedzenie = 30;
        milosc = 30;
        zabawa = 30;
        vx = (float) 0.5;
        i = 0;
        zrobKupe = false;
    }


    public void act(boolean ruch) {
        super.act();
        i++;
        if(ruch){
            x += vx;
            if (x < 300 || x > 500) {
                vx = -vx;
            }
        }
        if(i%50 == 0){
            jedzenie -=1;
            milosc -=1;
        }
        if(i%100 == 0){
            zabawa -=1;
        }
        if(i%800 == 0){
            zrobKupe = true;
        }
    }

    public float getVx() {
        return vx;
    }

    public void setVx(int i) {
        vx = i;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(int i) {
        vy = i;
    }


    /**
     * @return the jedzenie
     */
    public int getJedzenie() {
        return jedzenie;
    }

    /**
     * @param jedzenie the jedzenie to set
     */
    public void setJedzenie(int jedzenie) {
        this.jedzenie = jedzenie;
        if(this.jedzenie>100)
            this.jedzenie = 100;
        else if(this.jedzenie<0)
            this.jedzenie = 0; 
    }

    /**
     * @return the milosc
     */
    public int getMilosc() {
        return milosc;
    }

    /**
     * @param milosc the milosc to set
     */
    public void setMilosc(int milosc) {
        this.milosc = milosc;
        if(this.milosc>100)
            this.milosc = 100;
        else if(this.milosc<0)
            this.milosc = 0;
    }

    /**
     * @return the zabawa
     */
    public int getZabawa() {
        return zabawa;
    }

    /**
     * @param zabawa the zabawa to set
     */
    public void setZabawa(int zabawa) {
        this.zabawa = zabawa;
        if(this.zabawa>100)
            this.zabawa = 100;
        else if(this.zabawa < 0)
            this.zabawa = 0;
    }

}
