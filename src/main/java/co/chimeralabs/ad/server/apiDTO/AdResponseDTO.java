package co.chimeralabs.ad.server.apiDTO;

import java.util.ArrayList;
import java.util.List;

public class AdResponseDTO {
	private List<AdResourceMetadata> adResources;
	private String adUnitId;
	private Integer errorCode;
	private String errorMsg;
	
	public AdResponseDTO(){
		adResources = new ArrayList<AdResourceMetadata>();
	}
	
	public void addAdResource(AdResourceMetadata adResourceData){
		adResources.add(adResourceData);
	}
	
	public List<AdResourceMetadata> getAdResources() {
		return adResources;
	}

	public void setAdResources(List<AdResourceMetadata> adResources) {
		this.adResources = adResources;
	}

	public String getAdUnitId() {
		return adUnitId;
	}

	public void setAdUnitId(String adUnitId) {
		this.adUnitId = adUnitId;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
