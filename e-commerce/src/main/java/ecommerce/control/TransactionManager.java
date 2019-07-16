package ecommerce.control;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transactional
@Interceptor
public class TransactionManager implements Serializable {
	private static final long serialVersionUID = -7211840937318263848L;
	
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
