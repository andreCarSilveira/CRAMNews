package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import modelo.PessoaFisica;
import modelo.Usuario;

@Stateless
public class UsuService extends GenericService<Usuario> {

	public UsuService () {
		super(Usuario.class);
	}
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public Boolean listarUsuarioPorLogin(String nome){
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Usuario> cQuery = cBuilder.createQuery(Usuario.class);
		final Root<Usuario> rootAtendimento = cQuery.from(Usuario.class);

		cQuery.select(rootAtendimento);
		cQuery.where(cBuilder.equal(rootAtendimento.get("login"), nome));

		try {
			Usuario resultado = getEntityManager().createQuery(cQuery).getSingleResult();
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	
}
