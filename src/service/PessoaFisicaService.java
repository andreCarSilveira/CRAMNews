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

import modelo.PessoaFisica;
import modelo.Tipo;

@Stateless
public class PessoaFisicaService extends GenericService<PessoaFisica> {
	
	public PessoaFisicaService () {
		super(PessoaFisica.class);
	}
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<PessoaFisica> listarPessoaFisicas(){
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<PessoaFisica> cQuery = cBuilder.createQuery(PessoaFisica.class);
		final Root<PessoaFisica> rootAtendimento = cQuery.from(PessoaFisica.class);

		cQuery.select(rootAtendimento);
		cQuery.orderBy(cBuilder.asc(rootAtendimento.get("nome")));

		List<PessoaFisica> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;

	}
	
	public List<PessoaFisica> listarPessoaFisicaPorNome(String nome){
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<PessoaFisica> cQuery = cBuilder.createQuery(PessoaFisica.class);
		final Root<PessoaFisica> rootAtendimento = cQuery.from(PessoaFisica.class);

		final Expression<String> expdescricao = rootAtendimento.get("nome");

		cQuery.select(rootAtendimento);
		cQuery.where(cBuilder.like(expdescricao, "%"+ nome +"%"));
		cQuery.orderBy(cBuilder.asc(rootAtendimento.get("nome")));

		List<PessoaFisica> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;

	}
	
	public PessoaFisica obterPessoaFisicaPorId(Long id) {
		
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<PessoaFisica> cQuery = cBuilder.createQuery(PessoaFisica.class);
		final Root<PessoaFisica> rootNoticia = cQuery.from(PessoaFisica.class);
	    
	    cQuery.select(rootNoticia);
	    cQuery.where(cBuilder.equal(rootNoticia.get("id"), id));
	    
	    try {
	        return getEntityManager().createQuery(cQuery).getSingleResult();
	    } catch (NoResultException e) {
	        throw new RuntimeException();
	    }
	    
	}
	
	public List<PessoaFisica> listarPessoaFisicaPorNomeEUsuario(String nome, Long usuarioId) {
	    final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaQuery<PessoaFisica> cQuery = cBuilder.createQuery(PessoaFisica.class);
	    final Root<PessoaFisica> rootAtendimento = cQuery.from(PessoaFisica.class);

	    final Expression<String> expNome = rootAtendimento.get("nome");
	    final Expression<Long> expUsuarioId = rootAtendimento.get("usuario").get("id"); // Supondo que o relacionamento seja mapeado corretamente

	    cQuery.select(rootAtendimento);
	    cQuery.where(
	        cBuilder.like(expNome, "%" + nome + "%"),
	        cBuilder.equal(expUsuarioId, usuarioId)
	    );
	    cQuery.orderBy(cBuilder.asc(rootAtendimento.get("nome")));

	    List<PessoaFisica> resultado = getEntityManager().createQuery(cQuery).getResultList();

	    return resultado;
	}
	
	public List<PessoaFisica> listarColunistas() {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<PessoaFisica> cQuery = cBuilder.createQuery(PessoaFisica.class);
		final Root<PessoaFisica> rootAtendimento = cQuery.from(PessoaFisica.class);

		cQuery.select(rootAtendimento);
		cQuery.where(cBuilder.equal(rootAtendimento.get("tipo"), Tipo.COLUNISTA));

		List<PessoaFisica> resultado = getEntityManager().createQuery(cQuery).getResultList();

		return resultado;
	}
	
	
}
