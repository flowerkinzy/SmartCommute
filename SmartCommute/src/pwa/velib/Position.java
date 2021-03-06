package pwa.velib;

public class Position {
	private double lat;
	private double lng;
	public Position(double lat, double lng){
		this.lat = lat;
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public double dist(Position p){
		return (p.lat-lat)*(p.lat-lat)+(p.lng-lng)*(p.lng-lng);
	}
}
