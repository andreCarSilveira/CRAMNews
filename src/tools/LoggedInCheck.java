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
	
	//M�todo que ser� acionado sempre antes da fase definida
	public void afterPhase(PhaseEvent event) {
		//Pega o contexto JSF da aplica��o
		final FacesContext context = event.getFacesContext();
		//Pega o contexto externo (Endere�o da aplica��o)
		final ExternalContext ec = context.getExternalContext();
		
		//do endere�o, retorna a url/p�gina atual
		final String viewid = context.getViewRoot().getViewId();
	
		//pega um atributo armazenado na sess�o, no caso o usuario
		final Usuario usuario = (Usuario) ec.getSessionMap().get("usuario");  
		  
		//Verifica se a p�gina atual n�o � a index (pagina permitida sem estar logado)
		//E
		//se o usu�rio � nulo (n�o tem ninguem logado)
		if(!viewid.startsWith("/login") && 
				   usuario == null && !viewid.startsWith("/inicio") && 
						   !viewid.startsWith("/noticia") && !viewid.startsWith("/CadastroUsuario") &&
						   !viewid.startsWith("/colunistaEspecifico") &&  !viewid.startsWith("/noticiaEspecifica") &&
						   !viewid.startsWith("/respostas") &&
				   usuario == null) {
					try {//usu�rio nulo e a p�gina n�o � a index? for�a o sistema a retornar para p�gina index
						ec.redirect(ec.getRequestContextPath() + "/inicio.xhtml");
					} catch (IOException e) {
						e.printStackTrace();
					}
			System.out.println("Usuario n�o esta logado"); //aviso de que n�o esta logado
		}else if(usuario != null && usuario.getPessoaFisica().getTipo() == Tipo.LEITOR &&
				!viewid.startsWith("/noticia") && !viewid.startsWith("/inicio") &&
				!viewid.startsWith("/login") && !viewid.startsWith("/respostas") &&
				!viewid.startsWith("/CadastroUsuario") && !viewid.startsWith("/colunistaEspecifico") &&
				!viewid.startsWith("/noticiaEspecifica") && !viewid.startsWith("/home")) {
			try {//usu�rio nulo e a p�gina n�o � a index? for�a o sistema a retornar para p�gina index
				ec.redirect(ec.getRequestContextPath() + "/inicio.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	//M�todo que ser� acionado sempre depois da fase definida
	public void beforePhase(PhaseEvent event) {} //n�o necess�rio no noso caso

}