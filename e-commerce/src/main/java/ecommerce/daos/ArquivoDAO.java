package ecommerce.daos;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ecommerce.control.Transactional;

@Named
public class ArquivoDAO implements Serializable {
	@Inject
	private EntityManager em;
}
