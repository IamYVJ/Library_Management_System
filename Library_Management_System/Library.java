import java.io.*;
import java.util.*;
class Library 
{
    static Scanner sc = new Scanner (System.in);
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static date dw = new date();
    static DatabaseWrite DatabaseWrite = new DatabaseWrite();
    static DatabaseRead DatabaseRead = new DatabaseRead();
    static Lists Lists = new Lists();
    void head()
    {
        System.out.print("\f");
        System.out.print("\n ********************************************************************************");
        System.out.print("\n                           LIBRARY MANAGEMENT SYSTEM");
        System.out.print("\n ********************************************************************************");
        System.out.print("\n ");
        System.out.print("\n =================================================================================");
    }

    void borrow()throws IOException
    {
        System.out.print("\n                               BORROW A BOOK");
        System.out.print("\n =================================================================================");
        System.out.print("\n");
        System.out.print("\n ENTER THE NAME OF THE BOOK YOU WANT TO BORROW: ");
        String str=br.readLine().trim();
        if(!str.equals(""))
        {
            if(DatabaseRead.bookCheck(str,false))
            {
                System.out.print("\nEnter ID of the book you want to borrow: ");
                String s = br.readLine();
                if(dw.isInteger(s))
                {
                    int n = Integer.parseInt(s);
                    BorrowBook BorrowBook = new BorrowBook();
                    BorrowBook.borrowBook(str,n);
                }
                else
                {
                    System.out.print("\nInvalid ID");
                }
            }
        }
        else
        {
            System.out.print("\nName cannot be blank");
        }
    }

    void returnbook()throws IOException
    {
        System.out.print("\n                                  RETURN BOOK");
        System.out.print("\n =================================================================================");
        System.out.print("\n");
        System.out.print("\n ENTER THE NAME OF THE BOOK: ");
        String bookname = br.readLine();
        System.out.print("\n ENTER MEMBERSHIP NAME: ");
        String membername = br.readLine();
        System.out.print("\n ENTER MEMBERSHIP CODE: ");
        String membercode = br.readLine();
        if(!bookname.equals("") && !membername.equals("") && !membercode.equals(""))
        {
            ReturnBook ReturnBook = new ReturnBook();
            ReturnBook.returnBook(membername, membercode, bookname);
        }
        else
        {
            System.out.print("\nDetails cannot be blank");
        }
    }

    void availablebooks()
    {
        System.out.print("\n                               AVAILABLE BOOKS");
        System.out.print("\n =================================================================================");
        System.out.print("\n");
        Lists.allList("Books","Status","AVAILABLE",true);
    }

    void newbook()throws IOException
    {
        System.out.print("\n                               UPLOAD A NEW BOOK");
        System.out.print("\n ===============================================================================");
        System.out.print("\n  ");
        System.out.print("\n  ENTER BOOK NAME: ");
        String book = br.readLine();
        System.out.print("\n  ENTER AUTHOR   : ");
        String author = br.readLine();
        if(!book.equals("") && !author.equals(""))
        {
            DatabaseWrite.newBook(book,author);
        }
        else
            System.out.print("\nName cannot be blank");
    }

    void removebook() throws IOException
    {
        System.out.print("\n                               REMOVE A BOOK");
        System.out.print("\n =================================================================================");
        System.out.print("\n");
        System.out.print("\n ENTER THE NAME OF THE BOOK YOU WANT TO REMOVE: ");
        String str=br.readLine();
        if(!str.equals(""))
        {
            if(DatabaseRead.bookCheck(str,true))
            {
                System.out.print("\nEnter ID of the book you want to remove: ");
                String s = br.readLine();
                if(dw.isInteger(s))
                {
                    int n = Integer.parseInt(s);
                    RemoveBook RemoveBook = new RemoveBook();
                    RemoveBook.removeBook(str,n);
                }
                else
                {
                    System.out.print("\nInvalid ID");
                }
            }
        }
        else
        {
            System.out.print("\nName cannot be blank");
        }
    }

    void listofbooks()
    {
        System.out.print("\n                         LIST OF BOOKS");
        System.out.print("\n ================================================================================ ");
        System.out.print("\n  ");
        Lists.allList("Books","Status","BORROWED",false);
    }

