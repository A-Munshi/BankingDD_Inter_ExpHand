// 1. Custom Exceptions
class InsuffBalExp extends Exception 
{
	public InsuffBalExp(String msg) 
	{
		super(msg);
	}
}
class AccNotFoundExp extends Exception 
{
	public AccNotFoundExp(String msg) 
	{
		super(msg);
	}
}

// 2. Interface
interface BankOperations 
{
	void deposit(int accNo, double amount) throws AccNotFoundExp;
	void withdraw(int accNo, double amount) throws AccNotFoundExp, InsuffBalExp;
	void createDemandDraft(int accNo, String payee, double amount) throws AccNotFoundExp, InsuffBalExp;
	void viewAccounts();
}

// 3. Account Class
class Account 
{
	int accNo;
	String holderName;
	double balance;
	Account(int accNo, String holderName, double balance) 
	{
		this.accNo = accNo;
		this.holderName = holderName;
		this.balance = balance;
	}
	public String toString() 
	{
		return "Account No: " + accNo + ", Name: " + holderName + ", Balance: Rs. " + balance;
	}
}

// 4. Bank Implementation
class Bank implements BankOperations 
{
	Account[] accounts;
	int count;
	Bank(int size) 
	{
		accounts = new Account[size];
		count = 0;
	}
	
	void addAccount(Account acc) 
	{
		if (count < accounts.length) 
			accounts[count++] = acc;
		
		else
			System.out.println("Can't add more accounts.");
	}
	
	private Account findAccount(int accNo) throws AccNotFoundExp 
	{
		for (int i=0; i<count; i++) 
		{
			if (accounts[i].accNo == accNo) 
				return accounts[i];
		}
		throw new AccNotFoundExp("Account " + accNo + " not found!");
	}
	
	@Override
	public void deposit(int accNo, double amount) throws AccNotFoundExp 
	{
		Account acc = findAccount(accNo);
		acc.balance += amount;
		System.out.println("Deposited Rs. " + amount + " into " + acc.holderName + "'s account.");
	}
	 
	@Override
	public void withdraw(int accNo, double amount) throws AccNotFoundExp, InsuffBalExp 
	{
		Account acc = findAccount(accNo);
		if (acc.balance >= amount) 
		{
			acc.balance -= amount;
			System.out.println("Withdrawn Rs. " + amount + " from " + acc.holderName + "'s account.");
		} 
		else 
			throw new InsuffBalExp("Insufficient Balance in account " + accNo);
	}
	
	@Override
	public void createDemandDraft(int accNo, String payee, double amount) throws AccNotFoundExp, InsuffBalExp
	{
		Account acc = findAccount(accNo);
		if (acc.balance >= amount) 
		{
			acc.balance -= amount;
			System.out.println("DD created for Rs. " + amount + " in favor of " + payee + " from " + acc.holderName + "'s account.");
		} 
		else
			throw new InsuffBalExp("Insufficient Balance to create DD from account " + accNo);
	}
	
	@Override
	public void viewAccounts() 
	{
		System.out.println("\n--- Account Details ---");
		for (int i = 0; i < count; i++) 
			System.out.println(accounts[i]);
	}
}

//5. Main Application
public class BankingDD_Inter_ExpHand
{
	public static void main(String[] args) 
	{
		java.util.Scanner sc = new java.util.Scanner(System.in);
		Bank bank = new Bank(10);
		// Pre-loaded accounts
		bank.addAccount(new Account(101, "Anuvab", 20000));
		bank.addAccount(new Account(102, "Anurag", 15000));
		bank.addAccount(new Account(103, "Amit", 10000));
		int ch;
		do 
		{
			System.out.println("\n===== Banking Transaction Menu=====\n1. View Accounts\n2. Deposit\n3. Withdraw\n4. Create Demand Draft (DD)\n5. Exit");
			System.out.print("Enter your choice: ");
			ch = sc.nextInt();
			try 
			{
				switch(ch) 
				{
					case 1: bank.viewAccounts();	break;
					
					case 2: System.out.print("Enter Account No: ");
							int dAcc = sc.nextInt();
							System.out.print("Enter Amount: ");
							double dAmt = sc.nextDouble();
							bank.deposit(dAcc, dAmt);	break;
							
					case 3: System.out.print("Enter Account No: ");
							int wAcc = sc.nextInt();
							System.out.print("Enter Amount: ");
							double wAmt = sc.nextDouble();
							bank.withdraw(wAcc, wAmt);	break;
							
					case 4: System.out.print("Enter Account No: ");
							int ddAcc = sc.nextInt();
							sc.nextLine();
							System.out.print("Enter Payee Name: ");
							String payee = sc.nextLine();
							System.out.print("Enter Amount: ");
							double ddAmt = sc.nextDouble();
							bank.createDemandDraft(ddAcc, payee, ddAmt);	break;
							
					case 5: System.out.println("Exiting Banking App...");	break;
					
					default: System.out.println("Invalid choice!");
				}
			}
			catch (Exception e) 
			{
					System.out.println("Error: " + e.getMessage());
			}
		}while(ch != 5);
		sc.close();
	}
}
