package tamagomotion;

public class PapierKamienNozyce {
    public int puntyPet;
    public int punktyPlayer;
    public int odliczanie;
    public int coPokazalZwierzak;
    public boolean koniec;
    private TamagoMotion game;
    static int KAMIEN = 0;
    static int PAPIER = 1;
    static int NOZYCE = 2;
    
    private int i;
    
    public PapierKamienNozyce(TamagoMotion game){
        puntyPet = 0;
        punktyPlayer = 0;
        odliczanie = 3;
        coPokazalZwierzak = -1;
        koniec = false;
        this.game = game;
        
        i = 0;
    }
    
    public void act(){
        i++;
        if(i%100==0){
            odliczanie--;
            if(koniec)
                return;
            if(punktyPlayer==3 || puntyPet==3){
                koniec = true;
                game.pet.setZabawa(game.pet.getZabawa()+50);
                return;
            }
            if(odliczanie < -1)
                odliczanie = 3; 
            if(odliczanie == 0){
                coPokazalZwierzak = (int) (Math.random() * 3);
                int player;
                System.out.println("palce" + game.palce);
                if(game.palce == 2){
                    player = NOZYCE;
                    System.out.println("NOŻYCE");
                }
                else if(game.palce >2){
                    player = PAPIER;
                    System.out.println("PAPIER");
                }
                else{
                    player = KAMIEN;
                    System.out.println("KAMIEN");
                }
                
                if(coPokazalZwierzak == player){
                    //remis
                }
                else if((coPokazalZwierzak==KAMIEN && player==PAPIER)||
                        (coPokazalZwierzak==PAPIER && player==NOZYCE)||
                        (coPokazalZwierzak==NOZYCE && player==KAMIEN)){
                    punktyPlayer++;
                }
                else if((player==KAMIEN && coPokazalZwierzak==PAPIER)||
                        (player==PAPIER && coPokazalZwierzak==NOZYCE)||
                        (player==NOZYCE && coPokazalZwierzak==KAMIEN)){
                    puntyPet++;
                }
                    
            }
            System.out.println(i);
        }
            
    }
}