    void newmember()throws IOException
    {
        System.out.print("\n                            NEW MEMBERSHIP");
        System.out.print("\n =============================================================================== ");
        System.out.print("\n  ");
        System.out.print("\n  ENTER NAME: ");
        String str = br.readLine();
        if(!str.equals(""))
        {
            int n = (int)((str.toUpperCase()).charAt(0));
            if(n>=65 && n<=90)
            {
                System.out.print("\n MEMBERSHIP TYPES: ");
                System.out.print("\n SILVER(S)    - Rs. 250/m (BORROW 1 BOOK AT A TIME)");
                System.out.print("\n GOLD (G)     - Rs. 450/m (BORROW 3 BOOKS AT A TIME)");
                System.out.print("\n PLATINUM (P) - Rs. 600/m (BORROW 5 BOOKS AT A TIME WITH SPECIAL BENEFITS)");
                System.out.print("\n ENTER YOUR MEMBERSHIP PLAN (S/G/P): ");
                String plan = br.readLine().toUpperCase();
                if(plan.equals(""))
                {
                    System.out.println("INVALID PLAN");
                }
                else if(plan.charAt(0) == 'S' || plan.charAt(0) == 'G' || plan.charAt(0) == 'P')
                {
                    DatabaseWrite.newMember(str,plan.charAt(0));
                }
                else
                {
                    System.out.println("INVALID PLAN");
                }
            }
            else
            {
                System.out.println("NAME SHOULD BEGIN WITH AN ALPHABET");
            }
        }
        else
        {
            System.out.println("NAME SHOULD BEGIN WITH AN ALPHABET");
        }
    }

    void listofmembers()
    {
        System.out.print("\n                               LIST OF MEMBERS");
        System.out.print("\n ================================================================================= ");
        System.out.print("\n  ");
        Lists.allList("Members","Membership_Plan","BRONZE",false);
    }

    void upgrademembership() throws IOException
    {
        System.out.print("\n                            UPGRADE MEMBERSHIP");
        System.out.print("\n ======================================================================= ");
        System.out.print("\n  ");
        System.out.print("\n ENTER MEMBERSHIP NAME: ");
        String membername = br.readLine();
        System.out.print("\n ENTER MEMBERSHIP CODE: ");
        String membercode = br.readLine();
        if(!membername.equals("") && !membercode.equals(""))
        {
            Membership Membership = new Membership();
            Membership.alterMembership(membername, membercode, true);
        }
        else
        {
            System.out.print("\nDetails cannot be blank");
        }
    }

    void listofborrowed()
    {
        System.out.print("\n                          LIST OF BORROWED BOOKS & BORROWERS");
        System.out.print("\n ================================================================================= ");
        System.out.print("\n  ");
        Lists.allList("History", "Return_Date", "BORROWED", true);
    }

    void historyBorrowsReturns()
    {
        System.out.print("\n                           HISTORY OF BORROWS & RETURNS");
        System.out.print("\n ================================================================================= ");
        System.out.print("\n  ");
        Lists.allList("History","Return_Date","RETURNED",false);
    }

    void terminatemembership()throws IOException
    {
        System.out.print("\n                             TERMINATE MEMBERSHIP");
        System.out.print("\n ================================================================================= ");
        System.out.print("\n  ");
        System.out.print("\n ENTER MEMBERSHIP NAME: ");
        String membername = br.readLine();
        System.out.print("\n ENTER MEMBERSHIP CODE: ");
        String membercode = br.readLine();
        if(!membername.equals("") && !membercode.equals(""))
        {
            Membership Membership = new Membership();
            Membership.alterMembership(membername, membercode, false);
        }
        else
        {
            System.out.print("\nDetails cannot be blank");
        }
    }

    void searchBook()throws IOException
    {
        System.out.print("\n                               SEARCH FOR A BOOK");
        System.out.print("\n ================================================================================= ");
        System.out.print("\n  ");
        System.out.print("\n ENTER THE NAME OF THE BOOK YOU WANT TO SEARCH FOR: ");
        String str=br.readLine();
        if(!str.equals(""))
        {
            DatabaseRead.bookCheck(str,true);
        }
        else
        {
            System.out.print("\nSEARCH NAME CANNOT BE BLANK");
        }
    }

