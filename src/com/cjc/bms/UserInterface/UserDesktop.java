package com.cjc.bms.UserInterface;

import java.util.Scanner;
import com.cjc.bms.ServiceImpl.SBI;
import com.cjc.bms.ServiceI.RBI;
public class UserDesktop {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		RBI r=new SBI();
		int ch=0;
		
		while (ch!=6) {
			
			System.out.println("Enter 1 for Create account");
			System.out.println("Enter 2 for Viwe Detalis");
			System.out.println("Enter 3 fro Withdrawl Money");
			System.out.println("Enter 4 for Deposite Money");
			System.out.println("Enter 5 for Check Balance");
			System.out.println("Enetr 6  for Exist");
			
			System.out.println("Enetr Your Choice:-");
			ch=sc.nextInt();
			
			switch (ch) {
			case 1:
				r.createAccount();
				
				break;

			case 2:
				r.showDetails();
				break;
			case 3:
				r.withDrawlMoney();
				break;
			case 4:
				r.depositMoney();
				break;
			case 5:
				r.showBalance();
				break;
		    case 6:
					System.out.println("Thank you ");
					break;
			default:
				System.out.println("Enter valid No");
				
			}
	
		}
	}

}
