package tamagomotion;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

/**
 *
 * @author Michal Laszkowski
 */
public class TamagoMotion extends Canvas implements Stage, KeyListener {

    public long usedTime;
    public BufferStrategy strategia;
    private SpriteCache spriteCache;
    private ArrayList kupy;
    public Pet pet;
    public boolean papierkamien;
    public int palce;
    public boolean karmienie;
    public boolean glaskanie;
    public PapierKamienNozyce pkn;
    private boolean gameOver;
    

    public TamagoMotion() {
        gameOver = false;
        papierkamien = false;
        glaskanie = false;
        karmienie = false;
        spriteCache = new SpriteCache();
        JFrame okno = new JFrame("TamagoMotion");
        JPanel panel = (JPanel) okno.getContentPane();
        setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
        panel.setPreferredSize(new Dimension(Stage.WIDTH, Stage.HEIGHT));
        panel.setLayout(null);
        panel.add(this);

        okno.setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
        okno.setVisible(true);
        okno.setResizable(false);
        okno.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        createBufferStrategy(2);
        strategia = getBufferStrategy();
        requestFocus();
        addKeyListener(this);
    }

    public void initWorld() {
        kupy = new ArrayList();
        
        pet = new Pet(this);
        pet.setX(400);//(int) (Math.random() * Stage.WIDTH));
        pet.setY(Stage.HEIGHT - 100);

    }

