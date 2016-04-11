package co.chimeralabs.ad.server.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.chimeralabs.ad.server.apiDTO.AdRequestDTO;
import co.chimeralabs.ad.server.apiDTO.AdResourceMetadata;
import co.chimeralabs.ad.server.apiDTO.AdResponseDTO;
import co.chimeralabs.ad.server.apiDTO.AdServingLog;
import co.chimeralabs.ad.server.apiDTO.AnalyticsLogDTO;
import co.chimeralabs.ad.server.service.AdServingIdService;
import co.chimeralabs.ad.server.util.RetrieveResources;
import co.chimeralabs.advertiser.client.api.*;
import co.chimeralabs.advertiser.client.apiDTO.ImageTextureAdDTO;
import co.chimeralabs.advertiser.client.apiDTO.ImageTextureAdsDTO;

@RestController
public class PublisherAdAPIController {
	
	@Autowired
	AdServingIdService adServingIdService;
	
	@RequestMapping(value={"/publisher/api/loadad", "/"}, method=RequestMethod.POST, consumes="application/json")
	public AdResponseDTO getAdMetadata(@RequestBody AdRequestDTO adRequest){
		ObjectMapper objectMapper = new ObjectMapper();
		List<AnalyticsLogDTO> analyticsLogs = new ArrayList<AnalyticsLogDTO>();
		
		InputStream inputStream = getClass().getResourceAsStream("/machine/MachineConstants.xml");
		
		AdResponseDTO response = new AdResponseDTO();
		if(adRequest==null){
			response.setErrorCode(1);
			response.setErrorMsg("Invalid Request");
			return response;
		}
		response.setAdUnitId(adRequest.getAdUnitId());
		switch(adRequest.getAdType()){
		case IMAGE_TEXTURE:
			if(adRequest.getnDistinctAds()==null){
				response.setErrorCode(1);
				response.setErrorMsg("nDistinctAds not defined");
			}else{
				AdvertiserServerAPI advertiserServerAPI = new AdvertiserServerAPI();
				advertiserServerAPI.setUrl(RetrieveResources.retrieveResourcesAppConatants(inputStream, "advertiserapiurl").get(0)+"/"	+"api/ads");
				ImageTextureAdsDTO imageObjects = advertiserServerAPI.getAdResourceData();
				if(imageObjects.getErrorMsg() != null){
					response.setErrorCode(0);
					response.setErrorMsg("");
				}
				else{
					response.setErrorCode(1);
					response.setErrorMsg(imageObjects.getErrorMsg());
				}
				List<ImageTextureAdDTO> ads = imageObjects.getAds();
				int size = ads.size();
				inputStream = getClass().getResourceAsStream("/machine/MachineConstants.xml");
				String baseDir = RetrieveResources.retrieveResourcesAppConatants(inputStream, "imageresourceurl").get(0);
				Random random = new Random();
				for(int i=0; i<adRequest.getnDistinctAds(); i++){
					AdResourceMetadata resourceData = new AdResourceMetadata();
					int index = random.nextInt(size);
					ImageTextureAdDTO imageObject = ads.get(index);
					String url = baseDir + "/" + imageObject.getAdResourceIdentifier() +"."+imageObject.getAdResourceFormat();
					resourceData.setDiffuseTextureImageUrl(url);
					resourceData.setAdServingId(adServingIdService.getAdServingIdHashed());
					resourceData.setErrorCode(0);
					resourceData.setErrorMsg("");
					response.addAdResource(resourceData);
					AdServingLog log = new AdServingLog(resourceData.getAdServingId(), imageObject.getAdvertiserId(), imageObject.getAdId(), 10.1, 9.1);
					String logJson = "";
					AnalyticsLogDTO logDTO = new AnalyticsLogDTO();
					logDTO.setType(5);
					try {
						logJson = objectMapper.writeValueAsString(log);
						logDTO.setDtoObj(logJson);
						analyticsLogs.add(logDTO);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;
		}
		
		/*String analyticsLogUrl = "http://localhost:8080/analyticsdatareceiver/analytics";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String res = restTemplate.postForObject(analyticsLogUrl, analyticsLogs, String.class);
		*/
		AsyncRestTemplate asycTemp = new AsyncRestTemplate();
		String analyticsLogUrl = "http://localhost:8080/analyticsdatareceiver/analytics";
		HttpMethod method = HttpMethod.POST;
		Class<String> responseType = String.class;
		//create request entity using HttpHeaders
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<List<AnalyticsLogDTO>> requestEntity = new HttpEntity<List<AnalyticsLogDTO>>(analyticsLogs, headers);
		ListenableFuture<ResponseEntity<String>> future = asycTemp.exchange(analyticsLogUrl, method, requestEntity, responseType);
		
		return response;
	}
	
	@RequestMapping(value="/getipaddress", method=RequestMethod.GET)
	public String getIPAddress(HttpServletRequest request){
		return request.getRemoteAddr();
	}
	
	@RequestMapping(value="/getipaddress2", method=RequestMethod.GET)
	public String getIPAddressMethod2(HttpServletRequest request){
		return request.getHeader("X-FORWARDED-FOR");
	}
	
}
