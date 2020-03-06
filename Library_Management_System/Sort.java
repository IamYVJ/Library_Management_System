import java.sql.*;
class Sort
{
    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    String location = "jdbc:ucanaccess://E:\\Yashvardhan\\Java\\ICSE EXTERNAL PROJECT\\LibraryDatabase.accdb";
    void memberSort(int memberid)
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
                    int arr[] = new int [5];
                    for(int i = 1; i<=5; i++)
                    {
                        arr[i-1] = rs.getInt("Slot_"+i);
                    }
                    for(int i = 0; i<4; i++)
                    {
                        for(int j = 1; j<5; j++)
                        {
                            if(arr[i]<arr[j])
                            {
                                int t = arr[i];
                                arr[i] = arr[j];
                                arr[j] = t;
                            }
                        }
                    }
                    rs.updateInt("Slot_1",arr[0]);
                    rs.updateInt("Slot_2",arr[1]);
                    rs.updateInt("Slot_3",arr[2]);
                    rs.updateInt("Slot_4",arr[3]);
                    rs.updateInt("Slot_5",arr[4]);
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