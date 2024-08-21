package controle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import modelo.PessoaFisica;
import modelo.Tipo;
import modelo.Usuario;
import service.PessoaService;
import java.io.Serializable;


@ViewScoped
@Named
public class ValidarBean implements Serializable {
	
	@EJB()
	private PessoaService pessoaService;
	
	private String loginUser = "";
	private String senhaUser = "";	
	
	public PessoaService getPessoaService() {
		return pessoaService;
	}

	public void setPessoaService(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getSenhaUser() {
		return senhaUser;
	}

	public void setSenhaUser(String senhaUser) {
		this.senhaUser = senhaUser;
	}

	//metodo para criar as pessoas no banco de dados
	@PostConstruct
	public void init(){
		if(pessoaService.listAll().isEmpty()){
			
			Usuario user1 = new Usuario();
			user1.setLogin("roberto");
			user1.setSenha("java>php");			
			PessoaFisica pessoa1 = new PessoaFisica();
			pessoa1.setNome("Roberto Coutinho");
			pessoa1.setEmail("andre@gmail.com");
			pessoa1.setTipo(Tipo.LEITOR);
			pessoa1.setUsuario(user1);			
			pessoaService.PersitPessoaComUsuario(pessoa1);
			
			
			
			Usuario user2 = new Usuario();
			user2.setLogin("aluno");
			user2.setSenha("amojava");			
			PessoaFisica pessoa2 = new PessoaFisica();
			pessoa2.setNome("Aluno Exemplo");
			pessoa2.setEmail("andre@gmail.com");
			pessoa2.setTipo(Tipo.LEITOR);
			pessoa2.setUsuario(user2);			
			pessoaService.PersitPessoaComUsuario(pessoa2);
		
			
			
			Usuario user3 = new Usuario();
			user3.setLogin("teste");
			user3.setSenha("teste");			
			PessoaFisica pessoa3 = new PessoaFisica();
			pessoa3.setNome("Teste de Login");
			pessoa3.setEmail("andre@gmail.com");
			pessoa3.setTipo(Tipo.LEITOR);
			pessoa3.setUsuario(user3);			
			pessoaService.PersitPessoaComUsuario(pessoa3);
		}
		
	}
	
	
	public void logar(ActionEvent actionEvent) {
		validaUsuario("/inicio.xhtml");
    }
	
	//metodo para validar o usuário e coloca-lo na sessão(session)
	private void validaUsuario(final String destino) {
		try {
			//metodo que vai no service e verifica se o login e a senha digitados são iguais aos do banco
    		final PessoaFisica pessoa = pessoaService.validarUsuario(loginUser, senhaUser);
    		System.out.println("Usuario logado: " + pessoa.getUsuario().getLogin());

    		//pega a sessão	
    		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
    		final HttpSession session = (HttpSession) ec.getSession(true);
    		//'seta' um atributo na sessão, no caso, o Usuário do objeto pessoa
			session.setAttribute("usuario", pessoa.getUsuario());
			//session.setAttribute("pessoa", pessoa);
			
			//redireciona para a página de destino
			ec.redirect(ec.getRequestContextPath() + destino); 
		} catch (Exception e) {
			//caso o usuário não exista ou senha incorreta, cai no exception e exibe a mensagem
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensagem:", e.getMessage()));
		}
	}
	
}
