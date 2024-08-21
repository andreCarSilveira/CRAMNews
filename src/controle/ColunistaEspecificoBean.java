package controle;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import modelo.Comentario;
import modelo.Noticia;
import modelo.PessoaFisica;
import modelo.Reacao;
import modelo.Resposta;
import modelo.Usuario;
import service.ColunistaService;
import service.ComentarioService;
import service.NoticiaService;
import service.PessoaFisicaService;
import service.ReacaoService;
import service.RespostaService;
import service.UsuarioService;

@ViewScoped
@ManagedBean
public class ColunistaEspecificoBean {

	private Long pessoaId = 0L;
	
	@EJB
	private ColunistaService colunistaService;
	
	@EJB
	private PessoaFisicaService pessoaFisicaService;
	
	@EJB
	private NoticiaService noticiaService;
	
	private List<Noticia> noticias = new ArrayList<Noticia>();
	private PessoaFisica pessoaFisica = new PessoaFisica();
	private List<PessoaFisica> pessoasFisicas = new ArrayList<PessoaFisica>();
	
	
	
	
	public ColunistaEspecificoBean() {
		pessoaFisica = new PessoaFisica();
	}
	
	@PostConstruct
	private void init() {
		pessoasFisicas = pessoaFisicaService.listarColunistas();
	}
	
	public void filtrar() {
		if(pessoaId == 0) {
			noticias = noticiaService.listarItens();
		} else {
			PessoaFisica selectPessoa = pessoaFisicaService.obtemPorId(pessoaId);
			if(selectPessoa != null) {
				noticias = noticiaService.listarNoticiaPorColunista(pessoaId);
			}
		}
		if(noticias.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Não tem notícia.", null));
		}
		
		
	}
	
	public void listarPessoas() {
		pessoasFisicas = pessoaFisicaService.listarPessoaFisicas();
	}

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	public List<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
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
