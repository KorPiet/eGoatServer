package eGoatServer;

public class DatabaseLine {
	String filename;
	String shasum;
	String ip;
	
	public DatabaseLine(String filename, String shasum, String ip) {
		this.filename = filename;
		this.shasum = shasum;
		this.ip = ip;
	}
	
	public DatabaseLine(String all) {
		String parts[] = all.split("\t");
		filename = parts[0];
		shasum = parts[1];
		ip = parts[2];
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getShasum() {
		return shasum;
	}
	public void setShasum(String shasum) {
		this.shasum = shasum;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Boolean isFilename(String message) {
		if(filename.equals(message))
			return true;
		else
			return false;
	}
	
	public String toString() {
		return filename + "\t" + shasum + "\t" + ip;
	}
	
}
