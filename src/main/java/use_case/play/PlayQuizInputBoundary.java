package use_case.play;

import entities.Question;

import java.util.List;

public interface PlayQuizInputBoundary {
    void execute(PlayQuizInputData inputData);

    void setQuestions(List<Question> questions);

    void loadNextQuestion();
}
