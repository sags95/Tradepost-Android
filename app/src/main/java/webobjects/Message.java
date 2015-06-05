package webobjects;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by sharathsind on 2015-06-05.
 */
public class Message implements KvmSerializable {
String message;
String file;
    @Override
    public Object getProperty(int i) {
        switch ( i)
        {
            case 0: return  message;
            case 1:return file;

        }
        return null;
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
                message = o.toString();
                break;
            case 1:
                file = o.toString();
                break;

            default:
                break;
        }

    }




    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch(i)
        {
            case 0:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "msg";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "file";
                break;

            default:break;
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
