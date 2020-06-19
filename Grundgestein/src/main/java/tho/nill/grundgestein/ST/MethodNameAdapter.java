package tho.nill.grundgestein.ST;

import java.lang.reflect.Method;
import java.util.Collection;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.Misc;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

public class MethodNameAdapter extends ObjectModelAdaptor {

	public MethodNameAdapter() {
		super();
	}

	@Override
	public Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName)
			throws STNoSuchPropertyException {
		if (o instanceof Collection<?> && "size".equals(propertyName)) {
			return ""+((Collection)o).size();
		}
		if ("this".contentEquals(propertyName)) {
			return o;
		}
		try {
			Method m = Misc.getMethod(o.getClass(), propertyName);
			if (m != null) {
				return m.invoke(o);
			}
		} catch (Exception e) {
			throwNoSuchProperty(o.getClass().getName() + "." + propertyName);
		}

		return super.getProperty(interp, self, o, property, propertyName);
	}

}
