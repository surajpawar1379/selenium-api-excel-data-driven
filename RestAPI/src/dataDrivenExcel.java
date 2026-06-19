import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.Test;

import resources.BaseTest;
import resources.ConfigReader;

public class dataDrivenExcel extends BaseTest {

    @Test
    public void readExcelDataTest() throws IOException {
        dataDriven d = new dataDriven();
        ArrayList<String> data = d.getData("Add Profile");

        System.out.println("TestCase : " + data.get(0));
        System.out.println("Field 1  : " + data.get(1));
        System.out.println("Field 2  : " + data.get(2));
        System.out.println("Field 3  : " + data.get(3));
    }
}
