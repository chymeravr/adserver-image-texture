package co.chimeralabs.ad.server.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdRequestDTO {
	private AdType adType;
	private String adUnitId;
	private Integer nDistinctAds;
	private String location;
	private String hmd;
	private String OS;
	private String device;
	
	public AdType getAdType() {
		return adType;
	}

	public void setAdType(AdType adType) {
		this.adType = adType;
	}

	public String getAdUnitId() {
		return adUnitId;
	}

	public void setAdUnitId(String adUnitId) {
		this.adUnitId = adUnitId;
	}

	public Integer getnDistinctAds() {
		return nDistinctAds;
	}

	public void setnDistinctAds(Integer nDistinctAds) {
		this.nDistinctAds = nDistinctAds;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHmd() {
		return hmd;
	}

	public void setHmd(String hmd) {
		this.hmd = hmd;
	}

	@JsonProperty("OS")
	public String getOS() {
		return OS;
	}

	@JsonProperty("OS")
	public void setOS(String oS) {
		OS = oS;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
	
}
