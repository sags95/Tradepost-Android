package datamanager;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by sharathsind on 2015-05-01.
 */
public class Item implements Serializable ,KvmSerializable {
    private Integer itemid;

    @Override
    public String toString() {
        return "Item{" + "itemid=" + itemid + ", itemname=" + itemname + ", description=" + description + ", dateadded=" + dateadded + ", latitude=" + latitude + ", longtitude=" + longtitude + ", dir=" + dir + ", userid=" + userid + ", category=" + category + ", con=" + con + '}';
    }
    private String itemname;
    private String description;
    private Date dateadded;
    private BigDecimal latitude;
    private BigDecimal longtitude;
    private String dir;
    private int userid;
    private String category;
    private int con;

    public Item() {
    }
public void set(SoapObject o)
{


            setItemid( Integer.parseInt(o.getPrimitivePropertyAsString("itemid")));


            setItemname( o.getPrimitivePropertyAsString("itemname"));


            setLatitude(new BigDecimal(
                    o.getPrimitivePropertyAsString("latitude")));

            setLongtitude(new BigDecimal( o.getPrimitivePropertyAsString("longtitude")));

            setDescription( o.getPrimitivePropertyAsString("description"));


            setCategory( o.getPrimitivePropertyAsString("category"));

            setCon ( Integer.parseInt(o.getPrimitivePropertyAsString("con")));
setUserid(Integer.parseInt(o.getPrimitivePropertyAsString("userid")));

            setDir( o.getPrimitivePropertyAsString("dir"));
 String date =  o.getPrimitivePropertyAsString("dateadded");
    date=date.split("T")[0];
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
   dateadded=     dateFormat.parse(date.trim());
    } catch (ParseException e) {
        e.printStackTrace();
    }
 //   setDateadded(new Date(date.split("T")[0]));


    }





    public Item(String itemname, String description, Date dateadded, BigDecimal latitude, BigDecimal longtitude, String dir, int userid, String category, int con) {
        this.itemname = itemname;
        this.description = description;
        this.dateadded = dateadded;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.dir = dir;
        this.userid = userid;
        this.category = category;
        this.con = con;
    }

    public Integer getItemid() {
        return this.itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }
    public String getItemname() {
        return this.itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDateadded() {
        return this.dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }
    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    public BigDecimal getLongtitude() {
        return this.longtitude;
    }

    public void setLongtitude(BigDecimal longtitude) {
        this.longtitude = longtitude;
    }
    public String getDir() {
        return this.dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public int getCon() {
        return this.con;
    }

    public void setCon(int con) {
        this.con = con;
    }

    public static  ItemResult getItem(SoapPrimitive object)
    {
        try {

            String b = object.getValue().toString();

            ItemResult obj = null;

            return obj;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    @Override
    public Object getProperty(int i) {
switch (i)
{
    case 0:
        return getItemid();
    case 1:
        return getItemname();
    case 2:
        return getLatitude();
    case 3:
        return getLongtitude();
    case 4:
        return  getDescription();
    case 5:
        return getCategory();
    case 6:
        return getCon();
    case 7:
        return getDir();
    default:
        return getDateadded();

}



      //  return null;
    }

    @Override
    public int getPropertyCount() {
        return 9;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                setItemid((int) o);
                break;
            case 1:
                setItemname((String) o);
                break;
            case 2:

                setLatitude(new BigDecimal(o.toString()));
                break;
            case 3:
                setLongtitude(new BigDecimal(o.toString()));
                break;
            case 4:
                setDescription((String) o);
                break;
            case 5:
                setCategory((String) o);
                break;
            case 6:
                 setCon ((int)o);
                break;
            case 7:
                 setDir((String) o);
                break;
            default:
               setDateadded(new Date(o.toString()));
                break;

        }

        }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.setName("itemid");
                propertyInfo.setType(itemid.getClass());
                break;
            case 1:
                propertyInfo.setName("itemname");
                propertyInfo.setType(itemname.getClass());
                break;
            case 2:

                propertyInfo.setName("latitude");
                propertyInfo.setType(latitude.getClass());
                break;
            case 3:
                propertyInfo.setName("longtitude");
                propertyInfo.setType(longtitude.getClass());
                break;
            case 4:
                propertyInfo.setName("description");
                propertyInfo.setType(description.getClass());
                break;
            case 5:
                propertyInfo.setName("category");
                propertyInfo.setType(category.getClass());
                break;
            case 6:
                propertyInfo.setName("con");
                propertyInfo.setType(propertyInfo.INTEGER_CLASS);
                break;
            case 7:
                propertyInfo.setName("dir");
                propertyInfo.setType(dir.getClass());
                break;
            default:
                propertyInfo.setName("dateadded");
                propertyInfo.setType(dateadded.getClass());
                break;

        }

    }

    @Override
    public String getInnerText() {
        return null;
    }

    @Override
    public void setInnerText(String s) {

    }
}
