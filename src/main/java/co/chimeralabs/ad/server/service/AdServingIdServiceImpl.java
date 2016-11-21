package co.chimeralabs.ad.server.service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.chimeralabs.ad.server.model.AdServingId;
import co.chimeralabs.ad.server.repository.AdServingIdRepository;

@Service
public class AdServingIdServiceImpl implements AdServingIdService{

	@Autowired
	AdServingIdRepository adServingIdRepository;
	
	@Override
	@Transactional
	public String getAdServingIdHashed() {
		AdServingId adServingId = adServingIdRepository.findAll().get(0);
		Long currentId = adServingId.getCurrentAdServingId();
		Long nextId = currentId+1;
		Long hashId = Long.MAX_VALUE - nextId;
		String hashIdString = hashId.toString();
		adServingId.setCurrentAdServingId(nextId);
		adServingIdRepository.save(adServingId);
		Integer rand = ThreadLocalRandom.current().nextInt(1000, 10000);
		hashIdString = rand.toString() + hashIdString;
		return hashIdString;
	}
}