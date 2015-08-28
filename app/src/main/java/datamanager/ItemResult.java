package datamanager;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Created by sharathsind on 2015-07-29.
 */
public class ItemResult implements Serializable,KvmSerializable {
 public    Item item;
  public  String [] images;
    public String []tags;
public  String username;
    @Override
    public String toString() {
        return "ItemResult{" +
                "item=" + item +
                ", images=" + Arrays.toString(images) +
                '}';
    }

    @Override
    public Object getProperty(int i) {
        switch (i)
        {
            case 0:
                return this.item;
            default:
                return this.images;

        }

    }

    @Override
    public int getPropertyCount() {
        return 2;
    }

    @Override
    public void setProperty(int i, Object o) {
switch(i)
{
    case 0:
        item=(Item)o;
        break;
    default:
        images=(String[])o;

}

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
switch (i)
{
    case 0:
        propertyInfo.setName("item");
        propertyInfo.setType(Item.class);
     break;
    case 1:
        propertyInfo.setName("item");
        propertyInfo.setType(String[].class);
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
