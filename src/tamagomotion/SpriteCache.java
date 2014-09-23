package tamagomotion;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SpriteCache {
    public HashMap sprites;
    public SpriteCache() {
        sprites = new HashMap();
    }
    private BufferedImage loadImage(String sciezka) {
        URL url=null;
        try {
            url = getClass().getClassLoader().getResource(sciezka);
            return ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("Przy otwieraniu " + sciezka +" jako " + url);
            System.out.println("Wystapil blad : "+e.getClass().getName()+" "+e.getMessage());
            System.exit(0);
            return null;
        }
    }
    
    public BufferedImage getSprite(String sciezka) {
        BufferedImage img = (BufferedImage)sprites.get(sciezka);
        if (img == null) {
            img = loadImage("img/"+sciezka);
            sprites.put(sciezka,img);
        }
        return img;
    }
}