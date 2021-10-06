import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

public class BlockChain {

	public  List<Block> chain;
	public int dif;
	
	public BlockChain(int dif)
	{
		this.chain=new ArrayList<>();
		addNewBlock(1,"0");
		this.dif=dif;
	}
	
	// add new block
	public Block addNewBlock(int proof,String prev_hash)
	{		
		Block newBlock = new Block(chain.size(),proof,prev_hash,new Date());		
		chain.add(newBlock);	
		return newBlock;
	}
	
	// get previous block for its hash
	public Block getPreviouBlock()
	{
		return chain.get(chain.size()-1);
	}
	
	// get hash of the current block
	public String getHashBlock(Block b,Block b2)throws NoSuchAlgorithmException
	{
		
		String input = (b2.getProof()*b2.getProof()-b.getProof()*b.getProof())+"";
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        byte arr[]=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
    	String hash=toHexString(arr);
    	
    	return hash;
	}
	
	// generate the proof of work to mine the current hash
	public int getProof(int prevProof)throws NoSuchAlgorithmException
	{
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        int currProof=1;
        int prev = prevProof;
        while(true)
        {
        	int value=(prev*prev-currProof*currProof);
        	String input = value+"";
        	byte arr[]=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
        	String hash=toHexString(arr);
        	if(isOk(hash,dif))
        	{
        		break;
        	}
        	currProof++;
        }
        return currProof;
	}
	// helper function for proof of work
	public boolean isOk(String str,int n)
	{
		for(int i=0;i<n;i++)
			if(str.charAt(i)!='0')
				return false;
		return true;
	}	
	public String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16)); 
  
        while (hexString.length() < 64) 
            hexString.insert(0, '0'); 
        
        return hexString.toString(); 
    }	
	
	// print the chain
	public void printChain()
	{
		for(Block b:chain)
			System.out.println(b.getIndex()+" "+b.getPrev_hash()+" "+
					b.getProof()+" "+b.getTimeNow());
		System.out.println("length of the blockChain = "+chain.size());
	}
	
	// validate the entire chain
	public boolean isValid()throws NoSuchAlgorithmException
	{
		for(int i=2;i<chain.size();i++)
		{
			Block curr = chain.get(i);
			Block prev = chain.get(i-1);
			
			int value = (prev.getProof()*prev.getProof()-
					curr.getProof()*curr.getProof());
			
			String input = value+"";

	        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        	byte arr[]=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
        	String hash=toHexString(arr);
        	if(!isOk(hash,dif))
        	{
        		System.out.println("proof of work is incorrect");
        		return false;
        	}
        	
        	Block prevPrev = chain.get(i-2);
        	input = (prevPrev.getProof()*prevPrev.getProof()-
					prev.getProof()*prev.getProof()) + "";
        			
        	arr=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
        	hash=toHexString(arr);
        	
        	if(!curr.getPrev_hash().equalsIgnoreCase(hash))
        	{
        		System.out.println("prevHash does not match");
        		return false;
        	}
		}
		return true;
	}
}	
