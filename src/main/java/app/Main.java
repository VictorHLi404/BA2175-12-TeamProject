package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addSignupView()
                .addViewScoreComponents()
                .addMainMenuView()
                .addLoginView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addMainMenuUseCases()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
