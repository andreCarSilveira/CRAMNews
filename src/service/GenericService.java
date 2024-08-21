package service;

import java.util.List;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

//Define a classe como de transação
@TransactionManagement(TransactionManagementType.CONTAINER)
public abstract class GenericService<T> { //classe abstrata, não poderá ser instanciada, ela servira de base para outras classes que irão estender à ela.
//Esta classe se assemelha a uma classe DAO generica.	
	/*
	 * 
	Define a unidade de persistencia, ou seja, qual banco deve ser manipulado (punit é o nome dado á unidade de persistencia
	criada dentro do arquivo persistence.xml)
	*
	*/
	@PersistenceContext(unitName="punit")
    private EntityManager entityManager;
	
	//Objeto genérica, ou seja, pode assumir qualquer classe persistente no sistema
	private final Class<T> classe;

	//construtor
	public GenericService(Class<T> classe) {
		this.classe = classe; //ao instanciar ou chamar esta classe, no construtor deve-se informar a classe que será usada
								//desta forma, não será possivel usar um unico service para todas as classes.
	}
	
	//get's e set's
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	//metodo para criar/salvar
	public void create(T entity ) {
        getEntityManager().persist(entity); 
    }

	//metodo para atualizar/update
    public void merge(T entity){
			entity = getEntityManager().merge(entity);			
    }

    //metodo para remover/apagar
    public void remove(T entity) {
	        getEntityManager().remove(getEntityManager().merge(entity));		  
    }
    
    
    //metodo para obter um objeto por ID
    public final T obtemPorId(Long id ){
		T entity = getEntityManager().find(classe, id);		
		return entity;
	}
	
    //metodo para listar todos os dados armazenados na tabela.
    public List<T> listAll(){
    	final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();	
    	final CriteriaQuery<T> cQuery = cb.createQuery(classe);
	
    	cQuery.select(cQuery.from(classe));

    	List<T> list = getEntityManager().createQuery(cQuery).getResultList();
    	return list;
    }	
}



