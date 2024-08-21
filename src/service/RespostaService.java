package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Comentario;
import modelo.Noticia;
import modelo.Resposta;

@Stateless
public class RespostaService extends GenericService<Resposta> {

	public RespostaService() {
		super(Resposta.class);
	}
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<Resposta> obterRespostasPorComentario(Long id) {
		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Resposta> cQuery = cBuilder.createQuery(Resposta.class);
		final Root<Resposta> rootResposta = cQuery.from(Resposta.class);
	    
	    cQuery.select(rootResposta);
	    cQuery.where(cBuilder.equal(rootResposta.get("comentario").get("id"), id));
	    
	    try {
	        return getEntityManager().createQuery(cQuery).getResultList();
	    } catch (NoResultException e) {
	        throw new RuntimeException();
	    }
	    
	}

	public void deletarRespostasPorComentario(Long comentarioId) {
	    final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaDelete<Resposta> cDelete = cBuilder.createCriteriaDelete(Resposta.class);
	    final Root<Resposta> rootResposta = cDelete.from(Resposta.class);

	    cDelete.where(cBuilder.equal(rootResposta.get("comentario"), comentarioId));

	    getEntityManager().createQuery(cDelete).executeUpdate();
	}
	
}
