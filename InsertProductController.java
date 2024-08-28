package ProductManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;
public class InsertProductController {
	public static void main(String[] args) {
		Connection connection=null;
		PreparedStatement stmt = null;
		Scanner sc=new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/practice_java";
			String userName="root";
			String pass="Root09**";
			connection = DriverManager.getConnection(url,userName,pass);
			while(true) {
				System.out.println("Press 1 to add the Product");
				System.out.println("Press 2 to Delete the Product");
				System.out.println("Press 3 to Update the Product");
				System.out.println("Press 4 to Display the Product");
				
				int n=sc.nextInt();
				
				switch(n) {
				case 1:
					System.out.println("ENTER THE NAME OF THE PRODUCT");
					sc.nextLine();
					String name=sc.nextLine();
					System.out.println("ENTER THE Price of the PRODUCT");
					double price=sc.nextDouble();
					System.out.println("Manufactured DATE WILL AUTOMATICALLY GENERATED");
					Date ab=getDate();
					System.out.println("Enter the PRODUCT ID");
					int prdId=sc.nextInt();
					
					
					String insert="insert into product (name,price,mfd,prd_id) values(?,?,?,?)";
					stmt=connection.prepareStatement(insert);
					stmt.setString(1,name);
					stmt.setDouble(2, price);
					stmt.setDate(3,ab);
					stmt.setInt(4, prdId);
					stmt.execute();
					break;
				case 2:
					System.out.println("ENTER THE Product ID OF THE PRODUCT");
					sc.nextLine();
					int a=sc.nextInt();
					String delete="delete from product where prd_id=?";
					stmt=connection.prepareStatement(delete);
					stmt.setInt(1, a);
					int b=stmt.executeUpdate();
					if(b>0) {
						System.out.println("Product Deleted");
					}
					else {
						System.out.println("Product not Found");
					}
					break;
				case 3:
					System.out.println("ENTER THE Product ID");
					sc.nextLine();
					int pr=sc.nextInt();
					System.out.println("ENTER THE NAME OF THE NEW PRODUCT");
					sc.nextLine();
					String name2=sc.nextLine();
					System.out.println("ENTER THE PRICE OF THIS PRODUCT");
					double price2=sc.nextDouble();
					System.out.println("THE MANUFACTURING DATE WILL AUTOMATICAllY GENERATED");
					String update="update product set name = ?, price = ?, Mfd = ? where prd_id = ?";
					stmt=connection.prepareStatement(update);
					stmt.setString(1, name2);
					stmt.setDouble(2, price2);
					stmt.setDate(3, getDate());
					stmt.setInt(4, pr);
					int c=stmt.executeUpdate();
					if(c>0) {
						System.out.println("PRODUCT UPDATED");
					}
					else {
						System.out.println("NO SUCH PRODUCT FOUND");
					}
					
					
					break;
				case 4:
					String display="Select * from product";
					stmt=connection.prepareStatement(display);
					ResultSet s=stmt.executeQuery();
					while(s.next()) {
						int id=s.getInt("id");
						String na=s.getString("name");
						double p=s.getDouble("price");
						Object d=s.getObject("Mfd");
						int prd=s.getInt("prd_id");
						
						System.out.println("----------------------");
						System.out.println("ID........ -> "+id);
						System.out.println("Name...... -> "+na);
						System.out.println("Price..... -> "+p);
						System.out.println("MFD....... -> "+d);
						System.out.println("Product ID -> "+prd);
						System.out.println("----------------------");
						System.out.println();
					}
					break;
					
					
				}
			}
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(connection!=null&&sc!=null&&stmt!=null) {
					connection.close();
					sc.close();
					stmt.close();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static Date getDate() {
		LocalDate ab=LocalDate.now();
		return Date.valueOf(ab);
	}

}
