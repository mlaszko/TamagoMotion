package tamagomotion;

import java.awt.image.ImageObserver;

public interface Stage extends ImageObserver {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int SPEED = 10;
    public SpriteCache getSpriteCache();
}