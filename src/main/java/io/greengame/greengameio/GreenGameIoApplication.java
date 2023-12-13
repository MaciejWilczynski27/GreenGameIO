package io.greengame.greengameio;

import io.greengame.greengameio.entity.*;

import io.greengame.greengameio.repository.*;
import io.greengame.greengameio.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class GreenGameIoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenGameIoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, QuizRepository quizRepository, QuestionRepository questionRepository,
										AnswerRepository answerRepository, HiScoreRepository highScoreRepository) {
		return args -> {
			//password is password
				User admin = userRepository.save(new User(1L, "admin", "$2b$12$6J4h6z.Er73Ud7zWhUT4yueCCFl2xCLkUZGHi8JtJYYwxp3NHtbBK", "admin@email.com", UserType.ADMINISTRATOR));
				User user = userRepository.save(new User(2L, "user", "$2b$12$6J4h6z.Er73Ud7zWhUT4yueCCFl2xCLkUZGHi8JtJYYwxp3NHtbBK", "user@email.com", UserType.USER));

				Answer answerNo1ForQ1 = new Answer("Prawda");
                Answer answerNo2ForQ1 = new Answer("Fałsz");
                Question questionNo1 = new Question("Recykling papieru jest korzystny dla środowiska, ponieważ pomaga ograniczyć wycinkę drzew.", 1, List.of(answerNo1ForQ1, answerNo2ForQ1), answerNo1ForQ1);

               	Answer answerNo1ForQ2 = new Answer("Transport");
                Answer answerNo2ForQ2 = new Answer("Energia odnawialna");
                Answer answerNo3ForQ2 = new Answer("Górnictwo");
                Answer answerNo4ForQ2 = new Answer("Przemysł spożywczy");
                Question questionNo2 = new Question("Jakie jest główne źródło emisji dwutlenku węgla związane z działalnością człowieka?", 2, List.of(answerNo1ForQ2, answerNo2ForQ2, answerNo3ForQ2, answerNo4ForQ2), answerNo1ForQ2);

               	Answer answerNo1ForQ3 = new Answer("Prawda");
                Answer answerNo2ForQ3 = new Answer("Fałsz");
                Question questionNo3 = new Question("Energia słoneczna jest odnawialnym źródłem energii.", 3, List.of(answerNo1ForQ3, answerNo2ForQ3), answerNo1ForQ3);

				Answer answerNo1ForQ4 = new Answer("Emisje gazów cieplarnianych i wylesianie");
				Answer answerNo2ForQ4 = new Answer("Nadmierna konsumpcja czekolady");
				Question questionNo4 = new Question("Jakie są główne przyczyny zmian klimatu?", 4, List.of(answerNo1ForQ4, answerNo2ForQ4), answerNo1ForQ4);

				Answer answerNo1ForQ5 = new Answer("Hodowanie roślin w domu");
				Answer answerNo2ForQ5 = new Answer("Recykling i używanie przedmiotów wielokrotnego użytku");
				Question questionNo5 = new Question("W jaki sposób możemy ograniczyć zużycie plastiku?", 5, List.of(answerNo1ForQ5, answerNo2ForQ5), answerNo2ForQ5);


                Quiz quiz = new Quiz("QuizTestowy", 3, admin, LocalDateTime.now().minusDays(1), List.of(questionNo1, questionNo2, questionNo3));
				Quiz quiz2 = new Quiz("QuizTestowy2", 2, admin, LocalDateTime.now().plusDays(30), List.of(questionNo4, questionNo5));

                answerRepository.saveAll(List.of(answerNo1ForQ1, answerNo2ForQ1,
                		answerNo1ForQ2, answerNo2ForQ2, answerNo3ForQ2, answerNo4ForQ2,
               			answerNo1ForQ3, answerNo2ForQ3, answerNo1ForQ4, answerNo2ForQ4, answerNo1ForQ5, answerNo2ForQ5));
               	questionRepository.saveAll(List.of(questionNo1, questionNo2, questionNo3, questionNo4, questionNo5));
                quizRepository.save(quiz);
				quizRepository.save(quiz2);

                HiScore hiScore = new HiScore(quiz, user, 1);
                HiScore hiScore2 = new HiScore(quiz2, user, 2);
                highScoreRepository.save(hiScore);
                highScoreRepository.save(hiScore2);

		};
	}


}
