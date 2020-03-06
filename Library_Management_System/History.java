import java.sql.*;
class History 
{
    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    String location = "jdbc:ucanaccess://E:\\Yashvardhan\\Java\\ICSE EXTERNAL PROJECT\\LibraryDatabase.accdb";
    void borrowHistory(String membername, int memberid, String bookname, String author, int bookid, String date)
    {
        try          
        {
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO History" + 
                    "(Member_Name, Member_ID,Book_Name, Book_Author, Book_ID, Borrow_Date, Return_Date) " + 
                    "VALUES (?,?,?,?,?,?,?)");
            ps.setString (1,membername);
            ps.setInt (2,memberid);
            ps.setString(3,bookname);
            ps.setString(4,author);
            ps.setInt(5,bookid);
            ps.setString(6,date);
            ps.setString(7,"BORROWED");
            ps.executeUpdate();
            ps.close();
            conn.close();
        }
        catch(Exception ex)
        {
            System.err.println("Exception: ");
            System.err.println(ex.getMessage());
        }
    }

    void returnHistory(int memberid, int bookid, String date)
    {
        try
        {
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  History");
            rs.first();
            while(rs.next())
            {
                int mID = rs.getInt("Member_ID");
                int bID  =rs.getInt("Book_ID");
                String retdat = rs.getString("Return_Date");
                if(memberid==mID && bookid==bID && retdat.equals("BORROWED"))
                {
                    rs.updateString("Return_Date",date);
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
    }
}
