import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

public class BlockChain {

	public  List<Block> chain;
	public int dif;
	public List<Transaction> txn;
	
	public BlockChain(int dif) throws NoSuchAlgorithmException
	{
		this.chain=new ArrayList<>();
		this.dif=dif;
		this.txn = new ArrayList<>();
		Block b=getGenesis();
		addNewBlock(b);
	}
	
	// add new block
	public Block addNewBlock(Block nb)
	{		
		txn=new ArrayList<>();
		chain.add(nb);
		return nb;
	}
	
	// get previous block for its hash
	public Block getPreviouBlock()
	{
		return chain.get(chain.size()-1);
	}
	
	// get hash of the current block
	public String getHashBlock(Block b)throws NoSuchAlgorithmException
	{
		String input = b.getIndex()+" "+b.getPrev_hash()+" "+b.getProof()+" "+b.getTimeNow()+" ";
		String txns=b.data;
		input+=txns;
		MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        byte arr[]=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
    	String hash=toHexString(arr);
    	
    	return hash;
	}
	
	// get genesis block
	public Block getGenesis() throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        int currProof=1;
        String prev_hash = "";
        for(int i=0;i<64;i++)
        	prev_hash+="0";
        Date date=new Date();
        Block b=null;
        while(true)
        {
        	date = new Date();
        	String input = "0 "+prev_hash+" "+currProof+" "+date+" ";
    		String txns="This is the first block.";
    		input+=txns;
     
        	byte arr[]=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
        	String hash=toHexString(arr);
        	if(isOk(hash,dif))
        	{
        		b=new Block(chain.size(),currProof,prev_hash,date,txns);
        		System.out.println("Mined Genesis Block with hash = "+hash);
        		break;
        	}
        	currProof++;
        }
        return b;
	}
	
	// generate the proof of work to mine the current hash
	public Block getProof()throws NoSuchAlgorithmException
	{
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        int currProof=1;
        String prev_hash = getHashBlock(getPreviouBlock());
        Date date=new Date();
        Block b=null;
        while(true)
        {
        	date = new Date();
        	String input = chain.size()+" "+prev_hash+" "+currProof+" "+date+" ";
    		String txns="";
    		for(Transaction t:txn)
    			txns+=(t.sender+" -> "+t.receiver+" = "+t.amount+"; ");
    		input+=txns;
        	
        	byte arr[]=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
        	String hash=toHexString(arr);
        	if(isOk(hash,dif))
        	{
        		b=new Block(chain.size(),currProof,prev_hash,date,txns);
        		break;
        	}
        	currProof++;
        }
        return b;
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
		{
			System.out.println("{");
			System.out.println("index : "+b.getIndex());
			System.out.println("previos_hash : "+b.getPrev_hash());
			System.out.println("nonce : "+b.getProof());
			System.out.println("timestamp : "+b.getTimeNow());
			System.out.println("data : {"+b.data+"}");
			System.out.println("}");
		}
					
		System.out.println("length of the blockChain = "+chain.size());
	}
	
	// validate the entire chain
	public boolean isValid()throws NoSuchAlgorithmException
	{
		Block first = chain.get(0);
		String hash = getHashBlock(first);
		if(!isOk(hash,dif))
			return false;
		for(int i=1;i<chain.size();i++)
		{
			Block curr = chain.get(i);
			Block prev = chain.get(i-1);
			
			// prevHash stored in curr block
			String prev_hash_stored = curr.prev_hash;
			String prev_hash = getHashBlock(prev);
			
			if(!prev_hash_stored.equals(prev_hash))
				return false;
			
			hash = getHashBlock(curr);
			if(!isOk(hash,dif))
        	{
        		System.out.println("proof of work is incorrect");
        		return false;
        	}
		}
		return true;
	}
	
	// add a new transaction to the list of transactions
	public void addTransaction(String sender,String recv,float amount)
	{
		Transaction t = new Transaction(sender,recv,amount);
		txn.add(t);
	}
	
	// print all the transactions in the current mempool
	public void printTransactions() {
		System.out.println("{");
		for(Transaction t:txn)
			System.out.println(t.getSender()+" -> "+t.getReceiver()+" = "+t.amount+";");
		System.out.println("}");
	}
	
}	
