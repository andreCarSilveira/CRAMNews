package controle;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
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
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import modelo.Comentario;
import modelo.Noticia;
import modelo.PessoaFisica;
import modelo.Reacao;
import modelo.Resposta;
import modelo.Usuario;
import service.ComentarioService;
import service.NoticiaService;
import service.PessoaFisicaService;
import service.ReacaoService;
import service.RespostaService;
import service.UsuarioService;

@ViewScoped
@ManagedBean
public class NoticiaBean {

	private Noticia noticia;
	private Comentario comentario;
	private Usuario usuario;
	private Reacao reacao;
	
	private List<Resposta> respostas = new ArrayList<Resposta>();
	
	private Long noticiaId;
	
	@EJB
	private NoticiaService noticiaService;
	
	@EJB
	private RespostaService respostaService;
	
	@EJB
	private ComentarioService comentarioService;
	
	@EJB
	private UsuarioService usuarioService;
	
	@EJB
	private ReacaoService reacaoService;
	
	@EJB
	private PessoaFisicaService pessoaFisicaService;
	
	public NoticiaBean() {
		noticia = new Noticia();
		comentario = new Comentario();
		reacao = new Reacao();
	}
	
	@PostConstruct
	public void inicializar() {
		
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
	
	public void curtir() {
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		final HttpSession session = (HttpSession) ec.getSession(true);
		usuario = (Usuario) session.getAttribute("usuario");
		
		Reacao reacaoCurtiu = reacaoService.reagiu(usuario, noticia, true);
		Reacao reacaoDescurtiu = reacaoService.reagiu(usuario, noticia, false);
		
		if (usuario != null) {
			if (reacaoCurtiu == null) {
				if (reacaoDescurtiu != null) {
					reacaoService.remove(reacaoDescurtiu);
					noticia.setDislikes(noticia.getDislikes() - 1);
					noticiaService.merge(noticia);
				}
				reacao.setCurtida(true);
				reacao.setNoticia(noticia);
				reacao.setUsuario(usuario);
				reacaoService.create(reacao);
				noticia.setCurtidas(noticia.getCurtidas() + 1);
				noticiaService.merge(noticia);
			} else {
				reacaoService.remove(reacaoCurtiu);
				noticia.setCurtidas(noticia.getCurtidas() - 1);
				noticiaService.merge(noticia);
			}
		} else {
			addMessage(FacesMessage.SEVERITY_ERROR, "Somente usuários cadastrados podem curtir", null);
		}
		
		reacao = new Reacao();
	}
	
	public void descurtir() {
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		final HttpSession session = (HttpSession) ec.getSession(true);
		usuario = (Usuario) session.getAttribute("usuario");
		
		Reacao reacaoCurtiu = reacaoService.reagiu(usuario, noticia, true);
		Reacao reacaoDescurtiu = reacaoService.reagiu(usuario, noticia, false);
		
		if (usuario != null) {
			if (reacaoDescurtiu == null) {
				if (reacaoCurtiu != null) {
					reacaoService.remove(reacaoCurtiu);
					noticia.setCurtidas(noticia.getCurtidas() - 1);
					noticiaService.merge(noticia);
				}
				reacao.setCurtida(false);
				reacao.setNoticia(noticia);
				reacao.setUsuario(usuario);
				reacaoService.create(reacao);
				noticia.setDislikes(noticia.getDislikes() + 1);
				noticiaService.merge(noticia);
			} else {
				reacaoService.remove(reacaoDescurtiu);
				noticia.setDislikes(noticia.getDislikes() - 1);
				noticiaService.merge(noticia);
			}
		} else {
			addMessage(FacesMessage.SEVERITY_ERROR, "Somente usuários cadastrados podem descurtir", null);
		}
		
		reacao = new Reacao();
	}
	
	public void comentar() {
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		final HttpSession session = (HttpSession) ec.getSession(true);
		usuario = (Usuario) session.getAttribute("usuario");
		
		if (usuario != null) {
			PessoaFisica pessoaFisica = pessoaFisicaService.obterPessoaFisicaPorId(usuario.getPessoaFisica().getId());
			
		    Date dataAtual = new Date();
		    comentario.setDataPublicacao(dataAtual);
		    comentario.setUsuario(pessoaFisica);
		    comentario.setNoticia(noticia);
	
		    comentarioService.create(comentario);
	
		    noticia.getComentarios().add(comentario);
	
		    noticiaService.merge(noticia);
	
		    comentario = new Comentario();
		} else {
			addMessage(FacesMessage.SEVERITY_ERROR, "Somente usuários cadastrados podem comentar", null);
		}
	}
	
	 
	
	public void carregarNoticia(Long id) {
	    noticia = noticiaService.obterNoticiaPorId(id);
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public Long getNoticiaId() {
		return noticiaId;
	}

	public void setNoticiaId(Long noticiaId) {
		this.noticiaId = noticiaId;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Reacao getReacao() {
		return reacao;
	}

	public void setReacao(Reacao reacao) {
		this.reacao = reacao;
	}

}