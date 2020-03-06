class FineCal
{
    void fineCal(int n)
    {
        if(n>7)
        {
            System.out.print("\n  YOU HAVE SUBMITTED THE BOOK " +(n-7) + " DAYS LATE");
            System.out.print("\n  FINE: " + ((n-7)*5) + " INR");
        }
    }
}
