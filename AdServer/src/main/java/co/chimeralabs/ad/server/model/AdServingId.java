package co.chimeralabs.ad.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AdServingId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -729750511337529214L;

	@Id
	private Long id;
	
	private Long currentAdServingId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCurrentAdServingId() {
		return currentAdServingId;
	}

	public void setCurrentAdServingId(Long currentAdServingId) {
		this.currentAdServingId = currentAdServingId;
	}
	
}
