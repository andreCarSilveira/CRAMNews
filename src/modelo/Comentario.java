package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comentario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	private String texto;
	private Date dataPublicacao;
	
	@ManyToOne
	private Noticia noticia;
	
	@ManyToOne
	private PessoaFisica usuario;
	
	@OneToMany(cascade = CascadeType.REMOVE)
    private List<Resposta> respostas = new ArrayList<>();

	
	public Comentario() {
		
	}
	
	public void excluirRespostas() {
        for (Resposta resposta : respostas) {
            resposta.setComentario(null); 
        }
        respostas.clear(); 
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public PessoaFisica getUsuario() {
		return usuario;
	}

	public void setUsuario(PessoaFisica usuario) {
		this.usuario = usuario;
	}
	
}
