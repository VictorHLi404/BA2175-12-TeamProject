package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args) {
//        new Main();
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addMainMenuView()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
