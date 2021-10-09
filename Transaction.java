
public class Transaction {
	String sender,receiver;
	float amount;
	
	public Transaction(String sender, String receiver, float amount)
	{
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
	}

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}
	
	public float getAmount() {
		return amount;
	}	
}
