package co.chimeralabs.ad.server.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.chimeralabs.ad.server.model.AdServingId;

@Repository
@Transactional
public interface AdServingIdRepository extends JpaRepository<AdServingId, Long>{

}
