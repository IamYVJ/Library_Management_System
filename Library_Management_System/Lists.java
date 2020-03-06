import java.sql.*;
class Lists
{
    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    String location = "jdbc:ucanaccess://E:\\Yashvardhan\\Java\\ICSE EXTERNAL PROJECT\\LibraryDatabase.accdb";
    void allList(String table, String column, String criteria, boolean condition)
    {
        String result;
        try
        {
            int n =1;
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(table.equals("Books") || table.equals("Members"))
                result = table + " ORDER BY " + table.substring(0,(table.length()-1)) + "_Name ASC";
            else
                result = table;
            ResultSet  rs = st.executeQuery("SELECT (*) FROM " + result);
            while(rs.next())
            {
                if(((condition)&&(rs.getString(column).equals(criteria))) || ((condition==false)&&(!(rs.getString(column)).equals(criteria))))
                {
                    if(table.equals("Members"))
                    {
                        System.out.print("\n  " + n + ")");
                        System.out.print("\tName of Member: "+rs.getString("Member_Name"));
                        System.out.print("\n    \tMembership ID  : "+rs.getString("Member_ID"));
                        System.out.print("\n    \tMembership Plan: "+rs.getString("Membership_Plan"));
                    }
                    else if(table.equals("Books"))
                    {
                        System.out.print("\n " + n + ")");
                        System.out.print("\tName of Book  : "+rs.getString("Book_Name"));
                        System.out.print("\n\tName of Author: "+rs.getString("Book_Author"));
                        System.out.print("\n\tBook ID       : "+rs.getInt("ID"));
                        if(!criteria.equals("AVAILABLE"))
                        {
                            String stat = rs.getString("Status");
                            if(!stat.equals("AVAILABLE"))
                                stat = "BORROWED";
                            System.out.print("\n\tBook Status   : "+stat);
                        }
                    }

                    else if(table.equals("History"))
                    {
                        System.out.print("\n\t->");
                        System.out.print("  Name of Member: " + rs.getString("Member_Name") + " ");
                        System.out.print( "(" + rs.getString("Member_Name").charAt(0) + rs.getInt("Member_ID") + ")");
                        System.out.print("\n\t    Name of Book  : " + rs.getString("Book_Name") + " (" + rs.getInt("Book_ID") + ")");
                        System.out.print("\n\t    Name of Author: " + rs.getString("Book_Author"));
                        System.out.print("\n\t    Borrow Date   : " + rs.getString("Borrow_Date"));
                        if(condition==false)
                            System.out.print("\n\t    Return Date   : " + rs.getString("Return_Date"));
                    }
                    System.out.print("\n");
                    n++;
                }       
            }
            rs.close();
            st.close();
            conn.close();
            if(n==1)
            {
                System.out.print("\n NO ITEMS PRESENT");
            }
        }

        catch(Exception ex)
        {
            System.err.println("Exception: ");
            System.err.println(ex.getMessage());
        }
    }
}