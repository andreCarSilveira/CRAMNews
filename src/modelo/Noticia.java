package modelo;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Noticia {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private String manchete;
	
	@Lob
	private String tituloAuxiliar;
	
	@Lob
	private String lide;
	
	@Lob
    @Column(columnDefinition = "LONGTEXT")
    private String corpoDaNoticia;
	
	@Lob
	@Column(length=100000)
	private byte[] foto;
	
	private Long curtidas;
	private Long dislikes;
	private Date dataPublicacao;
	private Date dataEdicao;
	
	@Enumerated
	private Tema tema;
	
	@ManyToOne
	private PessoaFisica colunista;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Comentario> comentarios = new ArrayList<>();
	
	public Noticia() {
		
	}
	
	public void excluirComentarios() {
        for (Comentario comentario : comentarios) {
            comentario.setNoticia(null); // Remover a referência da notícia no comentário
        }
        comentarios.clear(); // Limpar a lista de comentários
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getManchete() {
		return manchete;
	}

	public void setManchete(String manchete) {
		this.manchete = manchete;
	}

	public String getTituloAuxiliar() {
		return tituloAuxiliar;
	}

	public void setTituloAuxiliar(String tituloAuxiliar) {
		this.tituloAuxiliar = tituloAuxiliar;
	}

	public String getLide() {
		return lide;
	}

	public void setLide(String lide) {
		this.lide = lide;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public Date getDataEdicao() {
		return dataEdicao;
	}

	public void setDataEdicao(Date dataEdicao) {
		this.dataEdicao = dataEdicao;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Long getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(Long curtidas) {
		this.curtidas = curtidas;
	}

	public Long getDislikes() {
		return dislikes;
	}

	public void setDislikes(Long dislikes) {
		this.dislikes = dislikes;
	}

	public PessoaFisica getColunista() {
		return colunista;
	}

	public void setColunista(PessoaFisica colunista) {
		this.colunista = colunista;
	}

	public String getCorpoDaNoticia() {
		return corpoDaNoticia;
	}

	public void setCorpoDaNoticia(String corpoDaNoticia) {
		this.corpoDaNoticia = corpoDaNoticia;
	}
	
	
	
}