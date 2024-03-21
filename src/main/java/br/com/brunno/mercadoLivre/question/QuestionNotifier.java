package br.com.brunno.mercadoLivre.question;

import br.com.brunno.mercadoLivre.product.Product;
import org.springframework.stereotype.Service;

@Service
public class QuestionNotifier {

    public void notify(Question question) {
        String title = question.getTitle();
        Product product = question.getProduct();

        System.out.println(product.getOwnerLogin() + " seu produto recebeu a seguinte pergunta: '"+title+"'. " +
                "Para vizualiza-la acesse a pagina do produto no seguinte endereço: /product/"+product.getId());
    }

}
