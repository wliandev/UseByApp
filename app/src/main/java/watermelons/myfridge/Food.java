package watermelons.myfridge;
import java.util.Calendar;
import java.util.Date; //consider using Joda Time
import java.util.concurrent.TimeUnit;

public class Food {
    private String name;
    private Date dateBought;
    private Date dateExpires;
    private long days_until_expired;

    public Food (String name){
        this.name = name;
        dateBought = null;
        dateExpires = null;
        days_until_expired = -1;
    }

    public String getName() {
        return name;
    }

    public Date getDateBought() {
        return dateBought;
    }

    public Date getDateExpires(){
        return dateExpires;
    }

    public long getDays_until_expired(){
        return days_until_expired;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateBought(){
        dateBought = Calendar.getInstance().getTime(); //get Date time right now
    }

    public void setDateExpires(int days){
        Calendar exp = Calendar.getInstance(); //get calendar at Date time right now
        exp.setTime(dateBought);
        exp.add(Calendar.DATE, days);
        dateExpires = exp.getTime();
    }

    public void checkExpired(){ //runs every time pantry is reloaded
        Date today = Calendar.getInstance().getTime();
        long diffInMillies = Math.abs(dateExpires.getTime() - today.getTime());
        days_until_expired = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

    }

}
