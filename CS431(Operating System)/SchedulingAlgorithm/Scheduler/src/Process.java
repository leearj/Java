public class Process {
	
	private int pID, sBT, eBT, CT, priority;

	Process(int pID, int sBT, int eBT, int CT, int priority) {
		this.pID = pID;
		this.sBT = sBT;
		this.eBT = eBT;
		this.CT = CT;
		this.priority = priority;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getpID() {
		return pID;
	}
	
	public void setpID(int pID) {
		this.pID = pID;
	}
	
	public int getsBT() {
		return sBT;
	}
	
	public void setsBT(int sBT) {
		this.sBT = sBT;
	}
	
	public int geteBT() {
		return eBT;
	}
	
	public void seteBT(int eBT) {
		this.eBT = eBT;
	}
	
	public int getCT() {
		return CT;
	}
	
	public void setCT(int cT) {
		CT = cT;
	}
	
	public String toString() {
		return "[pID:"+pID+", sBT:"+sBT+", eBT: "+eBT+", CT: "+CT+", Priority:"+priority+"]";
	}
}