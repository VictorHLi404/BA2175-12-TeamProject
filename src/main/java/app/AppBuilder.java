package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.main_menu.MainMenuViewModel;
import view.MainMenuView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addMainMenuView() {
        MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
        MainMenuView mainMenuView = new MainMenuView(mainMenuViewModel);
        cardPanel.add(mainMenuView, mainMenuView.getViewName());
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Color.GRAY);
        application.setContentPane(background);
        application.add(cardPanel);

        viewManagerModel.firePropertyChange();
        return application;
    }

}
