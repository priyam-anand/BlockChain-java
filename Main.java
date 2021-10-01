import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {

	static BlockChain bc;
	
	public static void main(String[] args)throws NoSuchAlgorithmException
	{
		FastReader f=new FastReader();
		System.out.println("Enter Difficulty for the BlockChain");
		int dif=f.nextInt();
		bc=new BlockChain(dif);
		boolean over=false;
		while(!over)
		{
			System.out.println("1. to add new block");
			System.out.println("2 to display the blockchain");
			System.out.println("3 check validity of blockchain");
			System.out.println("4 any other key to exit.");
			int input=f.nextInt();
			
			switch(input) {
				// add new block
				case 1:
					mineBlock();
					break;
				case 2:
					displayBlockChain();
					break;
				case 3:
					if(isBlockChainValid())
						System.out.println("Chain is Valid");
					else
						System.out.println("Chain is NOT Valid");
					break;
				default:
					over=true;
					break;
			}
			if(over)
				System.out.println("Breaking blockChain");
		}
		
		
	}

	public static boolean isBlockChainValid() throws NoSuchAlgorithmException
	{
		return bc.isValid();
	}
	
	public static void displayBlockChain()
	{
		bc.printChain();
		System.out.println();
	}
	
	public static void mineBlock() throws NoSuchAlgorithmException
	{
		Block previous_block = bc.getPreviouBlock();
		String prev_proof = previous_block.getProof();
		
		String curr_proof = bc.getProof(prev_proof);
		String prev_hash = bc.getHashBlock(bc.chain.get(bc.chain.size()-1));
		
		Block newBlock = bc.addNewBlock(curr_proof, prev_hash);
		System.out.println("successfully mined a new Block = "+newBlock.index+" "+newBlock.prev_hash+" "
				+newBlock.proof+" "+newBlock.timeNow);
		System.out.println();
	}
	
	
	static class FastReader 
	{ 
	    BufferedReader br; 
	    StringTokenizer st; 
	    
	    public FastReader() {
	    	br = new BufferedReader(new
	                 InputStreamReader(System.in)); 
	    }
	    String next() { 
	        while (st == null || !st.hasMoreElements()) { 
	            try{ 
	                st = new StringTokenizer(br.readLine()); 
	            } 
	            catch (IOException  e) { 
	                e.printStackTrace(); 
	            } 
	        } 
	        return st.nextToken(); 
	    } 
	    int nextInt() { 
	        return Integer.parseInt(next()); 
	    } 
	    long nextLong() { 
	        return Long.parseLong(next()); 
	    } 
	    double nextDouble() { 
	        return Double.parseDouble(next()); 
	    } 
	    String nextLine() { 
	        String str = ""; 
	        try{ 
	            str = br.readLine(); 
	        } 
	        catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	        return str; 
	    } 
	} 
}
