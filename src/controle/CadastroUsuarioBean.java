package controle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;

import org.primefaces.model.file.UploadedFile;
import org.primefaces.util.IOUtils;

import modelo.PessoaFisica;
import modelo.Tipo;
import modelo.Usuario;
import service.PessoaFisicaService;
import service.PessoaService;
import service.UsuService;
import service.UsuarioService;


@ViewScoped
@ManagedBean
public class CadastroUsuarioBean {

	private UploadedFile uploadedFile;
	private String filtro;
	private Boolean editando = false;
	private Boolean gravar = true; 
	
	private Usuario usuario = new Usuario();
	private Tipo tipo;
	
	@EJB
	private UsuarioService usuarioService;
	
	@EJB
	private UsuService usuService;
	
	@EJB
    private PessoaFisicaService pessoaFisicaService;
	
	@EJB
	private PessoaService pessoaService;

	private PessoaFisica pessoaFisica = new PessoaFisica();
	private List<PessoaFisica> pessoaFisicas = new ArrayList<PessoaFisica>();


    public CadastroUsuarioBean() {
    	pessoaFisica = new PessoaFisica();
        pessoaFisica.setUsuario(new Usuario());
    }
    
	public void pesquisar() {
		pessoaFisicas = pessoaFisicaService.listarPessoaFisicaPorNome(filtro);
	}
	
	@PostConstruct 
	private void inicializar() {
		listarPessoasFisicas();
	}
	
	public void carregarPessoa(PessoaFisica psF) {
		pessoaFisica = psF;
		editando = true;
		gravar = false;
	}

	public void excluir(PessoaFisica psF) {
		pessoaFisicaService.remove(psF);
		listarPessoasFisicas();
		FacesContext.getCurrentInstance().
		addMessage("msg1", new FacesMessage("Usuário excluído com sucesso!"));
	}

	
	public void listarPessoasFisicas() {
		pessoaFisicas = pessoaFisicaService.listarPessoaFisicas(); 
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

	
	@Transactional
	public void gravar() {
		try {
			if (usuService.listarUsuarioPorLogin(usuario.getLogin()) == true) {
				usuario.setPessoaFisica(pessoaFisica);
				pessoaFisica.setFoto(IOUtils.toByteArray(uploadedFile.getInputStream()));
				pessoaFisica.setTipo(Tipo.LEITOR);	
				pessoaFisica.setUsuario(usuario);
				pessoaService.PersitPessoaComUsuario(pessoaFisica);
				 
				FacesContext facesContext = FacesContext.getCurrentInstance();
		        ExternalContext externalContext = facesContext.getExternalContext();
		        String contextPath = externalContext.getRequestContextPath();
		        
		        externalContext.redirect(contextPath + "/login.xhtml");
			}
			
		}catch(Exception ex) {
			System.out.println(ex);
		}
		
		
	}




	public String getFiltro() {
		return filtro;
	}


	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}


	public Boolean getEditando() {
		return editando;
	}


	public void setEditando(Boolean editando) {
		this.editando = editando;
	}


	public Boolean getGravar() {
		return gravar;
	}


	public void setGravar(Boolean gravar) {
		this.gravar = gravar;
	}


	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}


	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}


	public List<PessoaFisica> getPessoaFisicas() {
		return pessoaFisicas;
	}


	public void setPessoaFisicas(List<PessoaFisica> pessoaFisicas) {
		this.pessoaFisicas = pessoaFisicas;
	}


	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}


	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Tipo[] getUsuariosDisponiveis() {
		return Tipo.values();
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	
}