package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import modelo.Comentario;
import modelo.Noticia;
import modelo.Reacao;

@Stateless
public class ComentarioService extends GenericService<Comentario> {
	
	public ComentarioService () {
		super(Comentario.class);
	}
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public Comentario obtemComentarioPorId(Long id) {
		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Comentario> cQuery = cBuilder.createQuery(Comentario.class);
		final Root<Comentario> rootComentario = cQuery.from(Comentario.class);
	    
	    cQuery.select(rootComentario);
	    cQuery.where(cBuilder.equal(rootComentario.get("id"), id));
	    
	    try {
	        return getEntityManager().createQuery(cQuery).getSingleResult();
	    } catch (NoResultException e) {
	        throw new RuntimeException();
	    }
	    
	}
	
	public Comentario obtemComentarioPorIdComRespostas(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comentario> criteriaQuery = criteriaBuilder.createQuery(Comentario.class);
        Root<Comentario> rootComentario = criteriaQuery.from(Comentario.class);

        // Carrega as respostas associadas ao comentário usando JOIN FETCH
        rootComentario.fetch("respostas", JoinType.LEFT);

        criteriaQuery.select(rootComentario).distinct(true);
        criteriaQuery.where(criteriaBuilder.equal(rootComentario.get("id"), id));

        TypedQuery<Comentario> typedQuery = entityManager.createQuery(criteriaQuery);

        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new RuntimeException("Comentário não encontrado com o ID: " + id);
        }
    }
	
	public List<Comentario> listarComentariosPorNoticia(Long noticiaId) {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Comentario> cQuery = cBuilder.createQuery(Comentario.class);
		final Root<Comentario> rootComentario = cQuery.from(Comentario.class);
		
		cQuery.select(rootComentario);
	    cQuery.where(cBuilder.equal(rootComentario.get("noticia"), noticiaId));
	    
	    try {
	        return getEntityManager().createQuery(cQuery).getResultList();
	    } catch (NoResultException e) {
	        throw new RuntimeException();
	    }
	}
	
	public void deletarComentariosPorNoticia(Long noticiaId) {
	    final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaDelete<Comentario> cDelete = cBuilder.createCriteriaDelete(Comentario.class);
	    final Root<Comentario> rootComentario = cDelete.from(Comentario.class);

	    cDelete.where(cBuilder.equal(rootComentario.get("noticia"), noticiaId));

	    getEntityManager().createQuery(cDelete).executeUpdate();
	}
	
}
