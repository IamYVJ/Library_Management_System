import java.util.*;
import java.text.*;
class SystemDate 
{
    String date() 
    {
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("HH:mm:ss zzz',' EEEE, dd MMMM, yyyy");
        return ft.format(dNow);
    }
}