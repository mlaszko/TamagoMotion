package tamagomotion;


public class Kupa extends Actor{
    protected float vx;
    public boolean sprzatnieta;

    public Kupa(Stage stage) {
        super(stage);
        setSpriteNames( new String[] {"kupa.png","kupa2.png"} );
        setFrameSpeed(25);
        vx = 0;
        sprzatnieta = false;
    }

    
    public void act(){
        x += vx;
    }
    
    public void sprzatnij(int speed){
        vx = speed;
        sprzatnieta = true;
    }
    
}
