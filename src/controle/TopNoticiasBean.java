package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import modelo.Noticia;
import modelo.PessoaFisica;
import modelo.Usuario;
import service.NoticiaService;
import service.UsuarioService;

@ViewScoped
@ManagedBean
public class TopNoticiasBean {

private List<Noticia> noticias = new ArrayList<Noticia>();
	
	private Noticia noticiaAtual;
	
	@EJB
	private NoticiaService noticiaService;
	
	@EJB
	private UsuarioService usuarioService;
	
	public TopNoticiasBean() {
		
	}
	
	@PostConstruct
	private void inicializar() {
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
        final HttpSession session = (HttpSession) ec.getSession(true);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        PessoaFisica pessoaFisica1 = usuarioService.obtemPessoaFisicaPorUsuario(usuario.getId());
        
		noticias = noticiaService.listarMaisComentadas(pessoaFisica1.getId());
	}
	
	public void carregarNoticia(Long id) {
		noticiaAtual = noticiaService.obterNoticiaPorId(id);
	}

	public List<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public Noticia getNoticiaAtual() {
		return noticiaAtual;
	}

	public void setNoticiaAtual(Noticia noticiaAtual) {
		this.noticiaAtual = noticiaAtual;
	}
	

}
