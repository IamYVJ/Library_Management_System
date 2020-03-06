import java.io.*;
class HistoryRead
{
    void readFromFile()
    {
        try
        {
            File file = new File("History.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            while(str!=null)
            {
                System.out.println(str);
                str = br.readLine();
            }
        }
        catch(Exception ex)
        {
            System.err.println("Exception: " + ex.getMessage());
        }
    }
}