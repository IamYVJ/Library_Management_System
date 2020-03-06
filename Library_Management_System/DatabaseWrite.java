import java.sql.*;
import java.io.*;
class DatabaseWrite
{
    HistoryWrite HistoryWrite = new HistoryWrite();
    BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    String location = "jdbc:ucanaccess://E:\\Yashvardhan\\Java\\ICSE EXTERNAL PROJECT\\LibraryDatabase.accdb";
    void newBook(String book, String author)
    {
        try          
        {
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Books(Book_Name, Book_Author, Status) VALUES (?,?,?)");
            ps.setString (1,book);
            ps.setString (2,author);
            ps.setString(3,"AVAILABLE");
            ps.executeUpdate();
            ps.close();
            conn.close();
            Connection con=DriverManager.getConnection(location);
            Statement sta = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rss = sta.executeQuery("SELECT (*) FROM  Books");
            rss.last();
            int bookid = rss.getInt("ID");
            HistoryWrite.writeToFile("New Book: '" + book + "' (" + bookid + ") By '" + author + "' Added To Library");
            rss.close();
            sta.close();
            con.close();
            System.out.print("\n" + book + " has been added to the library"); 
        }
        catch(Exception ex)
        {
            System.err.println("Exception: ");
            System.err.println(ex.getMessage());
        }
    }

    void newMember(String name, char plan)
    {
        int n = 1;
        try          
        {
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Members(Member_Name, Member_ID, Membership_Plan," +
                    " Slot_1, Slot_2, Slot_3, Slot_4, Slot_5) VALUES (?,?,?,?,?,?,?,?)");
            ps.setString (1,name);
            ps.setString (2,(name.charAt(0)+""));
            String mplan = "";
            if(plan=='S')
                mplan = "SILVER";
            else if(plan == 'G')
            {
                mplan = "GOLD";
                n = 3;
            }
            else
            {
                mplan = "PLATINUM";
                n = 5;
            }
            ps.setString(3,mplan);
            for(int i = 1; i<=n; i++)
            {
                ps.setInt(i+3,0);
            }
            for(int i = 1; i<=5-n; i++)
            {
                ps.setInt(n+i+3,-1);
                
            }
            ps.executeUpdate();
            ps.close();
            conn.close();
        }
        catch(Exception ex)
        {
            System.err.println("Exception: ");
            System.err.println(ex.getMessage());
        }
        memCode();
    }

    void memCode()
    {
        try          
        {
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  Members");
            rs.last();
            int num = rs.getInt("ID");
            String name  = rs.getString("Member_Name");
            String code = name.charAt(0) + "" + num;
            HistoryWrite.writeToFile("New Member: " + name + " (" + code + ") Subscribed With " + rs.getString("Membership_Plan") + " Plan");
            rs.updateString("Member_ID",code);
            rs.updateRow();
            rs.close();
            st.close();
            conn.close();
            System.out.print("\nCongratulations! You are now a member!");
            System.out.print("\nYour Membership Code is " + code);
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