    void allHistory()
    {
        System.out.print("\n                             LIBRARY HISTORY");
        System.out.print("\n ================================================================================= ");
        System.out.print("\n  ");
        HistoryRead HistoryRead = new HistoryRead();
        HistoryRead.readFromFile();
    }

    int displaymenu()
    {
        System.out.print("\n                               MAIN MENU");
        System.out.print("\n =================================================================================");
        System.out.print("\n");
        System.out.print("\n\t 1)   NEW BOOK");
        System.out.print("\n\t 2)   NEW MEMBER");
        System.out.print("\n\t 3)   SEARCH FOR A BOOK");
        System.out.print("\n\t 4)   BORROW A BOOK");
        System.out.print("\n\t 5)   RETURN A BOOK");
        System.out.print("\n\t 6)   REMOVE A BOOK");
        System.out.print("\n\t 7)   LIST OF BOOKS");
        System.out.print("\n\t 8)   LIST OF AVAILABLE BOOKS");
        System.out.print("\n\t 9)   LIST OF BORROWED BOOKS & BORROWERS");
        System.out.print("\n\t 10)  LIST OF MEMBERS");
        System.out.print("\n\t 11)  UPGRADE MEMBERSHIP");
        System.out.print("\n\t 12)  TERMINATE MEMBERSHIP  ");
        System.out.print("\n\t 13)  HISTORY OF BORROWS AND RETURNS");
        System.out.print("\n\t 14)  LIBRARY HISTORY");
        System.out.print("\n\t 15)  AT A GLANCE");
        System.out.print("\n\t 16)  ABOUT/CONTACT");
        System.out.print("\n\t 17)  EXIT");
        System.out.print("\n  ");
        System.out.print("\n =================================================================================");
        System.out.print("\n  ");
        System.out.print("\n  ENTER YOUR CHOICE: ");
        String n = sc.next();
        if(dw.isInteger(n))
            return Integer.parseInt(n);
        else
            return -1;
    }

    public static void main()throws IOException
    {
        Library obj = new Library();
        int n;char c;
        do
        {
            obj.head();
            n = obj.displaymenu();
            obj.head();
            switch (n)
            {
                case 1: obj.newbook();
                break;
                case 2: obj.newmember();
                break;
                case 3: obj.searchBook();
                break;
                case 4: obj.borrow();
                break;
                case 5: obj.returnbook();
                break;
                case 6: obj.removebook();
                break;
                case 7: obj.listofbooks();
                break;
                case 8: obj.availablebooks();
                break;
                case 9: obj.listofborrowed();
                break;
                case 10: obj.listofmembers();
                break;
                case 11: obj.upgrademembership();
                break;
                case 12: obj.terminatemembership();
                break;
                case 13: obj.historyBorrowsReturns();
                break;
                case 14: obj.allHistory();
                break;
                case 15: obj.ataglance();
                break;
                case 16: obj.about();
                break;
                case 17: 
                break;
                default:
                System.out.print("\n  ");
                System.out.print("\n INVALID INPUT, YOU MAY ENTER AGAIN");
            }
            if(n==17)
                break;
            System.out.print("\n");  
            System.out.print("\n");  
            System.out.print("\n  DO YOU WANT TO CONTINUE TO THE MAIN MENU? (Y/N): ");
            c = sc.next().charAt(0);
        }
        while (c=='y' || c =='Y');
        System.out.print("\n  ");
        System.out.print("\n  ARE YOU SURE YOU WANT TO QUIT? (Y/N): ");
        c = sc.next().charAt(0);
        if (c == 'y' || c == 'Y')
        {
            for(int l = 0;l<=8;l++)
            {
                obj.quitdisplay();
                for(long u = 1; u<=99999999;u++);
            }
            System.exit(0);
        }
        else
            main();
    }

    void quitdisplay()
    {
        System.out.print("\f\n  ");
        for(long u = 1; u<=9999999;u++);
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n\t\t ******************************************THANK YOU FOR USING THE CENTRAL LIBRARY MANAGEMENT SYSTEM************************************************ ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
    }

    void ataglance()
    {
        System.out.print("\n                                     AT A GLANCE");
        System.out.print("\n =============================================================================== ");
        System.out.print("\n  ");
        AtAGlanceData aagd = new AtAGlanceData();
        aagd.atAGlanceData();
    }

    void about()
    {
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        System.out.print("\n  ");
        About About = new About();
    }
}

