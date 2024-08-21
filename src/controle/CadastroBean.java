package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;


import service.UsuarioService;
import modelo.Tipo;
import modelo.Usuario;
import modelo.PessoaFisica;


@ViewScoped
@ManagedBean
public class CadastroBean {

	private String filtro;
	
	private Usuario usuario;
	
	@EJB
    private UsuarioService usuarioService;

	private PessoaFisica pessoaFisica = new PessoaFisica();
	private Tipo tipo;
	private List<PessoaFisica> pessoasFisicas = new ArrayList<PessoaFisica>();

    public CadastroBean() {
    	pessoaFisica = new PessoaFisica();
    }

	@PostConstruct 
	private void inicializar() {
		
	}
	

	public void gravar() {
		/*if(usuario.getId() != null) {
			usuarioService.merge(usuario);
			FacesContext.getCurrentInstance().
			addMessage("msg1", new FacesMessage("Usuário atualizado com sucesso!")); 
		} else {
			usuarioService.create(usuario);
			FacesContext.getCurrentInstance().
			addMessage("msg1", new FacesMessage("Usuário gravado com sucesso!")); 
		}*/
		pessoaFisica.setTipo(tipo.LEITOR);
		
		usuarioService.create(pessoaFisica);
		FacesContext.getCurrentInstance().
		addMessage("msg1", new FacesMessage("Usuário gravado com sucesso!")); 
		
		pessoaFisica = new PessoaFisica();
		usuario = new Usuario();
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}


	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public List<PessoaFisica> getPessoasFisicas() {
		return pessoasFisicas;
	}

	public void setPessoasFisicas(List<PessoaFisica> pessoasFisicas) {
		this.pessoasFisicas = pessoasFisicas;
	}

	

}
