import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RuntimeExample { 
	public static void main(String[] args) {
		try {
			Process p =Runtime.getRuntime().exec("test.bat");
		    
		    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(),"EUC-KR"));
		    String line = null;
		    
		    while ((line = br.readLine()) != null) {
		      System.out.println(line);
		    }
			p.destroy();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
