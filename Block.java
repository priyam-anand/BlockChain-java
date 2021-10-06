import java.util.Date;

public class Block 
{
	int index,proof;
	String prev_hash;
	Date timeNow;
	
	public Block(int index,int proof,String prev_hash,Date time)
	{
		this.index=index;
		this.proof=proof;
		this.prev_hash=prev_hash;
		this.timeNow=time;
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

