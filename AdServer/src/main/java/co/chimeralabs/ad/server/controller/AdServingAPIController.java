package co.chimeralabs.ad.server.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.chimeralabs.ad.server.apiDTO.AdRequestDTO;
import co.chimeralabs.ad.server.apiDTO.AdResourceMetadata;
import co.chimeralabs.ad.server.apiDTO.AdResponseDTO;
import co.chimeralabs.ad.server.apiDTO.AdServingLog;
import co.chimeralabs.ad.server.apiDTO.AnalyticsLogDTO;
import co.chimeralabs.ad.server.apiDTO.ClientAppConfigParamsDTO;
import co.chimeralabs.ad.server.service.AdServingIdService;
import co.chimeralabs.ad.server.util.RetrieveResources;
import co.chimeralabs.advertiser.client.api.*;
import co.chimeralabs.advertiser.client.apiDTO.ImageTextureAdDTO;
import co.chimeralabs.advertiser.client.apiDTO.ImageTextureAdsDTO;

@RestController
public class AdServingAPIController {
	private static final Logger logger = LoggerFactory.getLogger(AdServingAPIController.class);
	private static final Logger analyticsLogger = LoggerFactory.getLogger(AdServingAPIController.class.getName() + ".adserveranalytics");
	
	@Autowired
	AdServingIdService adServingIdService;
	
	@RequestMapping(value={"clientappconfigparams"}, method=RequestMethod.GET)
	public ClientAppConfigParamsDTO getClientAppConfigParams() throws IOException{
		ClientAppConfigParamsDTO configParams = new ClientAppConfigParamsDTO();
		InputStream inputStream = getClass().getResourceAsStream("/machine/MachineConstants.xml");
		configParams.setAnalyticsUrl(RetrieveResources.retrieveResourcesAppConatants(inputStream, "analyticsurlforclient").get(0));
		inputStream = getClass().getResourceAsStream("/machine/MachineConstants.xml");
		configParams.setImageTextureAdUnitUrl(RetrieveResources.retrieveResourcesAppConatants(inputStream, "imagetextureaduniturlforclient").get(0));
		inputStream.close();
		return configParams;
	}
	
	@RequestMapping(value={"/api/getadmetadata", "/"}, method=RequestMethod.POST, consumes="application/json")
	public AdResponseDTO getAdMetadata(@RequestBody AdRequestDTO adRequest) throws IOException{
		Long timestamp = System.currentTimeMillis();
		ObjectMapper objectMapper = new ObjectMapper();
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
				if(imageObjects == null)
					logger.warn("getAdMetadata - advertiser server returned null");
				else if(imageObjects.getErrorMsg() != null){
					response.setErrorCode(0);
					response.setErrorMsg("");
					logger.debug("getAdMetadata - advertiser server returned with success");
				}
				else{
					response.setErrorCode(1);
					response.setErrorMsg(imageObjects.getErrorMsg());
					logger.debug("getAdMetadata - advertiser server returned with error message=" + imageObjects.getErrorMsg());
				}
				List<ImageTextureAdDTO> ads = imageObjects.getAds();
				int size = ads.size();
				Random random = new Random();
				for(int i=0; i<adRequest.getnDistinctAds(); i++){
					AdResourceMetadata resourceData = new AdResourceMetadata();
					int index = random.nextInt(size);
					ImageTextureAdDTO imageObject = ads.get(index);
					resourceData.setDiffuseTextureImageUrl(imageObject.getAdResourceUrl());
					resourceData.setAdServingId(adServingIdService.getAdServingIdHashed());
					resourceData.setErrorCode(0);
					resourceData.setErrorMsg("");
					logger.debug("getAdMetadata - adding resource with adServingId=" + resourceData.getAdServingId() + " and resourceUrl=" + resourceData.getDiffuseTextureImageUrl() );
					response.addAdResource(resourceData);
					AdServingLog log = new AdServingLog(resourceData.getAdServingId(), imageObject.getAdvertiserId(), imageObject.getAdId(), 10.1, 9.1, adRequest);
					AnalyticsLogDTO logDTO = new AnalyticsLogDTO();
					logDTO.setType(5);
					try {
						String jsonObj = objectMapper.writeValueAsString(log);
						jsonObj = jsonObj.substring(1, jsonObj.length() - 1);
			            String[] properties = jsonObj.split(",");
			            String finalValue = "";
			            int j = 0;
			            for (j = 0; j < properties.length - 1; j++)
			            {
			                String property = properties[j];
			                String value = property.split(":")[1];
			                finalValue = finalValue + value + "\t";
			            }
			            String propertyLast = properties[j];
			            String valueLast = propertyLast.split(":")[1];
			            finalValue = finalValue + valueLast;
						logDTO.setDtoObj(finalValue);
						logDTO.setDtoObj(timestamp.toString() + "\t" + logDTO.getType() + "\t" +logDTO.getDtoObj());
						analyticsLogger.info(logDTO.getDtoObj());
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
//		AsyncRestTemplate asycTemp = new AsyncRestTemplate();
//		String analyticsLogUrl = "http://localhost:8080/analyticsdatareceiver/analytics";
//		HttpMethod method = HttpMethod.POST;
//		Class<String> responseType = String.class;
//		//create request entity using HttpHeaders
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		
//		HttpEntity<List<AnalyticsLogDTO>> requestEntity = new HttpEntity<List<AnalyticsLogDTO>>(analyticsLogs, headers);
//		ListenableFuture<ResponseEntity<String>> future = asycTemp.exchange(analyticsLogUrl, method, requestEntity, responseType);
//		
		inputStream.close();
		logger.debug("getAdMetadata - Returning");
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
