package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import modelo.PessoaFisica;

@Stateless
public class PessoaService extends GenericService<PessoaFisica> {
	
	public PessoaService(){
		super(PessoaFisica.class);
	}
	
	public void PersitPessoaComUsuario(PessoaFisica pessoa){
		getEntityManager().persist(pessoa.getUsuario());
		create(pessoa);		
	}
	
	public PessoaFisica validarUsuario(String log, String pass) throws Exception{
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		final CriteriaQuery<PessoaFisica> cquery = cb.createQuery(PessoaFisica.class);
		final Root<PessoaFisica> root = cquery.from(PessoaFisica.class);
		final List<Predicate> condicoes = new ArrayList<Predicate>();

		condicoes.add(cb.equal(root.get("usuario").get("login"), log));
		condicoes.add(cb.equal(root.get("usuario").get("senha"), pass));
		
		cquery.select(root).where(condicoes.toArray(new Predicate[]{}));
		PessoaFisica pessoa = new PessoaFisica();
		try{
			pessoa = getEntityManager().createQuery(cquery).getSingleResult();
		} catch (Exception e) {
			throw new Exception("Usuário ou senha invalido!");
		}	
    	
    	return pessoa;
	}
	
	
	public List<PessoaFisica> filtrarPorNome(String filtro){
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		final CriteriaQuery<PessoaFisica> cquery = cb.createQuery(PessoaFisica.class);
		final Root<PessoaFisica> root = cquery.from(PessoaFisica.class);
		final List<Predicate> condicoes = new ArrayList<Predicate>();

		Expression<String> path = root.get("nome");
		condicoes.add(cb.like(path, "%"+filtro+"%"));
		
		cquery.select(root).where(condicoes.toArray(new Predicate[]{}));
		List<PessoaFisica> pessoas = getEntityManager().createQuery(cquery).getResultList();
			    	
    	return pessoas;
	}

}