    public void paint() {
        Graphics2D g = (Graphics2D) strategia.getDrawGraphics();
        g.setColor(Color.WHITE);//getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < kupy.size(); i++) {
            Actor m = (Actor) kupy.get(i);
            m.paint(g);
        }
        pet.paint(g);
        //g.drawImage(getSprite("pikachu.png"), pozX, pozY,this);
        paintScore(g);
        paintHelp(g);
        //FPS
//        g.setColor(Color.BLACK);
//        if (usedTime > 0) {
//            g.drawString(String.valueOf(1000 / usedTime) + " fps", 5, Stage.HEIGHT - 50);
//        } else {
//            g.drawString("--- fps", 5, Stage.HEIGHT - 50);
//        }
        strategia.show();
    }
    public void miloscPlus(int i){
        pet.setMilosc(pet.getMilosc() + i);
    }
    public void jedzeniePlus(int i){
        pet.setJedzenie(pet.getJedzenie() + i);
    }
    public BufferedImage loadImage(String sciezka) {
        URL url = null;
        try {
            url = getClass().getClassLoader().getResource(sciezka);
            return ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("Przy otwieraniu " + sciezka + " jako " + url);
            System.out.println("Wystapil blad : " + e.getClass().getName() + " " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public void updateWorld() {
        if(pet.zrobKupe){
            pet.zrobKupe = false;
            Kupa k = new Kupa(this);
            k.setX((int) pet.getX());
            k.setY((int) pet.getY() - 100);
            kupy.add(k);
        }
        
        for (int i = 0; i < kupy.size(); i++) {
            Actor m = (Actor) kupy.get(i);
            if(m.getX()<-30 || m.getY() >830){
                kupy.remove(m);
            }
            else
                m.act();
            
        }
        if(glaskanie){
            pet.setSpriteNames(new String[] {"pikachu_glaskanie.png","pikachu_glaskanie.png"});
        }
        if(karmienie){
            pet.setSpriteNames(new String[] {"pikachu_glaskanie.png","pikachu_karmienie.png"});
            pet.act(false);
        }
        //poruszanie sie zwierzaka
        if(!papierkamien && !glaskanie && !karmienie){
            pet.setSpriteNames(new String[] {"pikachu.png","pikachu.png"});
            pet.act(true);
        }
        if(papierkamien){
            pet.setSpriteNames(new String[] {"pikachu_pkn.png","pikachu_pkn.png"});
            pkn.act();
        }
        if(pet.getJedzenie() <=0 ){
            gameOver("Twój zwierzak umarł z głodu");
        }
        else if(pet.getMilosc() <=0){
            gameOver("Twój zwierzak umarł z samotności");
        }
        else if(pet.getZabawa() <=0){
            gameOver("Twój zwierzak umarł z nudów"); 
        }
        else if(kupy.size()>=5){
            gameOver("Twój zwierzak umarł w smrodzie");
        }
    }

    @Override
    public SpriteCache getSpriteCache() {
        return spriteCache;
    }

    public void game() {
        usedTime = 1000;
        initWorld();
        while (isVisible() && !gameOver) {
            long startTime = System.currentTimeMillis();
            updateWorld();
            paint();
            usedTime = System.currentTimeMillis() - startTime;
            try {
                Thread.sleep(SPEED);
            } catch (InterruptedException ex) {
                Logger.getLogger(TamagoMotion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_G:
                pkn = new PapierKamienNozyce(this);
                papierkamien = true;
                karmienie = false;
                break;
            case KeyEvent.VK_K:
                karmienie = true;
                papierkamien = false;
                break;
        }
        //pet.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //pet.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void paintScore(Graphics2D g) {
        g.setPaint(Color.red);
        g.fillRect(110,20,200,30);
        g.setPaint(Color.blue);
        g.fillRect(110, 20, 2 * pet.getJedzenie(),30);
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.setPaint(Color.green);
        g.drawString("Jedzenie",20,40);
        
        g.setPaint(Color.red);
        g.fillRect(110,60,200,30);
        g.setPaint(Color.blue);
        g.fillRect(110, 60, 2 * pet.getMilosc(),30);
        //g.setFont(new Font("Arial",Font.BOLD,20));
        g.setPaint(Color.green);
        g.drawString("Miłość",20,80);
        
        g.setPaint(Color.red);
        g.fillRect(110,100,200,30);
        g.setPaint(Color.blue);
        g.fillRect(110, 100, 2* pet.getZabawa(),30);
        //g.setFont(new Font("Arial",Font.BOLD,20));
        g.setPaint(Color.green);
        g.drawString("Zabawa",20,120);
        
        if(papierkamien){
            paintPKNScores(g);
        }
    }
    
    private void paintPKNScores(Graphics2D g){
            if(pkn.koniec){
                g.setFont(new Font("Arial",Font.BOLD,36));
                if(pkn.punktyPlayer==3){
                    g.drawString("Wygrałeś",250,300);
                }
                else if(pkn.puntyPet==3){
                    g.drawString("Wygrał zwierzak",250,300);
                }
                if(pkn.odliczanie<-5){
                    papierkamien = false;
                }
            }
            else{
                g.setFont(new Font("Arial",Font.BOLD,36));
                g.drawString("Gracz "+pkn.punktyPlayer+" : "+pkn.puntyPet+" Zwierzak",250,300); 

                if(pkn.odliczanie>0 && pkn.odliczanie<4){
                   g.setPaint(Color.red);
                   g.setFont(new Font("Arial",Font.BOLD,42));
                   g.drawString(Integer.toString(pkn.odliczanie),400,400);
                }
                else if(pkn.odliczanie<=0){
                   g.setPaint(Color.red);
                   g.setFont(new Font("Arial",Font.BOLD,36));
                   g.drawString("Zwierzak pokazuje",250,400);
                   g.setFont(new Font("Arial",Font.BOLD,42));
                   if(pkn.coPokazalZwierzak == 0)
                       g.drawString("KAMIEŃ",300,440);
                   if(pkn.coPokazalZwierzak == 1)
                       g.drawString("PAPIER",300,440);
                   if(pkn.coPokazalZwierzak == 2)
                       g.drawString("NOŻYCE",300,440);                   
                }
            }  
    }
    
    public void paintHelp(Graphics2D g) {
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.setPaint(Color.green);
        g.drawString("Zagraj w PKN - G",600,40); 
    }
    
    private void gameOver(String message){
        JOptionPane.showMessageDialog(null, message, "KONIEC GRY" , JOptionPane.INFORMATION_MESSAGE);
        gameOver = true;
    }
    
    void sprzatnijKupe(int speed){
        for(int i = 0; i < kupy.size(); i++){
            Kupa k = (Kupa) kupy.get(i);
            if(!k.sprzatnieta){
                k.sprzatnij(speed);
                break;
            }
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TamagoMotion inv = new TamagoMotion();
        inv.game();
    }

}
