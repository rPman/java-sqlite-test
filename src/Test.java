import java.sql.SQLException;
import java.util.Date;

public class Test
{

	public static void main(String[] args)
	{
		try
		{
			Database db=new Database();
			db.CreateDB();
			Date t1=new Date();
			for(int i=1000;i>0;i--) db.WriteDB();
			Date t2=new Date();
			db.ReadDB();
			Date t3=new Date();
			db.CloseDB();
			System.out.println("insert "+(t2.getTime()-t1.getTime())+"ms");
			System.out.println("select "+(t3.getTime()-t2.getTime())+"ms");
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
