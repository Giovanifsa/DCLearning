package ecommerce.daos;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import ecommerce.control.Transactional;
import ecommerce.models.BinaryData;

@Named
public class FileDao implements Serializable {
	@Inject
	private EntityManager em;
	
	@Transactional
	public void addBinaryFile(BinaryData bData) {
		em.persist(bData);
	}
}
