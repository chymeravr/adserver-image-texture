package co.chimeralabs.ad.server.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdServingLog {
	private String adServingId;
	private String advertiserId;
	private String adId;
	private Double sellPrice;
	private Double buyPrice;
	private String location;
	private String hmd;
	private String OS;
	private String device;
	
	public AdServingLog(){
		
	}
	
	public AdServingLog(String adServingId, String advertiserId, String adId, Double sellPrice, Double buyPrice, AdRequestDTO adRequestDTO){
		this.adServingId = adServingId;
		this.advertiserId = advertiserId;
		this.adId = adId;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		this.location = adRequestDTO.getLocation();
		this.hmd = adRequestDTO.getHmd();
		this.OS = adRequestDTO.getOS();
		this.device = adRequestDTO.getDevice();
	}
	
	public String getAdServingId() {
		return adServingId;
	}
	public void setAdServingId(String adServingId) {
		this.adServingId = adServingId;
	}
	public String getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
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
