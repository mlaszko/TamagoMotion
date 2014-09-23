package tamagomotion;

/**
 * **********************************************************************\
 * Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved. * Leap Motion
 * proprietary and confidential. Not for distribution. * Use subject to the
 * terms of the Leap Motion SDK Agreement available at*
 * https://developer.leapmotion.com/sdk_agreement, or another agreement *
 * between Leap Motion and you, your company or other organization. *
 * \**********************************************************************
 */
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.Math.abs;
import javax.swing.JOptionPane;

class LeapListener extends Listener {

    int f; //licznik klatek
    TamagoMotion game;

    public LeapListener(TamagoMotion g) {
        f = 0;
        game = g;
    }

    public LeapListener() {
        f = 0;
    }

    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        System.out.println("Connected");

    }

    public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();

        if (!frame.hands().isEmpty()) {

            // Get the first hand
            Hand hand = frame.hands().get(0);
            //liczba palców do papier kamien nozyce
            game.palce = hand.fingers().count();

            //GŁASKANIE
            if (!game.papierkamien) {
                f++;
                if (f % 5 == 0) {
                    //KARMIENIE
                    if (!controller.frame(15).hands().isEmpty()) {
                        Vector normal = hand.palmNormal();
                        Vector normal1 = controller.frame(15).hands().get(0).palmNormal();
                        if (normal.getY() > 0 && abs(normal.getX()) < 0.5
                                && abs(normal.getZ()) < 0.5 && normal1.getY() > 0
                                && abs(normal1.getX()) < 0.5 && abs(normal1.getZ()) < 0.5) {
                           // System.out.println("karmienie");
                            game.jedzeniePlus(1);
                            game.karmienie = true;
                        }
                    }
                    //System.out.println("NORMALNA REKI "+vec1.getX() +  " " + vec1.getY() +  " " + vec1.getZ() +  " ");
                    //GŁASKANIE
                    Vector translation = hand.translation(controller.frame(15));
                    //System.out.println(vec.getX() +  " " + vec.getY() +  " " + c +  " ");
                    if (translation.getX() >= 40 && translation.getY() <= 40 && translation.getY() <= 40) {
                        //System.out.println("głaskanie");
                        game.miloscPlus(1);
                        game.glaskanie = true;

                    } else if (f % 150 == 0) {
                        // game.glaskanie = false;
                    }

                }

            }

        } else {//nie ma rak
            game.glaskanie = false;
            game.karmienie = false;
        }

        if (!game.papierkamien && !game.glaskanie && !game.karmienie) {
            GestureList gestures = frame.gestures();
            for (int i = 0; i < gestures.count(); i++) {
                Gesture gesture = gestures.get(i);

                switch (gesture.type()) {
                    case TYPE_SWIPE:
                        SwipeGesture swipe = new SwipeGesture(gesture);
    //                        System.out.println("Swipe id: " + swipe.id()
                        //                                   + ", " + swipe.state()
                        //                                   + ", position: " + swipe.position()
                        //                                   + ", direction: " + swipe.direction()
                        //                                   + ", speed: " + swipe.speed());
                        if (swipe.state() == Gesture.State.STATE_STOP
                                && abs(swipe.direction().getY()) <= 0.4
                                && abs(swipe.direction().getZ()) <= 0.4 //&&
                                //abs(hand.palmNormal().getY()) <= 0.4 &&
                                //abs(hand.palmNormal().getZ()) <= 0.4
                                ) {

                            if (swipe.direction().getX() > 0) {
                                game.sprzatnijKupe(8);
                            } else {
                                game.sprzatnijKupe(-8);
                            }

                        }
                        break;
                }
            }
        }
    }
}

class LeapControl {

    public static void main(String[] args) {//throws SlickException {

        TamagoMotion g = new TamagoMotion();
        // Create a sample listener and controller
        LeapListener listener = new LeapListener(g);
        //  new Avoid("Avoid Game", true, false));
        Controller controller = new Controller();
        // Have the sample listener receive events from the controller
        controller.addListener(listener);
        g.game();
        // Remove the sample listener when done
        controller.removeListener(listener);

        System.exit(0);
    }
}
