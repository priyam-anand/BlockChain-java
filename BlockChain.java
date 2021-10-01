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
		addNewBlock("1","0");
		this.dif=dif;
	}
	
	// add new block
	public Block addNewBlock(String proof,String prev_hash)
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
	public String getHashBlock(Block b)throws NoSuchAlgorithmException
	{
		String input = b.getIndex()+" "+b.getPrev_hash()+" "
						+b.getProof()+" "+b.getTimeNow();
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        byte arr[]=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
    	String hash=toHexString(arr);
    	
    	return hash;
	}
	
	// generate the proof of work to mine the current hash
	public String getProof(String prevProof)throws NoSuchAlgorithmException
	{
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        BigInteger currProof = new BigInteger("1",16);
        BigInteger prev = new BigInteger(prevProof,16);
        while(true)
        {
        	String input = getInput(currProof,prev);
        	byte arr[]=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
        	String hash=toHexString(arr);
        	if(isOk(hash,dif))
        	{
        		break;
        	}
        	currProof = currProof.add(new BigInteger("1"));
        }
        return currProof.toString(16);
	}
	// helper function for proof of work
	public boolean isOk(String str,int n)
	{
		for(int i=0;i<n;i++)
			if(str.charAt(i)!='0')
				return false;
		return true;
	}	
	public String getInput(BigInteger num1,BigInteger num2)
	{
		num2 = num2.subtract(num1);		
		return num2.toString(10);
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
		for(int i=1;i<chain.size();i++)
		{
			Block curr = chain.get(i);
			Block prev = chain.get(i-1);
			
			String input = getInput(new BigInteger(curr.getProof(),16),new 
					BigInteger(prev.getProof(),16));
	        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        	byte arr[]=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
        	String hash=toHexString(arr);
        	if(!isOk(hash,dif))
        	{
        		System.out.println("proof of work is incorrect");
        		return false;
        	}
        	
        	input = prev.getIndex()+" "+prev.getPrev_hash()+" "
					+prev.getProof()+" "+prev.getTimeNow();
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


//	public static void main(String[] args) {	
//		
//		chain = new ArrayList<>();
//		
//		// adding genesis block
//		addNewBlock("1","0");
//		
//		// adding 5 new blocks
//		for(int i=0;i<4;i++)
//		{
//			Block prev = getPreviouBlock();
//			try {
//				String proof = getProof(prev.getProof());
//				String prev_hash = getHashBlock(prev);
//				addNewBlock(proof, prev_hash);
//			}catch(Exception e) {
//				System.out.println(e);
//			}
//			
//		}
//		// printing the chain
//		printChain();
//		
////		explicitly changing a value in 1st block
//		
////		chain.set(0, new Block(0,"0","0",new Date()));		
////		System.out.println("changed chain");
////		printChain();
//		
//		// validating the chain
//		try {
//			boolean valid = isValid();
//			if(valid)
//				System.out.println("The BlockChain is Valid");
//			else
//				System.out.println("Not Valid");
//		}catch(Exception e) {
//			System.out.println(e);
//		}
//	}

	