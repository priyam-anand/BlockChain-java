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
		System.out.println("Mining the Genesis Block...");
		bc=new BlockChain(dif);
		boolean over=false;
		
		while(!over)
		{
			System.out.println("1. Add New Block");
			System.out.println("2. Add New Transaction");
			System.out.println("3. Display Blockchain");
			System.out.println("4. Display Mempool");
			System.out.println("5. Check Validity");
			int input=f.nextInt();
			
			switch(input) {
				case 1:
					mineBlock();
					break;
				case 2:
					System.out.println("Enter Sender Name");
					String sender = f.next();
					System.out.println("Enter Receiver Name");
					String rec = f.next();
					System.out.println("Enter Amount of haveCoin");
					float amt = (float)f.nextDouble();
					bc.addTransaction(sender, rec, amt);
					break;
				case 3:
					displayBlockChain();
					break;
				case 4:
					displayMempool();
					break;
				case 5:
					if(isBlockChainValid())
						System.out.println("The blockchain is Valid");
					else
						System.out.println("The blockchain is NOT Valid");
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
	
	public static void displayMempool()
	{
		bc.printTransactions();
		System.out.println();
	}
	
	public static void mineBlock() throws NoSuchAlgorithmException
	{
		
		Block newBlock = bc.getProof();
		bc.addNewBlock(newBlock);
		System.out.println("successfully mined a new Block with hash = "+bc.getHashBlock(newBlock));
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
