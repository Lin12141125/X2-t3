import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Table1 {
	public static void main(String[] args) {
		Statement stmt=null;
		Connection conn=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/school","root","lyzhmlzq1217.");
			stmt=conn.createStatement();
			System.out.println("需要的帮助：1.查看；2.更新");
			System.out.println("退出：EXIT/exit");
			Scanner sc=new Scanner(System.in);
			String choice=new String("");
			while((choice=sc.next()).equalsIgnoreCase("exit")==false) {
				if(choice.equalsIgnoreCase("1")) {//查看信息
					System.out.println("选择需要查看的内容：1.学生信息；2.班级信息；3.登记表");
					int a=sc.nextInt();
					switch(a) {
					case 1:printStudent(stmt);break;
					case 2:printClass(stmt);break;
					case 3:printRegister(stmt);break;
					}
				}
				if(choice.equalsIgnoreCase("2")) {//更改信息
					System.out.println("选择需要操作的对象：1.学生；2.班级");
					int manipulate=sc.nextInt();
					if(manipulate==1) {
						System.out.println();
						printStudent(stmt);
						System.out.println("\n以上为现学生信息，请选择需要的操作：1.删除；2.更新学生信息");
						int m=sc.nextInt();
						if(m==1) {
							System.out.println("请选择需要删除的学生学号：");
							int sid1=sc.nextInt();
							String s="DELETE FROM student WHERE sid="+sid1+";";
							stmt.executeUpdate(s);
							System.out.println("学生信息已删除，现信息为：");
							printStudent(stmt);
						}
						if(m==2) {
							System.out.println("选择需要更新信息的学生学号：");
							int sid2=sc.nextInt();
							System.out.println("该学生信息为：");
							printStudentOneLine(stmt,"sid="+sid2);
							System.out.println("\n请选择需要更新的项目：1.学号；2.姓名；3.性别；4.班级；5.入班时间");
							int b=sc.nextInt();
							if(b==1) {
								System.out.println("请输入正确学号：");
								int newsid=sc.nextInt();
								String s="UPDATE student SET sid="+newsid+" WHERE sid="+sid2+";";
								stmt.executeUpdate(s);
								System.out.println("更新完成");
								printStudentOneLine(stmt,"sid="+newsid);
							}
							if(b==2) {
								System.out.println("请输入正确姓名：");
								String newName=sc.next();
								String s="UPDATE student SET name='"+newName+"' WHERE sid="+sid2+";";
								stmt.executeUpdate(s);
								System.out.println("更新完成");
								printStudentOneLine(stmt,"sid="+sid2);
							}
							if(b==3) {
								System.out.println("请输入正确性别（F/M)：");
								String newGender=sc.next();
								String s="UPDATE student SET gender='"+newGender+"' WHERE sid="+sid2+";";
								stmt.executeUpdate(s);
								System.out.println("更新完成");
								printStudentOneLine(stmt,"sid="+sid2);
							}
							if(b==4) {
								System.out.println("请输入正确班级：");
								String newClass=sc.next();
								String s="UPDATE student,register SET student.class='"+newClass+"',register.cid='"+newClass+"' WHERE student.sid="+sid2+" AND student.sid=register.sid;";
								stmt.executeUpdate(s);
								System.out.println("更新完成");
								printStudentOneLine(stmt,"sid="+sid2);
								printRegisterOneLine(stmt,"sid="+sid2);
							}
							if(b==5) {
								System.out.println("请输入正确入学时间：");
								String newDate=sc.next();
								String s="UPDATE student SET student.indate='"+newDate+"' WHERE student.sid="+sid2+";";
								stmt.executeUpdate(s);
								System.out.println("更新完成");
								printStudentOneLine(stmt,"sid="+sid2);
							}
						}
					}
					if(manipulate==2) {
						System.out.println();
						printClass(stmt);
						System.out.println("\n以上为现班级信息，请选择需要操作的班级：");
						String m=sc.next();
						System.out.println("该班级信息为：");
						printClassOneLine(stmt,"cid='"+m+"'");
						System.out.println("\n请选择所需要的操作：1.班级更名；2.更改班级所在专业");
						int c=sc.nextInt();
						if(c==1) {
							System.out.println("请输入新的班级名称：");
							String newCid=sc.next();
							String s="UPDATE class,student,register SET class.cid='"+newCid+"',student.class='"+newCid+"' WHERE class.cid='"+m+"'AND student.class='"+m+"' AND class.cid=register.cid;";
							stmt.executeUpdate(s);
							System.out.println("更新完成");
							printStudentOneLine(stmt,"class='"+newCid+"'");
							printClassOneLine(stmt,"cid='"+newCid+"'");
							printRegisterOneLine(stmt,"cid='"+newCid+"'");
						}
						if(c==2) {
							System.out.println("请输入新的班级专业：");
							String newProgram=sc.next();
							String s="UPDATE class SET program='"+newProgram+"' WHERE cid='"+m+"';";
							stmt.executeUpdate(s);
							System.out.println("更新完成");
							printClassOneLine(stmt,"cid='"+m+"'");
						}
					}
				}
				
				System.out.println("\n需要的帮助：1.查看；2.更新");
				System.out.println("退出：EXIT/exit");
				continue;
			}
						
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static void printStudent(Statement stmt) {
		if(stmt!=null) {
			ResultSet r1=null;
			try {
				r1=stmt.executeQuery("SELECT * FROM student");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("学号\t姓名\t性别\t班级\t入班时间");
			try {
				while(r1.next()) {
					System.out.print(r1.getInt(1)+"\t"+r1.getString(2)+"\t"+r1.getString(3)+"\t"+r1.getString(4)+"\t"+r1.getDate(5));
					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				r1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void printClass(Statement stmt) {
		if(stmt!=null) {
			ResultSet r1=null;
			try {
				r1=stmt.executeQuery("SELECT * FROM class");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("班级\t专业");
			try {
				while(r1.next()) {
					System.out.print(r1.getString(1)+"\t"+r1.getString(2));
					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				r1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void printRegister(Statement stmt) {
		if(stmt!=null) {
			ResultSet r1=null;
			try {
				r1=stmt.executeQuery("SELECT * FROM register");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("班级\t学生学号");
			try {
				while(r1.next()) {
					System.out.print(r1.getString(1)+"\t"+r1.getInt(2));
					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				r1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void printStudentOneLine(Statement stmt,String s) {
		if(stmt!=null) {
			ResultSet r1=null;
			try {
				r1=stmt.executeQuery("SELECT * FROM student where "+s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("学号\t姓名\t性别\t班级\t入班时间");
			try {
				while(r1.next()) {
					System.out.print(r1.getInt(1)+"\t"+r1.getString(2)+"\t"+r1.getString(3)+"\t"+r1.getString(4)+"\t"+r1.getDate(5));
					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				r1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void printRegisterOneLine(Statement stmt,String s) {
		if(stmt!=null) {
			ResultSet r1=null;
			try {
				r1=stmt.executeQuery("SELECT * FROM register where "+s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("班级\t学号");
			try {
				while(r1.next()) {
					System.out.print(r1.getString(1)+"\t"+r1.getInt(2));
					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				r1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void printClassOneLine(Statement stmt,String s) {
		if(stmt!=null) {
			ResultSet r1=null;
			try {
				r1=stmt.executeQuery("SELECT * FROM class where "+s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("班级\t专业");
			try {
				while(r1.next()) {
					System.out.print(r1.getString(1)+"\t"+r1.getString(2));
					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				r1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
