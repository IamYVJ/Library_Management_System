class date
{
    int month[]={0,31,28,31,30,31,30,31,31,30,31,30,31};
    int isLeap(int y)
    {
        if((y%400==0) || ((y%100!=0)&&(y%4==0)))
            return 29;
        else
            return 28;
    }

 
    boolean dateValidate(int d, int m, int y)
    {
        month[2]=isLeap(y);
        if(m<1 || m>12 || d<1 || d>month[m] || y<1 || y>9999)
            return false;
        else
            return true;
    }

    int dayNo(int d, int m, int y)
    {
        int dn=0;
        month[2]=isLeap(y);
        for(int i=1;i<=m;i++)
        {
            dn=dn+month[i];
        }
        dn=dn+d;
        for(int i=1;i<=y;i++)
        {
            if(isLeap(i)==29)
                dn=dn+366;
            else
                dn=dn+365;
        }
        return dn;
    }

    boolean isInteger(String s) 
    {
        try 
        { 
            Integer.parseInt(s); 
        } 
        catch(NumberFormatException e) 
        { 
            return false; 
        } 
        catch(NullPointerException e) 
        {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    boolean dateCheck(String x)
    {
        int p=x.indexOf("/");
        int q=x.lastIndexOf("/");
        int j=0;
        for(int i=0;i<x.length();i++)
        {
            if(x.charAt(i)=='/')
            {
                j++;
            }
        }
        if (p!=-1&&q!=-1&&p!=q-1&&j==2)
        {
            if (isInteger(x.substring(0,p)))
            {
                if(isInteger(x.substring(p+1,q)))
                {
                    if(isInteger(x.substring(q+1)))
                    {
                        int d1=Integer.parseInt(x.substring(0,p));
                        int m1=Integer.parseInt(x.substring(p+1,q));
                        int y1=Integer.parseInt(x.substring(q+1));
                        if(dateValidate(d1,m1,y1)==true)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        System.out.print("\n INVALID DATE");
        return false;
    }

    void calc(String date1, String date2)
    {
        date1=date1.trim();
        int p,q;

        p=date1.indexOf("/");
        int d1=Integer.parseInt(date1.substring(0,p));

        q=date1.lastIndexOf("/");
        int m1=Integer.parseInt(date1.substring(p+1,q));

        int y1=Integer.parseInt(date1.substring(q+1));
        date2=date2.trim();
        p=date2.indexOf("/");
        int d2=Integer.parseInt(date2.substring(0,p));
        q=date2.lastIndexOf("/");
        int m2=Integer.parseInt(date2.substring(p+1,q));
        int y2=Integer.parseInt(date2.substring(q+1));

        FineCal FineCal = new FineCal();

        if(dateValidate(d1,m1,y1)==true && dateValidate(d2,m2,y2)==true)
        {
            int a=dayNo(d1,m1,y1);
            int b=dayNo(d2,m2,y2);
            System.out.print("\n BOOK WAS BORROWED FOR: "+Math.abs(a-b)+" DAYS");
            FineCal.fineCal(Math.abs(a-b));
        }
        else
            System.out.println("INVALID DATE");
    }

}