import java.sql.*;
import java.io.*;
class BorrowBook
{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    String location = "jdbc:ucanaccess://E:\\Yashvardhan\\Java\\ICSE EXTERNAL PROJECT\\LibraryDatabase.accdb";
    String memname;
    int memberID;
    void borrowBook(String bookname, int x)
    {
        boolean ch = false;
        try          
        {
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  Books");
            while(rs.next())
            {
                int n = rs.getInt("ID");
                String name  = rs.getString("Book_Name");
                String author = rs.getString("Book_Author");
                String stat = rs.getString("Status");
                if(x==n)
                {
                    if(bookname.equalsIgnoreCase(name))
                    {
                        if(stat.equals("AVAILABLE"))
                        {
                            ch = true;
                            System.out.print("\nEnter Today's Date (dd/mm/yyyy): ");
                            String dt = br.readLine().trim();
                            date date = new date();
                            if(date.dateCheck(dt))
                            {
                                if(memCheck(n))
                                {
                                    rs.updateString("Status",dt);
                                    rs.updateRow();
                                    System.out.print("\nYou have borrowed " + name + " by " + author);
                                    History History = new History();
                                    History.borrowHistory(memname,memberID,name,author,n,dt);
                                    HistoryWrite HistoryWrite = new HistoryWrite();
                                    HistoryWrite.writeToFile(memname + " (" + memname.charAt(0) + memberID + ") Borrowed " + name + " (" + n + ") By " + author + " [" + dt +"]"); 
                                    break;
                                }
                            }

                        }
                    }
                }
            }
            if(ch==false)
            {
                System.out.print("\nIncorrect Information Entered");
            }
            rs.close();
            st.close();
            conn.close();
        }
        catch(Exception ex)
        {
            System.err.println("Exception: ");
            System.err.println(ex.getMessage());
        }
    }

    boolean memCheck(int n)
    {
        boolean flag1 = false;
        boolean flag2 = false;
        try
        {
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  Members");
            System.out.print("\nEnter Membership Name: ");
            String name = br.readLine();
            System.out.print("\nEnter Membership ID: ");
            String code = br.readLine();
            while(rs.next())
            {
                memberID = rs.getInt("ID");
                memname = rs.getString("Member_Name");
                String memcod = rs.getString("Member_ID");
                if(name.equals(memname) && code.equals(memcod))
                {
                    flag1 = true;
                    String col = "Slot_";
                    for(int i = 1; i<=5; i++)
                    {
                        int c = rs.getInt(col+i);
                        if(c==0)
                        {
                            flag2 = true;
                            rs.updateInt(col+i,n);
                            rs.updateRow();
                            break;
                        }
                    }
                    break;
                }
            }
            if(flag1==false)
            {
                System.out.print("\nInvalid Membership Details");
            }
            if(flag2==false)
            {
                System.out.print("\nYou have reached your borrowing capacity");
                System.out.print("\nReturn borrowed books or upgrade your plan to borrow more books");
            }
            rs.close();
            st.close();
            conn.close();
        }
        catch(Exception ex)
        {
            System.err.println("Exception: ");
            System.err.println(ex.getMessage());
        }
        return flag2;
    }
}