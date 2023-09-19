
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateCalendarInvite {
    public static void main(String[] args) {
        // Set the date and time of the event
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.MAY, 31, 15, 0, 0); // May 31st, 2023 at 3:00 PM EST
        calendar.setTimeZone(TimeZone.getTimeZone("America/New_York")); // set timezone to EST

        // Set the duration of the event in minutes
        int duration = 60;

        // Set the event details
        String eventName = "Memorial Day OOO";
        String eventDescription = "Today we are OOO in observance of Memorial Day.";
        String location = "USA";

        // Generate the iCalendar (.ics) file
        try {
            File icsFile = new File("example.ics");
            FileWriter writer = new FileWriter(icsFile);

            writer.write("BEGIN:VCALENDAR\r\n");
            writer.write("VERSION:2.0\r\n");
            writer.write("PRODID:-//ABC Corporation//NONSGML My Product//EN\r\n");
            writer.write("BEGIN:VEVENT\r\n");
            writer.write("UID:" + java.util.UUID.randomUUID().toString() + "\r\n");
            writer.write("DTSTAMP:" + getCurrentDateTimeInICalFormat() + "\r\n");
            writer.write("DTSTART:" + getICalDateTimeFormat(calendar.getTime()) + "\r\n");
            calendar.add(Calendar.MINUTE, duration);
            writer.write("DTEND:" + getICalDateTimeFormat(calendar.getTime()) + "\r\n");
            writer.write("SUMMARY:" + eventName + "\r\n");
            writer.write("DESCRIPTION:" + eventDescription + "\r\n");
            writer.write("LOCATION:" + location + "\r\n");
            writer.write("END:VEVENT\r\n");
            writer.write("END:VCALENDAR\r\n");

            writer.close();
            System.out.println("iCalendar file generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating iCalendar file: " + e.getMessage());
        }
    }

    private static String getCurrentDateTimeInICalFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date());
    }

    private static String getICalDateTimeFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }
}
