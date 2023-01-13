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
			System.out.println("��Ҫ�İ�����1.�鿴��2.����");
			System.out.println("�˳���EXIT/exit");
			Scanner sc=new Scanner(System.in);
			String choice=new String("");
			while((choice=sc.next()).equalsIgnoreCase("exit")==false) {
				if(choice.equalsIgnoreCase("1")) {//�鿴��Ϣ
					System.out.println("ѡ����Ҫ�鿴�����ݣ�1.ѧ����Ϣ��2.�༶��Ϣ��3.�ǼǱ�");
					int a=sc.nextInt();
					switch(a) {
					case 1:printStudent(stmt);break;
					case 2:printClass(stmt);break;
					case 3:printRegister(stmt);break;
					}
				}
				if(choice.equalsIgnoreCase("2")) {//������Ϣ
					System.out.println("ѡ����Ҫ�����Ķ���1.ѧ����2.�༶");
					int manipulate=sc.nextInt();
					if(manipulate==1) {
						System.out.println();
						printStudent(stmt);
						System.out.println("\n����Ϊ��ѧ����Ϣ����ѡ����Ҫ�Ĳ�����1.ɾ����2.����ѧ����Ϣ");
						int m=sc.nextInt();
						if(m==1) {
							System.out.println("��ѡ����Ҫɾ����ѧ��ѧ�ţ�");
							int sid1=sc.nextInt();
							String s="DELETE FROM student WHERE sid="+sid1+";";
							stmt.executeUpdate(s);
							System.out.println("ѧ����Ϣ��ɾ��������ϢΪ��");
							printStudent(stmt);
						}
						if(m==2) {
							System.out.println("ѡ����Ҫ������Ϣ��ѧ��ѧ�ţ�");
							int sid2=sc.nextInt();
							System.out.println("��ѧ����ϢΪ��");
							printStudentOneLine(stmt,"sid="+sid2);
							System.out.println("\n��ѡ����Ҫ���µ���Ŀ��1.ѧ�ţ�2.������3.�Ա�4.�༶��5.���ʱ��");
							int b=sc.nextInt();
							if(b==1) {
								System.out.println("��������ȷѧ�ţ�");
								int newsid=sc.nextInt();
								String s="UPDATE student SET sid="+newsid+" WHERE sid="+sid2+";";
								stmt.executeUpdate(s);
								System.out.println("�������");
								printStudentOneLine(stmt,"sid="+newsid);
							}
							if(b==2) {
								System.out.println("��������ȷ������");
								String newName=sc.next();
								String s="UPDATE student SET name='"+newName+"' WHERE sid="+sid2+";";
								stmt.executeUpdate(s);
								System.out.println("�������");
								printStudentOneLine(stmt,"sid="+sid2);
							}
							if(b==3) {
								System.out.println("��������ȷ�Ա�F/M)��");
								String newGender=sc.next();
								String s="UPDATE student SET gender='"+newGender+"' WHERE sid="+sid2+";";
								stmt.executeUpdate(s);
								System.out.println("�������");
								printStudentOneLine(stmt,"sid="+sid2);
							}
							if(b==4) {
								System.out.println("��������ȷ�༶��");
								String newClass=sc.next();
								String s="UPDATE student,register SET student.class='"+newClass+"',register.cid='"+newClass+"' WHERE student.sid="+sid2+" AND student.sid=register.sid;";
								stmt.executeUpdate(s);
								System.out.println("�������");
								printStudentOneLine(stmt,"sid="+sid2);
								printRegisterOneLine(stmt,"sid="+sid2);
							}
							if(b==5) {
								System.out.println("��������ȷ��ѧʱ�䣺");
								String newDate=sc.next();
								String s="UPDATE student SET student.indate='"+newDate+"' WHERE student.sid="+sid2+";";
								stmt.executeUpdate(s);
								System.out.println("�������");
								printStudentOneLine(stmt,"sid="+sid2);
							}
						}
					}
					if(manipulate==2) {
						System.out.println();
						printClass(stmt);
						System.out.println("\n����Ϊ�ְ༶��Ϣ����ѡ����Ҫ�����İ༶��");
						String m=sc.next();
						System.out.println("�ð༶��ϢΪ��");
						printClassOneLine(stmt,"cid='"+m+"'");
						System.out.println("\n��ѡ������Ҫ�Ĳ�����1.�༶������2.���İ༶����רҵ");
						int c=sc.nextInt();
						if(c==1) {
							System.out.println("�������µİ༶���ƣ�");
							String newCid=sc.next();
							String s="UPDATE class,student,register SET class.cid='"+newCid+"',student.class='"+newCid+"' WHERE class.cid='"+m+"'AND student.class='"+m+"' AND class.cid=register.cid;";
							stmt.executeUpdate(s);
							System.out.println("�������");
							printStudentOneLine(stmt,"class='"+newCid+"'");
							printClassOneLine(stmt,"cid='"+newCid+"'");
							printRegisterOneLine(stmt,"cid='"+newCid+"'");
						}
						if(c==2) {
							System.out.println("�������µİ༶רҵ��");
							String newProgram=sc.next();
							String s="UPDATE class SET program='"+newProgram+"' WHERE cid='"+m+"';";
							stmt.executeUpdate(s);
							System.out.println("�������");
							printClassOneLine(stmt,"cid='"+m+"'");
						}
					}
				}
				
				System.out.println("\n��Ҫ�İ�����1.�鿴��2.����");
				System.out.println("�˳���EXIT/exit");
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
			System.out.println("ѧ��\t����\t�Ա�\t�༶\t���ʱ��");
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
			System.out.println("�༶\tרҵ");
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
			System.out.println("�༶\tѧ��ѧ��");
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
			System.out.println("ѧ��\t����\t�Ա�\t�༶\t���ʱ��");
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
			System.out.println("�༶\tѧ��");
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
			System.out.println("�༶\tרҵ");
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
