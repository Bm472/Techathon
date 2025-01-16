package uk.co.robertwalters.techathon;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseContent {

    private static String name;
    private static String desscription;

    private static ArrayList<String> coursePages = new ArrayList<>();
    private static short maxPages;
    private static short currentPage;
    private static float percentage;


    /* example "paragraphs".

        The paragraphs here need to be in sentence format to prevent negative space. Although not necessary, it will look more clean
     */
    static String cont1 = """
            What have you noticed today? I noticed that if you outline the eyes, nose, and mouth on your face with your finger, you make an "I" which makes perfect sense,but is something I never noticed before. What have you noticed today? /~
            He heard the crack echo in the late afternoon about a mile away. His heart started racingand he bolted into a full sprint. "It wasn't a gunshot, it wasn't a gunshot," he repeated under his breathlessness as he continued to sprint. /~
            This is important to remember. Love isn't like pie. You don't need to divide it among all your friends and loved ones. No matter how much love you give, you can always give more. It doesn't run out, so don't try to hold back giving it as if it may one day run out.

            This is important to remember. Love isn't like pie. You don't need to divide it among all your friends and loved ones. No matter how much love you give, you can always give more. It doesn't run out, so don't try to hold back giving it as if it may one day run out.

            This is important to remember. Love isn't like pie. You don't need to divide it among all your friends and loved ones. No matter how much love you give, you can always give more. It doesn't run out, so don't try to hold back giving it as if it may one day run out.
            """;


    // creates the courseDetails for the passed in class
    public CourseContent(String courseDetailName) {
        generateContentForClass(courseDetailName);

    }

    // getters and setters
    public String getName() {
        return name;
    }

    public String getDesscription() {
        return desscription;
    }

    public ArrayList<String> getCoursePages() {
        return coursePages;
    }

    public short getMaxPages() {
        return maxPages;
    }

    public short getCurrentPage() {
        return currentPage;
    }

    public float getPercentage() {
        return percentage;
    }
    // testing purposes
    public void generateContentForClass(String courseDetailName) {

        // ideally we would need the database here to get the course details.
        // whatever it returns
        this.name = courseDetailName;
        this.desscription = "description";

        // split-stream the string that is made,
        // using addAll or a loop to add all the content that would be shown
        coursePages.addAll(List.of(cont1.split("/~")));  // regex used within the expression
        this.maxPages = (short)coursePages.size();  // max size of the array list, which indicates the page numbers
        this.currentPage = (short)0;
        this.percentage = CalculatePercentage();
    }


    // here will alter the page number depending on what button is pressed, either forwards or backwards (relies on the boolean.
    public void TurnPage(boolean isForward) {
        if(isForward && currentPage < maxPages-1){
            currentPage++;
        }else if(!isForward){
            if(currentPage != 0) currentPage--;
        }

    }

    // this variable will calculate the percentage
    private float CalculatePercentage() {

        // creating a formatter to convert ot two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        System.out.println("current percent: "+percentage);

        System.out.println(currentPage + 1);
        System.out.println(maxPages);

        percentage = ((float)currentPage + 1.0f) / (float)maxPages * 100;
        percentage = Float.parseFloat(decimalFormat.format(percentage));    // formatting the decimal so it goes to 2dp
        System.out.println("percent: " +percentage);
        return percentage;
    }


}
