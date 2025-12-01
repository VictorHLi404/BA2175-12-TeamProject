package app;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main {

    /**
     * Entry point for the application. Initializes the UI by constructing all
     * views and use cases via {@link AppBuilder}, builds the main JFrame,
     * and displays the application window.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addSignupView()
                .addMainMenuView()
                .addViewScoreComponents()
                .addLoginView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addCustomizeQuizUseCase()
                .addPlayQuizUseCase()
                .addMainMenuUseCases()
                .addCompareScoreView()
                .addCompareScoreUseCase()
                .addCreateQuizView()
                .addCreateQuizUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
