package service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import modelo.Noticia;
import modelo.Tema;
import modelo.Comentario;

@Stateless
public class NoticiaService extends GenericService<Noticia> {
	
	public NoticiaService () {
		super(Noticia.class);
	}
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Noticia> listarNoticiasPadrao() {
	    final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaQuery<Noticia> cQuery = cBuilder.createQuery(Noticia.class);
	    final Root<Noticia> rootNoticia = cQuery.from(Noticia.class);
	    
	    Join<Noticia, Comentario> comentarioJoin = rootNoticia.join("comentarios", JoinType.LEFT);
	    
	    Expression<Long> countComentarios = cBuilder.count(comentarioJoin);
	    
	    Calendar tresDiasAtras = Calendar.getInstance();
	    tresDiasAtras.add(Calendar.DAY_OF_MONTH, -3);

	    cQuery.select(rootNoticia);
	    cQuery.where(
	        cBuilder.and(
	            cBuilder.greaterThanOrEqualTo(rootNoticia.get("dataPublicacao"), tresDiasAtras.getTime()), 
	            cBuilder.lessThanOrEqualTo(rootNoticia.get("dataPublicacao"), new Date()) 
	        )
	    );
	    cQuery.groupBy(rootNoticia.get("id")); 
	    cQuery.orderBy(cBuilder.desc(rootNoticia.get("curtidas"))); 
	    
	    TypedQuery<Noticia> query = getEntityManager().createQuery(cQuery);
	    List<Noticia> noticias = query.getResultList();
	    
	    return noticias;
	}

	
	public List<Noticia> listarMaisComentadas(Long pessoaId) {
	    final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaQuery<Noticia> cQuery = cBuilder.createQuery(Noticia.class);
	    final Root<Noticia> rootNoticia = cQuery.from(Noticia.class);
	    
	    // Join para trazer os comentários
	    Join<Noticia, Comentario> comentarioJoin = rootNoticia.join("comentarios", JoinType.LEFT);
	    
	    // Contar o número de comentários para cada notícia
	    Expression<Long> countComentarios = cBuilder.count(comentarioJoin);
	    
	    cQuery.select(rootNoticia);
	    cQuery.where(cBuilder.equal(rootNoticia.get("colunista").get("id"), pessoaId));
	    cQuery.groupBy(rootNoticia.get("id")); // Agrupar por notícia para contagem de comentários
	    cQuery.orderBy(cBuilder.desc(countComentarios)); // Ordenar pelo número de comentários em ordem decrescente
	    
	    TypedQuery<Noticia> query = getEntityManager().createQuery(cQuery);
	    List<Noticia> noticias = query.getResultList();
	    
	    return noticias;
	}
	
	public List<Noticia> listarItens(){
 		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Noticia> cQuery = cBuilder.createQuery(Noticia.class);
		final Root<Noticia> rootAtendimento = cQuery.from(Noticia.class);

		cQuery.select(rootAtendimento);
		cQuery.orderBy(cBuilder.asc(rootAtendimento.get("manchete")));

		List<Noticia> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;

	}
	
	public Noticia obterNoticiaPorId(Long id) {
		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Noticia> cQuery = cBuilder.createQuery(Noticia.class);
		final Root<Noticia> rootNoticia = cQuery.from(Noticia.class);
	    
	    cQuery.select(rootNoticia);
	    cQuery.where(cBuilder.equal(rootNoticia.get("id"), id));
	    
	    try {
	        return getEntityManager().createQuery(cQuery).getSingleResult();
	    } catch (NoResultException e) {
	        throw new RuntimeException();
	    }
	    
	}
	
	public List<Noticia> listarNoticiasPorNome(String nome){
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Noticia> cQuery = cBuilder.createQuery(Noticia.class);
		final Root<Noticia> rootAtendimento = cQuery.from(Noticia.class);
		
		final Expression<String> expdescricao = rootAtendimento.get("manchete");
		
		cQuery.select(rootAtendimento);
		cQuery.where(cBuilder.like(expdescricao, "%" + nome + "%" ));
		cQuery.orderBy(cBuilder.asc(rootAtendimento.get("manchete")));
		
		List<Noticia> resultado = getEntityManager().createQuery(cQuery).getResultList();
		
		return resultado;
	}
	
	public List<Noticia> listarNoticiasPorTema(Tema tema){
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Noticia> cQuery = cBuilder.createQuery(Noticia.class);
		final Root<Noticia> rootAtendimento = cQuery.from(Noticia.class);
		
		
		cQuery.select(rootAtendimento);
		cQuery.where(cBuilder.equal(rootAtendimento.get("tema"), tema));
		cQuery.orderBy(cBuilder.asc(rootAtendimento.get("manchete")));
		
		List<Noticia> resultado = getEntityManager().createQuery(cQuery).getResultList();
		
		return resultado;
	}
	
	
	public List<Noticia> listarNoticiaPorColunista(Long pessoaId) {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Noticia> cQuery = cBuilder.createQuery(Noticia.class);
		final Root<Noticia> rootAtendimento = cQuery.from(Noticia.class);
		
		
		
		cQuery.select(rootAtendimento);
		cQuery.where(cBuilder.equal(rootAtendimento.get("colunista"), pessoaId));
	
	    
	    List<Noticia> noticias = getEntityManager().createQuery(cQuery).getResultList();
		
		return noticias;
	}
	
	
}