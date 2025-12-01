package use_case.create_quiz;

import use_case.play.PlayQuizOutputData;

import java.util.List;

public class QuestionInputData {

        private final String category;
        private final String difficulty;
        private final String format;
        private final String question;
        private final List<String> choices;
        private final String correctChoice;

        public QuestionInputData (String category, String difficulty, String format, String question,
                                  List<String> choices, String correctChoice) {

            this.category = category;
            this.difficulty = difficulty;
            this.format = format;
            this.question = question;
            this.choices = choices;
            this.correctChoice = correctChoice;

        }

        public String getCategory (){
            return category;
        }

        public String getDifficulty () { return difficulty; }

        public String getFormat (){
            return format;
        }

        public String getQuestion (){
            return question;
        }

        public List<String> getChoices (){
            return choices;
        }

        public String getCorrectChoice (){
            return correctChoice;
        }

}

