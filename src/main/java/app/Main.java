package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addSignupView()
                .addMainMenuView()
                .addViewScoreComponents()
                .addLoginView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addPlayQuizView()
                .addPlayQuizUseCase()
                .addCustomizeQuizUseCase()
                .addMainMenuUseCases()
                .addCompareScoreView()
                .addCompareScoreUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
