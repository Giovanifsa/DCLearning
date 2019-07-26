package ecommerce.control;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transactional
@Interceptor
public class GerenciadorTransacao implements Serializable {
	@Inject
	private EntityManager em;
	
	@AroundInvoke
	public Object transactionContext(InvocationContext context) throws Exception {
		try {
			em.getTransaction().begin();
			Object obj = context.proceed();
			em.getTransaction().commit();
			
			return obj;
		} catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
	}
}
