package interface_adapter;

import interface_adapter.UI.CreateQuizScreen;
import use_case.CreateQuizInteractor;
import use_case.CreateQuizUserDataAccessInterface;

public class CreateQuizUseCaseBuilder {

    public static CreateQuizScreen build(
    ViewManagerModel viewManagerModel,
    CreateQuizUserDataAccessInterface DAO

    ) {
        CreateQuizViewModel viewModel = new CreateQuizViewModel();
        CreateQuizPresenter presenter = new CreateQuizPresenter(viewManagerModel, viewModel);
        CreateQuizInteractor interactor = new CreateQuizInteractor(DAO, presenter);
        CreateQuizController controller = new CreateQuizController(interactor);
        return new CreateQuizScreen(controller, viewModel);
    }

}
