package controle;

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

import modelo.Comentario;
import modelo.PessoaFisica;
import modelo.Resposta;
import modelo.Usuario;
import service.ComentarioService;
import service.PessoaFisicaService;
import service.RespostaService;
import service.UsuarioService;

@ViewScoped
@ManagedBean
public class RespostasBean {
	
	private Long comentarioId;
	private Comentario comentario;
	private Resposta resposta;
	private Usuario usuario;
	private List<Resposta> respostas = new ArrayList<Resposta>();
	
	@EJB
	private UsuarioService usuarioService;
	
	@EJB
	private RespostaService respostaService;
	
	@EJB
	private ComentarioService comentarioService;
	
	@EJB
	private PessoaFisicaService pessoaFisicaService;

	public RespostasBean() {
		comentario = new Comentario();
		resposta = new Resposta();
	}
	
	@PostConstruct
	public void inicializar() {
		
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
	
	public void responder() {
		final ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); 
		final HttpSession session = (HttpSession) ec.getSession(true);
		usuario = (Usuario) session.getAttribute("usuario");
				
		if (usuario != null) {
			PessoaFisica pessoaFisica = pessoaFisicaService.obterPessoaFisicaPorId(usuario.getPessoaFisica().getId());
		    Date dataAtual = new Date();
		    
		    resposta.setDataPublicacao(dataAtual);
		    resposta.setPessoaFisica(pessoaFisica);
		    resposta.setComentario(comentario);
	
		    respostaService.create(resposta);
	
		    comentario.getRespostas().add(resposta);
	
		    comentarioService.merge(comentario);
	
		    resposta = new Resposta();
		} else {
			addMessage(FacesMessage.SEVERITY_ERROR, "Somente usuários cadastrados podem responder", null);
		}
	}
	
	public void carregarRespostas() {
        comentario = comentarioService.obtemComentarioPorIdComRespostas(comentarioId);
        respostas = comentario.getRespostas();
    }

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public Long getComentarioId() {
		return comentarioId;
	}

	public void setComentarioId(Long comentarioId) {
		this.comentarioId = comentarioId;
	}

	public Resposta getResposta() {
		return resposta;
	}

	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}