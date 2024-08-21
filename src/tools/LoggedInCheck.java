package tools;


import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import modelo.Tipo;
import modelo.Usuario;


@SuppressWarnings("serial")
public class LoggedInCheck implements PhaseListener {
	
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW; //Define a qual a fase do ciclo de vida do JSF esse listener vai agir
	}
	
	//Método que será acionado sempre antes da fase definida
	public void afterPhase(PhaseEvent event) {
		//Pega o contexto JSF da aplicação
		final FacesContext context = event.getFacesContext();
		//Pega o contexto externo (Endereço da aplicação)
		final ExternalContext ec = context.getExternalContext();
		
		//do endereço, retorna a url/página atual
		final String viewid = context.getViewRoot().getViewId();
	
		//pega um atributo armazenado na sessão, no caso o usuario
		final Usuario usuario = (Usuario) ec.getSessionMap().get("usuario");  
		  
		//Verifica se a página atual não é a index (pagina permitida sem estar logado)
		//E
		//se o usuário é nulo (não tem ninguem logado)
		if(!viewid.startsWith("/login") && 
				   usuario == null && !viewid.startsWith("/inicio") && 
						   !viewid.startsWith("/noticia") && !viewid.startsWith("/CadastroUsuario") &&
						   !viewid.startsWith("/colunistaEspecifico") &&  !viewid.startsWith("/noticiaEspecifica") &&
						   !viewid.startsWith("/respostas") &&
				   usuario == null) {
					try {//usuário nulo e a página não é a index? força o sistema a retornar para página index
						ec.redirect(ec.getRequestContextPath() + "/inicio.xhtml");
					} catch (IOException e) {
						e.printStackTrace();
					}
			System.out.println("Usuario não esta logado"); //aviso de que não esta logado
		}else if(usuario != null && usuario.getPessoaFisica().getTipo() == Tipo.LEITOR &&
				!viewid.startsWith("/noticia") && !viewid.startsWith("/inicio") &&
				!viewid.startsWith("/login") && !viewid.startsWith("/respostas") &&
				!viewid.startsWith("/CadastroUsuario") && !viewid.startsWith("/colunistaEspecifico") &&
				!viewid.startsWith("/noticiaEspecifica") && !viewid.startsWith("/home")) {
			try {//usuário nulo e a página não é a index? força o sistema a retornar para página index
				ec.redirect(ec.getRequestContextPath() + "/inicio.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	//Método que será acionado sempre depois da fase definida
	public void beforePhase(PhaseEvent event) {} //não necessário no noso caso

}