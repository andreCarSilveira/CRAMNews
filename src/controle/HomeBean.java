package controle;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import modelo.PessoaFisica;
import modelo.Tipo;
import modelo.Usuario;
import service.PessoaService;

@ViewScoped
@Named
public class HomeBean implements Serializable {
	
	@EJB()
	private PessoaService pessoaService;
	
	private List<PessoaFisica> pessoas = new ArrayList<PessoaFisica>();
	private String filtro="";
	
	private Usuario usuario = new Usuario();
	
	
	public List<PessoaFisica> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaFisica> pessoas) {
		this.pessoas = pessoas;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Boolean verificaTipo() {
		Boolean resultado;
		if(usuario.getPessoaFisica().getTipo() == Tipo.COLUNISTA) {
			resultado = true;
		} else {
			resultado = false;
		}
		return resultado;
	}
	
	
	@PostConstruct
	public void init(){
		pessoas.addAll(pessoaService.listAll());
		
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		final HttpSession session = (HttpSession) ec.getSession(true);
		usuario = (Usuario) session.getAttribute("usuario");
				
		//PessoaFisica pessoa = (PessoaFisica) session.getAttribute("pessoa");
		//usuario = pessoa.getUsuario();
		
	}
	
	
	public void filtrar(){
		pessoas.clear();
		pessoas.addAll(pessoaService.filtrarPorNome(getFiltro()));
	}
	
	public void sair(){

		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		final HttpSession session = (HttpSession) ec.getSession(true);
		session.invalidate();		
		
		try {
			ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
