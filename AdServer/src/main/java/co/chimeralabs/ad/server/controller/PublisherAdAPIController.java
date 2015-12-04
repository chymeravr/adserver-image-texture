package co.chimeralabs.ad.server.controller;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.chimeralabs.ad.server.apiDTO.ImageTextureAdResponseClientDTO;
import co.chimeralabs.advertiser.client.api.*;
import co.chimeralabs.advertiser.client.apiDTO.ImageTextureAdDTO;
import co.chimeralabs.advertiser.client.apiDTO.ImageTextureAdsDTO;
import co.chimeralabs.ad.server.util.RetrieveResources;

@RestController
public class PublisherAdAPIController {
	
	@RequestMapping(value={"/publisher/api/loadad", "/"}, method=RequestMethod.GET)
	public ImageTextureAdResponseClientDTO getAdMetadata(){
		InputStream inputStream = getClass().getResourceAsStream("/machine/MachineConstants.xml");
		
		AdvertiserServerAPI advertiserServerAPI = new AdvertiserServerAPI();
		advertiserServerAPI.setUrl(RetrieveResources.retrieveResourcesAppConatants(inputStream, "advertiserapiurl").get(0)+"/"	+"api/ads");
		ImageTextureAdsDTO imageObjects = advertiserServerAPI.getAdResourceData();
		
		//co.chimeralabs.advertiser.client.apiDTO.AdResourceData adData = advertiserServerAPI.getAdResourceData();
		//AdResourceData adResourceData = new AdResourceData(adData.getResourceURL(), adData.getResourceMetadata(), adData.getResourceErrorCode());
		//AdResourceData adResourceData = new AdResourceData("asdf", "metadata", "200");
		ImageTextureAdResponseClientDTO response = new ImageTextureAdResponseClientDTO();
		if(imageObjects.getErrorMsg() != null){
			response.setResourceErrorCode(0);
			response.setErrorMsg("");
		}
		else{
			response.setResourceErrorCode(1);
			response.setErrorMsg(imageObjects.getErrorMsg());
		}
		List<ImageTextureAdDTO> ads = imageObjects.getAds();
		int size = ads.size();
		Random random = new Random();
		int index = random.nextInt(size);
		ImageTextureAdDTO imageObject = ads.get(index);
		inputStream = getClass().getResourceAsStream("/machine/MachineConstants.xml");
		String baseDir = RetrieveResources.retrieveResourcesAppConatants(inputStream, "imageresourceurl").get(0);
		String url = baseDir + "/" + imageObject.getAdResourceIdentifier() +"."+imageObject.getAdResourceFormat();
		response.setResourceURL(url);
		return response;
	}
}
