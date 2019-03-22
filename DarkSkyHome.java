package framework.darksky;

import framework.AssertionPage;
import framework.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DarkSkyHome extends BasePage {

    AssertionPage assertionPage = new AssertionPage();

    private By websitesCurrentTime = By.xpath("//span[@class='Now']");
    private By hoursOnWebsite = By.xpath("//div[@class='hours']/span/span");
    private By todayTimeLine = By.xpath("//a[1]//span[2]//span[2]");


    private By minTemp1 = By.xpath("//a[@class='day revealed']//span[@class='minTemp']");
    private By maxTemp1 = By.xpath("//a[@class='day revealed']//span[@class='maxTemp']");

    private By minTemp2 = By.xpath("//div[@class='dayDetails revealed']//span[@class='highTemp swip']//following-sibling::span[@class='temp']");
    private By maxTemp2 = By.xpath("//div[@class='dayDetails revealed']//span[@class='lowTemp swap']//following-sibling::span[@class='temp']");


    private By websiteCurrentTemp = By.xpath("//span[@class='first']");
    private By websiteAllTemp = By.xpath("//div[@id='timeline']//div[@class='temps']/span/span");


    //TASK 1

    String now = getTextFromElement(websitesCurrentTime);

    public void printAllHours() {
        getTextFromElements(hoursOnWebsite);
    }

    public List expectedAllHours() {

//        creating my own array list
        ArrayList<String> expectedList = new ArrayList<>();
//        adding now from the website just in case tomorrow they change it to another word
        expectedList.add(now);


//      creating my calender object
        Calendar calendar = Calendar.getInstance();
//      setting the time to now which will return me everything even the grandchild's
        calendar.setTime(new Date());

//        i am setting up the format according to the website which is 12pm
        SimpleDateFormat sdf = new SimpleDateFormat("ha");

//         i am creating a for loop so that i can add 2 hours up to 11 times just like on the website without repeating myself
        for (int i = 0; i < 11; i++) {

//          i am adding my two hours to my current time
            calendar.add(Calendar.HOUR, 2);

//          first i am formatting my time to the pattern i wanted from sdf and i am putting it into a string for later use
            String time = sdf.format(calendar.getTime()).toString();

//          since my pattern returns me capital AM PM so i am using stringreplace format to change it from my arraylist to am pm and putting that into a string
            String formattedTime = time.replace("AM", "am").replace("PM", "pm");

//          now i am putting that string i created into my arraylist
            expectedList.add(formattedTime);
        }

//          now i am just printing my expectedarraylist

        return expectedList;
    }


    public void verifyHoursActualExpected() {
        assertionPage.verifyTwoLists(getTextFromElements(hoursOnWebsite), expectedAllHours());

        System.out.println(getTextFromElements(hoursOnWebsite));
        System.out.println(expectedAllHours());
    }


    // TASK 2


    public void scrollToTodayTimeline() throws InterruptedException {
        scrollIntoView(todayTimeLine);
        Thread.sleep(2000);
    }

    public void verifyLowestHighestTemp() {
        assertionPage.verifyErrorMessageByGettingTextLocators(minTemp1, minTemp2);
        assertionPage.verifyErrorMessageByGettingTextLocators(maxTemp1, maxTemp2);
        System.out.println(getTextFromElement(minTemp1));
        System.out.println(getTextFromElement(minTemp2));
        System.out.println(getTextFromElement(maxTemp1));
        System.out.println(getTextFromElement(maxTemp2));

    }


    //TASK 3

    public void verifyCurrentTemp() {

//      creating an arrayList to save all the numbers from the website, data type is interger because i want to save them and int
        ArrayList<Integer> intArray = new ArrayList<>();

//      putting it into a string and then transferring it into text and then replacing the degree with a blank string
        String currentTemp = getTextFromElement(websiteCurrentTemp).replace("°", "");

//      transferring the string into an int by using the method parseInt from Integer
        int ab = stringToInteger(currentTemp);
//      i am just printing the current temp to make sure its returning me number and the temp
        System.out.println("Current Temp is: " + ab);

//       same as last time i will do with the arraylist of the temperature list and then putting it into a string and removing the degree sign
//        and then adding it one by one to the arraylist by converting it to int
        for (WebElement element : webActions(websiteAllTemp)) {
            String str = element.getText().replace("°", "");
            int a = stringToInteger(str);
            intArray.add(a);
        }

//      going through the list of numbers and then whichever one is smallest saving it into minimum
        int minimum = intArray.get(0);
        findingSmallestFromArrayList(intArray, minimum);

//      going through the list of numbers and then whichever one is largest saving it into maximum
        int maximum = intArray.get(0);
        for (int a : intArray) {
            if (maximum < a) {
                maximum = a;
            }
        }


//      printing out minimum and maximum

        System.out.println("Minimum: " + minimum);
        System.out.println("Maximum: " + maximum);


//        creating the logic to see if my current temp is between the least and greatest by putting in two conditions
        if (ab >= minimum && ab <= maximum) {
            System.out.println("Current Temp is not greater or less than lowest and highest temp");
        }


    }

}
