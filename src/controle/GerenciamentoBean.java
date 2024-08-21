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

import modelo.Comentario;
import modelo.Noticia;
import modelo.PessoaFisica;
import modelo.Tema;
import modelo.Usuario;
import service.ComentarioService;
import service.NoticiaService;
import service.ReacaoService;
import service.RespostaService;
import service.UsuarioService;


@ViewScoped
@ManagedBean
public class GerenciamentoBean {

	private UploadedFile uploadedFile;

	private Usuario usuario;
	
	private Boolean editando = false;
	private Boolean gravar = true;
	
	private List<Noticia> noticias = new ArrayList<Noticia>();
	
	private Noticia noticia;
	
	@EJB
	private RespostaService respostaService;
	
	@EJB
	private ComentarioService comentarioService;
	
	@EJB
	private ReacaoService reacaoService;
	
	@EJB
	private UsuarioService usuarioService;
	
	@EJB
	private NoticiaService noticiaService;
	
	public GerenciamentoBean() {
		noticia = new Noticia();
	}
	
	@PostConstruct 
	private void inicializar() {
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
        final HttpSession session = (HttpSession) ec.getSession(true);
        usuario = (Usuario) session.getAttribute("usuario");
        
        PessoaFisica pessoaFisica1 = usuarioService.obtemPessoaFisicaPorUsuario(usuario.getId());
        
		noticias = noticiaService.listarNoticiaPorColunista(pessoaFisica1.getId());
	}
		
	public void carregarNoticia(Noticia not) {
		noticia = not;
		editando = true;
		gravar = false;
	}
	
	public Tema[] getTemasDisponiveis() {
		return Tema.values();
	}
	
	public void excluir(Noticia not) {
		//List<Comentario> comentarios = comentarioService.listarComentariosPorNoticia(not.getId());
		//for (Comentario comentario : comentarios) {
		//	comentario.excluirRespostas();
		//	respostaService.deletarRespostasPorComentario(comentario.getId());
		//}
		
		//not.excluirComentarios();
		//noticiaService.merge(not);
		//comentarioService.deletarComentariosPorNoticia(not.getId());
		
		
		
		 	FacesContext facesContext = FacesContext.getCurrentInstance();
	        ExternalContext externalContext = facesContext.getExternalContext();
	        String contextPath = externalContext.getRequestContextPath();

	        // Redireciona para a mesma página, efetivamente recarregando-a
	        try {
	        	reacaoService.deletarReacoesPorNoticia(not.getId());
				
	    		noticiaService.remove(not);
				externalContext.redirect(contextPath + "/gerenciamentoNoticias.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void cadastro() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("cadastroNoticias.xhtml");
    }
	
	private void atualizarLista() {
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		final HttpSession session = (HttpSession) ec.getSession(true);
		usuario = (Usuario) session.getAttribute("usuario");
		
		PessoaFisica pessoaFisica = usuarioService.obtemPorId(usuario.getId());
		
		noticias = noticiaService.listarNoticiaPorColunista(pessoaFisica.getId());
		
	}

	public void gravar() {
	
			try {
				final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
				final HttpSession session = (HttpSession) ec.getSession(true);
				usuario = (Usuario) session.getAttribute("usuario");
				
				PessoaFisica pessoaFisica = usuarioService.obtemPorId(usuario.getId());
				
				noticia.setDislikes(0L);
				noticia.setCurtidas(0L);
				noticia.setColunista(pessoaFisica);
				noticia.setDataEdicao(new Date());
				noticia.setFoto(IOUtils.toByteArray(uploadedFile.getInputStream()));
				noticiaService.merge(noticia);
				noticia = new Noticia();
			} catch (Exception ex) {
				System.out.println(ex);
			}	
	
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

	public List<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	

	
}
