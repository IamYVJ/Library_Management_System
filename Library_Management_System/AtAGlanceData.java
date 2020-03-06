import java.sql.*;
class AtAGlanceData
{
    void atAGlanceData()
    {
        int hb = 0;
        int b = 0;
        int ab = 0;
        int hm = 0;
        int m = 0;
        int sm = 0;
        int gm = 0;
        int rvd = 0;
        int brw = 0;
        int rtn = 0;
        String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
        String location = "jdbc:ucanaccess://E:\\Yashvardhan\\Java\\ICSE EXTERNAL PROJECT\\LibraryDatabase.accdb";
        try
        {
            Class.forName(driver);  
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rsb = st.executeQuery("SELECT (*) FROM  Books");
            while(rsb.next())
            {
                b++;
                hb = rsb.getInt("ID");
                if(rsb.getString("Status").equals("AVAILABLE"))
                    ab++;
            }
            rsb.close();
            ResultSet  rsm = st.executeQuery("SELECT (*) FROM  Members");
            while(rsm.next())
            {
                m++;
                hm = rsm.getInt("ID");
                if(rsm.getString("Membership_Plan").equals("SILVER"))
                    sm++;
                else if(rsm.getString("Membership_Plan").equals("GOLD"))
                    gm++;
            }
            rsm.close();
            ResultSet  rsh = st.executeQuery("SELECT (*) FROM  History");
            while(rsh.next())
            {
                brw++;
                if(rsh.getString("Return_Date").equals("REMOVED"))
                    rvd++;
                else if(!rsh.getString("Return_Date").equals("BORROWED"))
                    rtn++;
            }
            rsh.close();
            st.close();
            conn.close();
        }
        catch(Exception ex)
        {
            System.err.println("Exception: " + ex.getMessage());
        }
        finally
        {
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  TOTAL BOOKS EVER: " + hb);
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  TOTAL BOOKS     : " + b);
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  BORROWED BOOKS  : " + (b-ab));
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  AVAILABLE BOOKS : " + ab);
            System.out.print("\n  ");
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  TOTAL MEMBERS EVER: " + hm);
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  TOTAL MEMBERS     : " + m);
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  MEMBERS WITH SILVER PLAN  : " + sm);
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  MEMBERS WITH GOLD PLAN    : " + gm);
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  MEMBERS WITH PLATINUM PLAN: " + (m-(gm+sm)));
            System.out.print("\n  ");
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  TOTAL BORROWS         : " + brw);
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  TOTAL RETURNS         : " + rtn);
            System.out.print("\n  ");
            for(long i = 0; i<=99999999;i++);
            System.out.print("\n  BOOROWED BOOKS REMOVED: " + rvd);
            System.out.print("\n  ");
            System.out.print("\n  ");
        }
    }
}