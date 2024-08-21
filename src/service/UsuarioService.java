package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import modelo.PessoaFisica;


	@Stateless
	public class UsuarioService extends GenericService<PessoaFisica>{

		public UsuarioService() {
			super(PessoaFisica.class);
		}

		 	@PersistenceContext
		    private EntityManager entityManager;
		 	
		 	public List<PessoaFisica> listarUsuarios(){
				final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
				final CriteriaQuery<PessoaFisica> cQuery = cBuilder.createQuery(PessoaFisica.class);
				final Root<PessoaFisica> rootAtendimento = cQuery.from(PessoaFisica.class);

				cQuery.select(rootAtendimento);
				cQuery.orderBy(cBuilder.asc(rootAtendimento.get("nome")));

				List<PessoaFisica> resultado = getEntityManager().createQuery(cQuery).getResultList();

				return resultado;

			}
		 	
		 	public PessoaFisica obtemPessoaFisicaPorUsuario(Long idUsuario){
				final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
				final CriteriaQuery<PessoaFisica> cQuery = cBuilder.createQuery(PessoaFisica.class);
				final Root<PessoaFisica> rootAtendimento = cQuery.from(PessoaFisica.class);

				cQuery.select(rootAtendimento);
				cQuery.where(cBuilder.equal(rootAtendimento.get("usuario"), idUsuario));

				PessoaFisica resultado = getEntityManager().createQuery(cQuery).getSingleResult();

				return resultado;

			}

		 	public List<PessoaFisica> listarUsuariosPorNome(String nome){
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
		 	
		 	public PessoaFisica verificaUsuario(String email, String senha) {
		 	    try {
		 	        CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		 	        CriteriaQuery<PessoaFisica> cQuery = cBuilder.createQuery(PessoaFisica.class);
		 	        Root<PessoaFisica> rootAtendimento = cQuery.from(PessoaFisica.class);

		 	        Expression<String> expdescricao = rootAtendimento.get("email");
		 	        Expression<String> expsenha = rootAtendimento.get("senha");

		 	        cQuery.select(rootAtendimento);
		 	        cQuery.where(cBuilder.and(cBuilder.equal(expdescricao, email), cBuilder.equal(expsenha, senha)));

		 	        return getEntityManager().createQuery(cQuery).getSingleResult();
		 	    } catch (NoResultException e) {
		 	        // Usuário não encontrado
		 	        return null;
		 	    } catch (Exception e) {
		 	        // Lidar com outras exceções, como HibernateException, SQLException, etc.
		 	        e.printStackTrace(); // ou logar a exceção
		 	        throw new RuntimeException("Erro ao verificar usuário", e);
		 	    }
		 	}
		 	
		 	public void PersitPessoaComUsuario(PessoaFisica usuario){
				getEntityManager().persist(usuario);
				create(usuario);		
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