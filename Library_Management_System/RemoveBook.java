import java.sql.*;
import java.io.*;
class RemoveBook
{
    String hist = "";
    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    String location = "jdbc:ucanaccess://E:\\Yashvardhan\\Java\\ICSE EXTERNAL PROJECT\\LibraryDatabase.accdb";
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    HistoryWrite HistoryWrite = new HistoryWrite();
    void removeBook(String bookname, int bookid)
    {
        try
        {
            Class.forName(driver);  
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  Books");
            while(rs.next())
            {
                if(rs.getInt("ID")==bookid)
                {
                    if(rs.getString("Book_Name").equalsIgnoreCase(bookname))
                    {
                        if(rs.getString("Status").equals("AVAILABLE"))
                        {
                            HistoryWrite.writeToFile(rs.getString("Book_Name") + "(" + bookid +  ") By " + rs.getString("Book_Author") + " Removed");
                            PreparedStatement ps = conn.prepareStatement("DELETE FROM Books WHERE ID = ?");
                            ps.setInt(1,bookid);
                            ps.executeUpdate(); 
                            System.out.print("\nBook Removed");
                        }
                        else
                        {
                            System.out.print("\nBook is borrowed");
                            System.out.print("\nDo you still want to remove the book?(Y/N):");
                            String c = br.readLine().toUpperCase().trim();
                            if(!c.equals(""))
                            {
                                if(c.charAt(0)=='Y')
                                {
                                    hist = rs.getString("Book_Name") + " (" + bookid + ") By " + rs.getString("Book_Author") +" Removed ";
                                    PreparedStatement ps = conn.prepareStatement("DELETE FROM Books WHERE ID = ?");
                                    ps.setInt(1,bookid);
                                    ps.executeUpdate(); 
                                    ps.close();
                                    memberUpdate(historyUpdate(bookid),bookid);
                                    System.out.print("\nBook Removed");
                                }
                                else
                                {
                                    System.out.print("\nBook Not Removed");
                                }
                            }
                            else
                            {
                                System.out.print("\nAnswer cannot be blank");   
                            }
                        }
                    }
                    break;
                }
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

    int historyUpdate(int bookid)
    {
        int memberid = 0;
        try
        {
            Class.forName(driver);  
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  History");
            while(rs.next())
            {
                if(rs.getInt("Book_ID")==bookid && rs.getString("Return_Date").equals("BORROWED"))
                {
                    memberid = rs.getInt("Member_ID");
                    rs.updateString("Return_Date","REMOVED");
                    rs.updateRow();
                    break;
                }
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
        finally
        {
            return memberid;
        }
    }

    void memberUpdate(int memberid, int bookid)
    {
        try
        {
            Class.forName(driver);  
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  Members");
            while(rs.next())
            {
                if(rs.getInt("ID")==memberid)
                {
                    hist = hist + "(Book With Borrower: " + rs.getString("Member_Name") + "(" + rs.getString("Member_ID") + "))"; 
                    HistoryWrite.writeToFile(hist);
                    for(int i = 1; i<=5; i++)
                    {
                        if(rs.getInt("Slot_"+i)==bookid)
                        {
                            rs.updateInt("Slot_"+i,0);
                            rs.updateRow();
                            break;
                        }
                    }
                    break;
                }
            }
            rs.close();
            st.close();
            conn.close();
            Sort Sort = new Sort();
            Sort.memberSort(memberid);
        }
        catch(Exception ex)
        {
            System.err.println("Exception: ");
            System.err.println(ex.getMessage());
        }
    }
}