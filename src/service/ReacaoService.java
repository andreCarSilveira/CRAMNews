package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Noticia;
import modelo.Reacao;
import modelo.Usuario;

@Stateless
public class ReacaoService extends GenericService<Reacao> {

	public ReacaoService(){
		super(Reacao.class);
	}
	
	public Reacao reagiu(Usuario usuario, Noticia noticia, Boolean curtida) {
		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Reacao> cQuery = cBuilder.createQuery(Reacao.class);
		final Root<Reacao> rootReacao = cQuery.from(Reacao.class);
		
		cQuery.select(rootReacao);
	    cQuery.where(cBuilder.and(cBuilder.equal(rootReacao.get("usuario"), usuario), cBuilder.equal(rootReacao.get("noticia"), noticia),
	    		cBuilder.equal(rootReacao.get("curtida"), curtida)));
	    
	    try {
	        return getEntityManager().createQuery(cQuery).getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
		
	}
	
	public void deletarReacoesPorNoticia(Long noticiaId) {
	    final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaDelete<Reacao> cDelete = cBuilder.createCriteriaDelete(Reacao.class);
	    final Root<Reacao> rootReacao = cDelete.from(Reacao.class);

	    cDelete.where(cBuilder.equal(rootReacao.get("noticia"), noticiaId));

	    getEntityManager().createQuery(cDelete).executeUpdate();
	}
	
}
