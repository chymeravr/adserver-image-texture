package co.chimeralabs.ad.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.chimeralabs.ad.server.apiDTO.AdResourceData;
import co.chimeralabs.advertiser.client.api.*;;

@RestController
public class PublisherAdAPIController {
	
	@RequestMapping(value={"/publisher/api/loadad", "/"}, method=RequestMethod.GET)
	public AdResourceData getAdMetadata(){
		AdvertiserServerAPI advertiserServerAPI = new AdvertiserServerAPI();
		co.chimeralabs.advertiser.client.apiDTO.AdResourceData adData = advertiserServerAPI.getAdResourceData();
		AdResourceData adResourceData = new AdResourceData(adData.getResourceURL(), adData.getResourceMetadata(), adData.getResourceErrorCode());
		//AdResourceData adResourceData = new AdResourceData("asdf", "metadata", "200");
		return adResourceData;
	}
}
