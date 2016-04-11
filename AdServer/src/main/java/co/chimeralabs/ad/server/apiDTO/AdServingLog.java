package co.chimeralabs.ad.server.apiDTO;

public class AdServingLog {
	private String adServingId;
	private String advertiserId;
	private String adId;
	private Double sellPrice;
	private Double buyPrice;
	
	public AdServingLog(){
		
	}
	
	public AdServingLog(String adServingId, String advertiserId, String adId, Double sellPrice, Double buyPrice){
		this.adServingId = adServingId;
		this.advertiserId = advertiserId;
		this.adId = adId;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
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
	
	
}
