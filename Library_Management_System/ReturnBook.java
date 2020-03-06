import java.sql.*;
import java.io.*;
class ReturnBook
{    
    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    String location = "jdbc:ucanaccess://E:\\Yashvardhan\\Java\\ICSE EXTERNAL PROJECT\\LibraryDatabase.accdb";
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    void returnBook(String membername, String membercode, String bookname)
    {
        HistoryWrite HistoryWrite = new HistoryWrite();
        boolean memcheck = false;
        boolean bkcheck = false;
        boolean f = false;
        try          
        {
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sta = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  Members");
            ResultSet res = sta.executeQuery("SELECT (*) FROM Books");
            rs.beforeFirst();
            while(rs.next())
            {
                int memberID = rs.getInt("ID");
                String name  = rs.getString("Member_Name");
                String code = rs.getString("Member_ID");
                if(membername.equals(name) && membercode.equals(code))
                {
                    memcheck = true;
                    for(int i = 1; i<=5; i++)
                    {
                        int bkid = rs.getInt("Slot_"+i);
                        if(bkid>0)
                        {
                            res.beforeFirst();
                            while(res.next())
                            {
                                int n = res.getInt("ID");
                                if(n==bkid)
                                {
                                    String name1  = res.getString("Book_Name");
                                    if(name1.equalsIgnoreCase(bookname))
                                    {
                                        String author1 = res.getString("Book_Author");
                                        String stat1 = res.getString("Status");
                                        System.out.print("\n\t" + "ID         : " + n );
                                        System.out.print("\n\t" + "BOOK       : " + name1);
                                        System.out.print("\n\t" + "AUTHOR     : " + author1);
                                        System.out.print("\n\t" + "BORROW DATE: " + stat1);
                                        f = true;
                                    }
                                }
                            }
                        }
                        if(bkid==(-1))
                        {
                            break;
                        }
                    }
                    if(f==true)
                    {
                        System.out.print("\nEnter ID of the book you want to return: ");
                        String ss = br.readLine();
                        date dw = new date();
                        if(ss.equals(""))
                        {
                            System.out.print("\nID cannot be blank");
                        }
                        else if(dw.isInteger(ss))
                        {
                            int bkid1 = Integer.parseInt(ss);
                            res.beforeFirst();
                            while(res.next())
                            {
                                int bkid2 = res.getInt("ID");
                                String name1 = res.getString("Book_Name");
                                String author0 = res.getString("Book_Author");
                                if(bkid1==bkid2 && name1.equalsIgnoreCase(bookname))
                                {
                                    System.out.print("\nEnter Today's Date (dd/MM/yyyy): ");
                                    String td = br.readLine().trim();
                                    if(dw.dateCheck(td))
                                    {
                                        String stat2 = res.getString("Status");
                                        dw.calc(stat2, td);
                                        res.updateString("Status","AVAILABLE");
                                        res.updateRow();
                                        memReturn(memberID,bkid2);
                                        History History = new History();
                                        History.returnHistory(memberID, bkid2, td);
                                        Sort Sort = new Sort();
                                        Sort.memberSort(memberID);
                                        HistoryWrite.writeToFile(membername + " (" + membercode + ") Returned " + name1 + " (" + bkid2 + ") By " + author0 + " [" + td +"]");
                                    }
                                    break;
                                }
                            }
                        }
                        else
                        {
                            System.out.print("\nInvalid ID");
                        }
                    }
                    break;
                }
            }
            res.close();
            rs.close();
            sta.close();
            st.close();
            conn.close();
        }
        catch(Exception ex)
        {
            System.err.println("Exception: ");
            System.err.println(ex.getMessage());
        }
    }

    void memReturn(int memID, int bookID)
    {
        try
        {
            Class.forName(driver);
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  Members");
            rs.beforeFirst();
            while(rs.next())
            {
                int mID = rs.getInt("ID");
                if(mID==memID)
                {
                    for(int i = 1; i<=5; i++)
                    {
                        if(rs.getInt("Slot_"+i)==bookID)
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
        }
        catch(Exception ex)
        {
            System.err.println("Exception: ");
            System.err.println(ex.getMessage());
        }
    }
}
