import java.sql.*;
import java.io.*;
class DatabaseRead
{
    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    String location = "jdbc:ucanaccess://E:\\Yashvardhan\\Java\\ICSE EXTERNAL PROJECT\\LibraryDatabase.accdb";
    boolean bookCheck(String bookname, boolean status)
    {
        boolean check = false;
        boolean av = false;
        try          
        {
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement();
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  Books");
            while(rs.next())
            {
                int n = rs.getInt("ID");
                String name  = rs.getString("Book_Name");
                String author = rs.getString("Book_Author");
                String stat = rs.getString("Status");
                if(bookname.equalsIgnoreCase(name))
                {
                    check = true;
                    if(stat.equals("AVAILABLE"))
                    {
                        System.out.print("\nID: " + n + "\tBook Name: " + name + "\tBook Author: " + author);
                        av = true;
                    }
                    else if(status)
                    {
                        System.out.print("\nID: " + n + "\tBook Name: " + name + "\tBook Author: " + author);   
                    }
                }
            }
            if(check==true && av==false)
            {
                System.out.print("\nBook is present but not currently available");
            }
            else if(check==false)
            {
                System.out.print("\nNo such book present in the library");
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
            if(status==false)
                return av;
            else
                return check;
        }
    }
}
 