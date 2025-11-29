package interface_adapter.create_quiz;

import view.CreateQuizView;
import use_case.create_quiz.CreateQuizInteractor;
import use_case.create_quiz.CreateQuizUserDataAccessInterface;

public class CreateQuizUseCaseBuilder {

    public static CreateQuizView build(
    ViewManagerModel viewManagerModel,
    CreateQuizUserDataAccessInterface DAO

    ) {
        CreateQuizViewModel viewModel = new CreateQuizViewModel();
        CreateQuizPresenter presenter = new CreateQuizPresenter(viewManagerModel, viewModel);
        CreateQuizInteractor interactor = new CreateQuizInteractor(DAO, presenter);
        CreateQuizController controller = new CreateQuizController(interactor);
        return new CreateQuizView(controller, viewModel);
    }

}
