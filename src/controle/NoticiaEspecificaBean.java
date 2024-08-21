package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Noticia;
import service.NoticiaService;

@ViewScoped
@ManagedBean
public class NoticiaEspecificaBean {
	
	private Long colunistaId;
	
	private List<Noticia> noticias = new ArrayList<Noticia>();
	
	
	@EJB
	private NoticiaService noticiaService;
	
	public NoticiaEspecificaBean() {
		
	}
	
	@PostConstruct
	private void inicializar() {
		
	}
	
	public void carregarNoticia() {
		noticias = noticiaService.listarNoticiaPorColunista(colunistaId);
	}

	public Long getColunistaId() {
		return colunistaId;
	}

	public void setColunistaId(Long colunistaId) {
		this.colunistaId = colunistaId;
	}

	public List<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}
	
	
	
	
}
