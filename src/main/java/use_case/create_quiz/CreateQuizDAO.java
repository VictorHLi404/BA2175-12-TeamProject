package use_case.create_quiz;

import entities.Quiz;
import persistence.JsonFileDataStore;
import persistence.JsonFileReader;

import java.util.Map;
import java.util.UUID;

public class CreateQuizDAO implements CreateQuizUserDataAccessInterface{

    private final JsonFileDataStore dataStore;
    private final JsonFileReader fileReader;

    public CreateQuizDAO() {
        // Creating an instance of JsonFileDataStore b/c it already includes an implementation of saveQuiz() that can be used
        this.dataStore = new JsonFileDataStore();
        this.fileReader = new JsonFileReader();
    }

    @Override
    public void saveQuiz(Quiz quiz) {

        // saveQuiz() is already implemented as a method in JsonFIleDataStore so we call that
        dataStore.saveQuiz(quiz);
    }

    @Override
    public boolean quizExists(String quizName) {
        // Need something to check if a quiz with the same name already exists

        // Returns a mapping of all the quizzes that exist
        Map<UUID, Quiz> allQuizzes = fileReader.loadAllQuizzes();

        for (Quiz quiz : allQuizzes.values()) {

            // Hard coding to prevent quiz.getQuizName() from being null
            if (quiz.getQuizName() == null) {
                return false;
            }

            if (quiz.getQuizName().strip().equals(quizName)) {
                return true;
            }
        }

        return false;

    }

}
