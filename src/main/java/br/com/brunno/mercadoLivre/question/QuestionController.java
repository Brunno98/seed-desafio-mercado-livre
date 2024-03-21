package br.com.brunno.mercadoLivre.question;

import br.com.brunno.mercadoLivre.login.AuthenticationService;
import br.com.brunno.mercadoLivre.user.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/*
    Contagem de carga cognitiva:
    (Classe sem estado - limite 7)
    - AuthenticationService
    - QuestionNotifier
    - NewQuestionRequest
    - User
    - Question
    - QuestionDetails

    total: 6
 */

@RestController
public class QuestionController {

    private final EntityManager entityManager;
    private final AuthenticationService authenticationService;
    private final QuestionNotifier questionNotifier;

    @Autowired
    public QuestionController(EntityManager entityManager, AuthenticationService authenticationService, QuestionNotifier questionNotifier) {
        this.entityManager = entityManager;
        this.authenticationService = authenticationService;
        this.questionNotifier = questionNotifier;
    }

    @Transactional
    @PostMapping("/question")
    public String createQuestion(@RequestBody @Valid NewQuestionRequest newQuestionRequest, Authentication authentication) {
        User user = authenticationService.getUserFromAuthentication(authentication);
        Question question = newQuestionRequest.toQuestion(entityManager, user);

        entityManager.persist(question);

        questionNotifier.notify(question);

        return question.toString();
    }

    @GetMapping("/question")
    public List<QuestionDetails> listQuestions() {
        List<Question> questions = entityManager.createQuery("SELECT q FROM Question q", Question.class)
                .getResultList();

        return questions.stream().map(QuestionDetails::new).collect(Collectors.toList());
    }
}
