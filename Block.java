import java.util.*;

public class Block 
{
	int index,proof;
	String prev_hash;
	Date timeNow;
	String data;
	
	public Block(int index,int proof,String prev_hash,Date time,String data)
	{
		this.index=index;
		this.proof=proof;
		this.prev_hash=prev_hash;
		this.timeNow=time;
		this.data=data;
	}
	
	public String getData(){
		return data;
	}
	public int getIndex() {
		return index;
	}
	public int getProof() {
		return proof;
	}
	public String getPrev_hash() {
		return prev_hash;
	}
	public Date getTimeNow() {
		return timeNow;
	}
}

