package com.cjc.bms.ServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.cjc.bms.DbConfiger.DbConnection;
import com.cjc.bms.ServiceI.RBI;
import com.cjc.bms.menumodel.Account;
//import com.mysql.cj.jdbc.PreparedStatementWrapper;

public class SBI implements RBI{
       Scanner sc =new Scanner(System.in);
      Account ac=new Account();
     //step 1-2 Load  the Driver And Establish the connection
     Connection con = DbConnection.getConnection();
	
	@Override
	public void createAccount() {
		
		System.out.println("Enter Ac No:-");
		ac.setAcno(sc.nextLong());
		
		System.out.println("Enter name:-");
		ac.setName(sc.next()+sc.nextLine());
		
		System.out.println("mob:-");
		ac.setMob(sc.nextLong());
		
		System.out.println("enter Adhar NO:-");
		ac.setAdharno(sc.nextLong());
		
		System.out.println("Enter age:-");
		ac.setAge(sc.nextInt());
		
		System.out.println("Enetr Balance:-");
		ac.setBalance(sc.nextDouble());
		
		
		
		//step 3 write the SqL query
		String s1="create table Account(acno int primary key, name varchar(25), mob bigint, adharno bigint, age int, balance decimal (10,2))";
		String s2="insert into Account value(?,?,?,?,?,?)";
		
		// step 4 Create the prepaed statement(I) Onject
	try {	
		PreparedStatement st1 = con.prepareStatement(s1);
		PreparedStatement st2 = con.prepareStatement(s2);
		
		
		st2.setLong(1, ac.getAcno());
		st2.setString(2, ac.getName());
		st2.setLong(3, ac.getMob());
		st2.setLong(4, ac.getAdharno());
		st2.setInt(5, ac.getAge());
		st2.setDouble(6, ac.getBalance());
		
		//step 5 Execute the Sql Query
		st1.executeUpdate();
		st2.execute();
		
		
		System.out.println("Account Details Fill Successfully");
		}

	catch(SQLException e) {
		
	System.out.println(e.getMessage());
	
	   }
	}
	
	@Override
	public void showDetails() {
		
		System.out.println("Enter ACcount no to fetch Details");
		int acno=sc.nextInt();
		
		// Step 3 Create Sql  Query
		String s1="select * from Account where acno=? ";
		
		try {
			//Step 4:create Prepared Statement
			PreparedStatement pd=con.prepareStatement(s1);
			pd.setInt(1, acno);
			
			
			//Step 5:Execute Query
			ResultSet rs = pd.executeQuery();
			if(rs.next()) {
				System.out.println("Account no:-"+rs.getLong("acno"));
				System.out.println("Name Is:-"+rs.getString("name"));
				System.out.println("Mob is:-"+rs.getLong("mob"));
				System.out.println("Addhar No:-"+rs.getLong("adharno"));
				System.out.println("Age is:-"+rs.getInt("age"));
				System.out.println("Balance is:-"+rs.getDouble("balance"));		
			}	
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
//		finally {
//			try {
//				con.commit();
//				con.close();
//				
//			} catch (SQLException e) {
//				System.out.println(e.getMessage());
//			}
//		}		
	}

	@Override
	public void withDrawlMoney() {
		System.out.println("Enetr Withdrawl Money");
		double wmoney=sc.nextDouble();
		
		System.out.println("Select *Account* to Withdrawl Amount");
		long ac=sc.nextLong();
		
		String se="SELECT balance from account where acno=? ";
		try {
			PreparedStatement pp=con.prepareStatement(se);
			pp.setLong(1, ac);
			
			ResultSet rs = pp.executeQuery();
			if(rs.next()) {
				
				double balance = rs.getDouble("balance");
				if(wmoney>0) {
					if(balance>=wmoney) {
						
						double newBalance=balance-wmoney;
						String up="UPDATE account set balance=? where acno=?";
						PreparedStatement pu=con.prepareStatement(up);
						pu.setDouble(1, newBalance);
						pu.setDouble(2, ac);
						int bup = pu.executeUpdate();
						
						if(bup>0) {
							
						   System.out.println("WithdrawlBalance Successful Current Balance Is:-"+newBalance);	
						}
												
					}
					else {						
						System.out.println("Insufficient balance ! Current balance"+balance);
					}
                       
					
				}
				
			}
				
			
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void depositMoney() {
		 try {
		        System.out.println("Enter Deposit Amount: ");
		        double amount = sc.nextDouble();

		        System.out.println("Enter Account Number For Deposit: ");
		        int acno = sc.nextInt();

		        if (amount > 0) {
		            
		            String select = "SELECT balance FROM account WHERE acno = ?";
		            PreparedStatement pst = con.prepareStatement(select);
		            pst.setInt(1, acno);
		            ResultSet rs = pst.executeQuery();

		            if (rs.next()) {
		                double balance = rs.getDouble("balance");
		                double newBalance = balance + amount;

		                
		                String update = "UPDATE account SET balance = ? WHERE acno = ?";
		                PreparedStatement pstUpdate = con.prepareStatement(update);
		                pstUpdate.setDouble(1, newBalance);
		                pstUpdate.setInt(2, acno);

		                int rows = pstUpdate.executeUpdate();
		                if (rows > 0) {
		                    System.out.println("Deposit Successful! New Balance: " + newBalance);
		                }
		            } else {
		                System.out.println("Account not found!");
		            }
		        } else {
		            System.out.println("Invalid Amount! Deposit must be greater than 0.");
		        }
		    } catch (SQLException e) {
		        System.out.println(e.getMessage());
		    }
		 catch(InputMismatchException e) {
			 System.out.println(e.getMessage());
		 }
		 finally
			{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
	}


		
	

	@Override
	public void showBalance() {
		try {
			System.out.println("Enter Account NUmber For View Balance :");
			int acno = sc.nextInt();
			
			String check = "select balance from account where acno=?";
			
			  PreparedStatement ps = con.prepareStatement(check);
			  ps.setLong(1, acno);
			  
			  ResultSet rs = ps.executeQuery();
			  if(rs.next()) {
				  double balance = rs.getDouble("balance");
				  System.out.println("Account Balance For Ac No " + acno + " is : " + balance);
			  }
			  else
			  {
				  System.out.println("Account Not Found!");
			  }
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			catch(InputMismatchException e)
			{
				System.out.println(e.getMessage());
			}
//			finally
//			{
//				try {
//					con.commit();
//					con.close();
//				
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}

	
	
	}
	}
