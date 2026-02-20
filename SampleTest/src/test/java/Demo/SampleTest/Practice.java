package Demo.SampleTest;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Practice {

	public static void main(String arg[]) {
		//TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://cosmocode.io/automation-practice-webtable/");
		List<WebElement> rows = driver.findElements(By.tagName("tr"));
		for (WebElement row : rows)
		{
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells)
			{
				System.out.print(cell.getText() + " | ");
			}
			System.out.println("");
		}
				
		driver.quit(); 
		
		Ind();
		Pak();
	}
	
	static void Ind() {
		String input = "I love my country my india";
		String result = "";	
		
		String[] temp = input.split(" ");
		for (int i =0; i<temp.length; i++)
		{
			if(i==temp.length-1)
				result = temp[i]+result;
			else
				result = " " + temp [i] + result;			
		} 
		System.out.println(result);
	}
	
	
	static void Pak() {
		String input = "I hate pakistan"; 
		String output = "";
		
		String[] text = input.split(" ");
		for (int i=0; i<text.length; i++)
		{
			if(i == text.length-1)
				output = text[i]+output;			
		else
			output = " " + text[i] + output;
			
		}		
		System.out.println(output);;
	}	
}

