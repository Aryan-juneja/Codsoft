import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    private String question;
    private List<String> options;
    private int correctOption;

    public QuizQuestion(String question, List<String> options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

public class QuizSystem {
    private List<QuizQuestion> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;

    public QuizSystem() {
        questions = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;
        timer = new Timer();
    }

    public void addQuestion(QuizQuestion question) {
        questions.add(question);
    }

    public void startQuiz() {
        if (questions.isEmpty()) {
            System.out.println("No questions available!");
            return;
        }

        displayNextQuestion();
    }

    private void displayNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
            System.out.println("\nQuestion: " + currentQuestion.getQuestion());
            List<String> options = currentQuestion.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            startTimer();
            handleAnswerSubmission(currentQuestion);
        } else {
            endQuiz();
        }
    }

    private void startTimer() {
        int timeLimitInSeconds = 10; // Change time limit here (in seconds)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up!");
                handleAnswerSubmission(null);
            }
        }, timeLimitInSeconds * 1000);
    }

    private void handleAnswerSubmission(QuizQuestion question) {
        timer.cancel(); // Cancel the timer task
        Scanner scanner = new Scanner(System.in);
        if (question != null) {
            System.out.print("\nEnter your choice (1-" + question.getOptions().size() + "): ");
            int userChoice = scanner.nextInt();
            if (userChoice == question.getCorrectOption()) {
                System.out.println("Correct answer!");
                score++;
            } else {
                System.out.println("Incorrect answer!");
            }
        } else {
            System.out.println("Answer not submitted in time.");
        }

        currentQuestionIndex++;
        displayNextQuestion();
    }

    private void endQuiz() {
        System.out.println("\nQuiz ended!");
        System.out.println("Your score: " + score + " out of " + questions.size());
    }

    public static void main(String[] args) {
        QuizSystem quizSystem = new QuizSystem();

        List<String> options1 = new ArrayList<>();
        options1.add("Option A");
        options1.add("Option B");
        options1.add("Option C");
        options1.add("Option D");

        QuizQuestion question1 = new QuizQuestion("What is 2 + 2?", options1, 3);

        List<String> options2 = new ArrayList<>();
        options2.add("Option A");
        options2.add("Option B");
        options2.add("Option C");
        options2.add("Option D");

        QuizQuestion question2 = new QuizQuestion("What is the capital of France?", options2, 4);

        quizSystem.addQuestion(question1);
        quizSystem.addQuestion(question2);

        quizSystem.startQuiz();
    }
}

