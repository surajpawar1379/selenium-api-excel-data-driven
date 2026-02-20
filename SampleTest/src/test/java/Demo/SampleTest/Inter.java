package Demo.SampleTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Inter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  String input = "madam1";
	        String output = "";
	        
	        for (int i =0; i<=input.length()-1; i++)
	        {
	            output = input.charAt(i)+output;
	        
	        }
	        System.out.println(output);
	  if (input.equals(output))
			  {
		  System.out.println("String is palandrome");
			  }
	  else
	  {
		  System.out.println("String is not a palandrome");
	  }
}
}





