package data;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by sharathsind on 2015-07-08.
 */
public class StringVector extends Vector<String> implements KvmSerializable {
    //n1 stores item namespaces:
  //  String n1 = "http://n1 ...";

    @Override
    public Object getProperty(int arg0) {
        return this.get(arg0);
    }

    @Override
    public int getPropertyCount() {
        return this.size();
    }

    @Override
    public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
        arg2.setName ( "string");
        arg2.type = PropertyInfo.STRING_CLASS;
    //    arg2.setNamespace = n1;
    }

    @Override
    public String getInnerText() {
        return null;
    }

    @Override
    public void setInnerText(String s) {

    }


    @Override
    public void setProperty(int arg0, Object arg1) {
        this.add(arg1.toString());
    }

}
