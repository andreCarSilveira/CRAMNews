package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import modelo.Noticia;
import modelo.Tema;

@Stateless
public class ColunistaService extends GenericService<Noticia> {
	
	public ColunistaService () {
		super(Noticia.class);
	}
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Noticia> listarItens(){
 		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Noticia> cQuery = cBuilder.createQuery(Noticia.class);
		final Root<Noticia> rootAtendimento = cQuery.from(Noticia.class);

		cQuery.select(rootAtendimento);
		cQuery.orderBy(cBuilder.asc(rootAtendimento.get("manchete")));

		List<Noticia> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;

	}
	
	public Noticia obterNoticiaPorIdEUsuario(Long id, Long userId) {
	    try {
	        final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	        final CriteriaQuery<Noticia> cQuery = cBuilder.createQuery(Noticia.class);
	        final Root<Noticia> rootNoticia = cQuery.from(Noticia.class);
	        
	        // Assuming there's a field named "usuario" in your Noticia entity that represents the user who created the news.
	        cQuery.select(rootNoticia);
	        cQuery.where(
	            cBuilder.and(
	                cBuilder.equal(rootNoticia.get("id"), id),
	                cBuilder.equal(rootNoticia.get("usuario").get("id"), userId)
	            )
	        );
	        
	        return getEntityManager().createQuery(cQuery).getSingleResult();
	    } catch (NoResultException e) {
	        throw new RuntimeException("News not found for id: " + id + " and userId: " + userId);
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
	
	
	
	
}