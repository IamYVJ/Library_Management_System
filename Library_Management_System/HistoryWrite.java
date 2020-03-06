import java.io.*;
class HistoryWrite
{
    void writeToFile(String s)
    {
        try
        {
            SystemDate SystemDate = new SystemDate();
            String dt = SystemDate.date();
            // creates a FileWriter Object
            FileWriter fw = new FileWriter("History.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            // Writes the content to the file 
            pw.println(dt + " - " + s);
            pw.close();
            bw.close();
            fw.close();
        }
        catch(Exception ex)
        {
            System.err.print("Exception: ");
            System.err.println(ex.getMessage());
        }
    }
}