package controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.file.UploadedFile;
import org.primefaces.util.IOUtils;

import modelo.Noticia;
import modelo.PessoaFisica;
import modelo.Tema;
import modelo.Usuario;
import service.NoticiaService;
import service.UsuarioService;

@ViewScoped
@ManagedBean
public class CadastroNoticiaBean {
	
	private Tema tema;
	private Boolean editando = false;
	private Boolean gravar = true;
	
	private PessoaFisica pessoaFisica;
	
	private Usuario usuario = new Usuario();
	private Long noticiaId;
	
	private UploadedFile uploadedFile;
	
	@EJB
	private UsuarioService usuarioService;
	
	
	@EJB
	private NoticiaService noticiaService;
	
	private Noticia noticia = new Noticia();
	private List<Noticia> noticias = new ArrayList<Noticia>();
	
	private boolean editPhotoEnabled = false;
    // Other properties and methods

    public boolean isEditPhotoEnabled() {
        return editPhotoEnabled;
    }

    public void setEditPhotoEnabled(boolean editPhotoEnabled) {
        this.editPhotoEnabled = editPhotoEnabled;
    }

    public void enablePhotoEdit() {
        this.editPhotoEnabled = true;
    }
	
	public CadastroNoticiaBean() {
		noticia = new Noticia();	
		
	}
	
	public Tema[] getTemasDisponiveis() {
		return Tema.values();
	}
	
	@PostConstruct
	public void inicializar() {
		listarNoticias();
	}
	
	public void carNoticia(Noticia ntc) {
		noticia = ntc;
		editando = true;
		gravar = false;
	}
	
	public void excluir(Noticia ntc) {
		noticiaService.remove(ntc);
		listarNoticias();
		FacesContext.getCurrentInstance().addMessage("msg1", new FacesMessage("Noticia excluida com sucesso"));
	}
	
	public void carregarNoticia(Long id) {
	    noticia = noticiaService.obterNoticiaPorId(id);
		/*
		 * try { uploadedFile.getInputStream().read(noticia.getFoto()); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
	    editando = true;
		gravar = false;
	}
	
	
	public void listarNoticias() {
		noticias = noticiaService.listarItens();
	}
	
	public void gravar() {
	    try {
	        final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
	        final HttpSession session = (HttpSession) ec.getSession(true);
	        usuario = (Usuario) session.getAttribute("usuario");
	        
	        PessoaFisica pessoaFisica1 = usuarioService.obtemPessoaFisicaPorUsuario(usuario.getId());

	        
	        if(editando) {
	            FacesContext facesContext = FacesContext.getCurrentInstance();
		        ExternalContext externalContext = facesContext.getExternalContext();
		        String contextPath = externalContext.getRequestContextPath();

		       
		        try {
		        	 noticia.setFoto(IOUtils.toByteArray(uploadedFile.getInputStream()));
			         noticia.setDataEdicao(new Date());
			         noticiaService.merge(noticia);
			         externalContext.redirect(contextPath + "/gerenciamentoNoticias.xhtml");
			         noticia = new Noticia();
			         FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Notícia modificada com sucesso"));
			         
			       
				} catch (IOException e) {
				
					e.printStackTrace();
				}
	            
	            
	           
	         
	            
	        } else {
	        	
	            noticia.setDislikes(0L);
	            noticia.setCurtidas(0L);
	            
	            noticia.setColunista(pessoaFisica1);

	            noticia.setFoto(IOUtils.toByteArray(uploadedFile.getInputStream()));
	            noticia.setDataPublicacao(new Date());
	            noticia.setDataEdicao(new Date());
	            noticiaService.create(noticia);
	            
	            // Resetando noticia para limpar os campos
	            noticia = new Noticia();
	            FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Notícia gravada com sucesso"));
	            // Adicione aqui o código para resetar outros campos se necessário
	        }
	        
	    } catch (Exception ex) {
	        System.out.println(ex);
	    }
	}

	
	public Boolean getEditando() {
		return editando;
	}
	
	

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
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

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public List<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getNoticiaId() {
		return noticiaId;
	}

	public void setNoticiaId(Long noticiaId) {
		this.noticiaId = noticiaId;
	}

	
	
	
	
}