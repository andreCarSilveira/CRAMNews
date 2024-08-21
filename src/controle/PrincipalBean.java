package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Noticia;
import modelo.Tema;
import service.NoticiaService;

@ViewScoped
@ManagedBean
public class PrincipalBean {
	
	private List<Noticia> noticias = new ArrayList<Noticia>();
	private String filtro;
	private Tema tema;
	private Noticia noticiaAtual;
	
	@EJB
	private NoticiaService noticiaService;
	
	public PrincipalBean() {
		
	}
	
	public void pesquisar() {
		if(filtro != null && !filtro.trim().isEmpty()) {
			noticias = noticiaService.listarNoticiasPorNome(filtro);
		}else {
			noticias = noticiaService.listAll();
		}
	}
	
	public void temaEspecifico(){
		if(tema != null) {
			noticias = noticiaService.listarNoticiasPorTema(tema);
		}else {
			noticias = noticiaService.listAll();
		}
	}
	
	@PostConstruct
	private void inicializar() {
		noticias = noticiaService.listarNoticiasPadrao();
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

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
		temaEspecifico();
	}
	
}
