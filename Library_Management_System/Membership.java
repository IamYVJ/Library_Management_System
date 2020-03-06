import java.sql.*;
import java.io.*;
class Membership
{
    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    String location = "jdbc:ucanaccess://E:\\Yashvardhan\\Java\\ICSE EXTERNAL PROJECT\\LibraryDatabase.accdb";
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    void alterMembership(String membername, String membercode, boolean task)
    {
        HistoryWrite HistoryWrite = new HistoryWrite();
        String hist = "";
        boolean flag = false;
        try          
        {
            Class.forName(driver);  
            Connection conn=DriverManager.getConnection(location);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = st.executeQuery("SELECT (*) FROM  Members");
            while(rs.next())
            {
                String memname = rs.getString("Member_Name");
                String memcode = rs.getString("Member_ID");
                if(memname.equals(membername) && memcode.equals(membercode))
                {
                    hist = memname + "(" + memcode + ") ";
                    flag = true;
                    String plan = "";
                    int n = 0;
                    for(int i = 1; i<=5; i++)
                    {
                        if(rs.getInt("Slot_"+i)>0)
                            n++;
                    }
                    if(task==true)
                    {
                        String memplan = rs.getString("Membership_Plan");
                        hist = hist + " Changed Plan From " + memplan + " To ";
                        System.out.print("\nYour Current Membership Plan: " + memplan);
                        int exhaust = 0;
                        System.out.print("\n MEMBERSHIP TYPES: ");
                        System.out.print("\n SILVER(S)    - Rs. 250/m (BORROW 1 BOOK AT A TIME)");
                        System.out.print("\n GOLD (G)     - Rs. 450/m (BORROW 3 BOOKS AT A TIME)");
                        System.out.print("\n PLATINUM (P) - Rs. 600/m (BORROW 5 BOOKS AT A TIME WITH SPECIAL BENEFITS)");
                        System.out.print("\nEnter Desired Membership Plan(S/G/P): ");
                        String mp = br.readLine().toUpperCase().trim();
                        if(!mp.equals(""))
                        {
                            char ch = mp.charAt(0);
                            if(ch==memplan.charAt(0))
                            {
                                System.out.print("\n" + memplan + " is already your plan");
                            }
                            else if(ch=='S')
                            {
                                if(n>1)
                                {
                                    exhaust = 1;
                                }
                                else
                                {
                                    plan = "SILVER";
                                    rs.updateString("Membership_Plan",plan);
                                    rs.updateInt("Slot_2",(-1));
                                    rs.updateInt("Slot_3",(-1));
                                    rs.updateInt("Slot_4",(-1));
                                    rs.updateInt("Slot_4",(-1));
                                    exhaust = -1;
                                }
                            }
                            else if(ch=='G')
                            {
                                if(n>3)
                                {
                                    exhaust = 3;
                                }
                                else
                                { 
                                    plan = "GOLD";
                                    rs.updateString("Membership_Plan",plan); 
                                    if(memplan.charAt(0)=='P')
                                    {
                                        rs.updateInt("Slot_4",(-1));
                                        rs.updateInt("Slot_5",(-1));
                                    }
                                    else 
                                    {
                                        rs.updateInt("Slot_2",(0));
                                        rs.updateInt("Slot_3",(0));  
                                    }
                                    exhaust = -1;
                                }
                            }
                            else if(ch=='P')
                            {
                                plan = "PLATINUM";
                                rs.updateString("Membership_Plan",plan);
                                if(memplan.charAt(0)=='S')
                                {
                                    rs.updateInt("Slot_2",(0));
                                    rs.updateInt("Slot_3",(0));
                                }
                                rs.updateInt("Slot_4",(0));
                                rs.updateInt("Slot_4",(0));  
                                exhaust = -1;
                            }
                            else
                            {
                                System.out.print("\nInvalid Membership Plan");
                            }
                            if(exhaust>0)
                            {
                                System.out.print("\nYou have " + n + " borrowed books");
                                System.out.print("\nYour desired plan allows only " + exhaust);
                                System.out.print("\nPlease return them before degrading your membership");
                            }
                            else if(exhaust==(-1))
                            {
                                hist = hist + plan;
                                rs.updateRow();
                                HistoryWrite.writeToFile(hist);
                                System.out.print("\nMembership Plan changed to " + plan + " successfully!");
                            }
                        }
                        else
                        {
                            System.out.print("\nPlan Cannot Be Blank");
                        }
                    }
                    else if(task==false)
                    {
                        if(n==0)
                        {
                            hist = hist + " Terminated Membership";
                            HistoryWrite.writeToFile(hist);
                            PreparedStatement ps = conn.prepareStatement("DELETE FROM Members WHERE Member_ID = ?");
                            ps.setString(1,memcode);
                            ps.executeUpdate(); 
                            System.out.print("\nMembership Terminated");
                        }
                        else
                        {
                            System.out.print("\nYou have " + n + " borrowed books");
                            System.out.print("\nPlease return them before terminating your membership");
                        }
                    }
                    break;
                }
            }
            if(flag==false)
                System.out.print("\nInvalid Membership details");
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