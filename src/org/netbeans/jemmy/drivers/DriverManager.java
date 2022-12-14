/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre Iline.
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 *
 *
 * $Id$ $Revision$ $Date$
 *
 */

package org.netbeans.jemmy.drivers;

import org.netbeans.jemmy.JemmyException;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * Manages driver set.
 */
public class DriverManager {
    /**
     * Symbolic constant - prefix for drivers names.
     */
    public static final String DRIVER_ID = "drivers.";
    /**
     * Symbolic constant for tree drivers.
     */
    public static final String TREE_DRIVER_ID = DRIVER_ID + "tree";
    /**
     * Symbolic constant for text drivers.
     */
    public static final String TEXT_DRIVER_ID = DRIVER_ID + "text";
    /**
     * Symbolic constant for key drivers.
     */
    public static final String KEY_DRIVER_ID = DRIVER_ID + "key";
    /**
     * Symbolic constant for mouse drivers.
     */
    public static final String MOUSE_DRIVER_ID = DRIVER_ID + "mouse";
    /**
     * Symbolic constant for scroll drivers.
     */
    public static final String SCROLL_DRIVER_ID = DRIVER_ID + "scroll";
    /**
     * Symbolic constant for button drivers.
     */
    public static final String BUTTON_DRIVER_ID = DRIVER_ID + "button";
    /**
     * Symbolic constant for list drivers.
     */
    public static final String LIST_DRIVER_ID = DRIVER_ID + "list";
    /**
     * Symbolic constant for multiselection list drivers.
     */
    public static final String MULTISELLIST_DRIVER_ID = DRIVER_ID + "multisellist";
    /**
     * Symbolic constant for reorderable list drivers.
     */
    public static final String ORDEREDLIST_DRIVER_ID = DRIVER_ID + "orderedlist";
    /**
     * Symbolic constant for table drivers.
     */
    public static final String TABLE_DRIVER_ID = DRIVER_ID + "table";
    /**
     * Symbolic constant for window drivers.
     */
    public static final String WINDOW_DRIVER_ID = DRIVER_ID + "window";
    /**
     * Symbolic constant for window drivers.
     */
    public static final String FRAME_DRIVER_ID = DRIVER_ID + "frame";
    /**
     * Symbolic constant for window drivers.
     */
    public static final String INTERNAL_FRAME_DRIVER_ID = DRIVER_ID + "internal_frame";
    /**
     * Symbolic constant for frame drivers.
     */
    public static final String FOCUS_DRIVER_ID = DRIVER_ID + "focus";
    /**
     * Symbolic constant for menu drivers.
     */
    public static final String MENU_DRIVER_ID = DRIVER_ID + "menu";

    //cannot be instantiated!
    private DriverManager() {
    }

    /**
     * Searches a driver.
     * @param id Driver type id.
     * @param operatorClass Class to get an driver for.
     * @param props Instance to get driver from.
     * @return a driver.
     * @see #setDriver
     */ 
    public static Object getDriver(String id, Class operatorClass, JemmyProperties props) {
	Object result = getADriver(id, operatorClass, props);
	if(result == null) {
	    return(getDriver(id, operatorClass));
	} else {
	    return(result);
	}
    }

    /**
     * Searches a driver. Uses <code>operator.getProperties()</code> to
     * receive JemmyProperties instance.
     * @param id Driver type id.
     * @param operator Operator to get an driver for.
     * @return a driver.
     * @see #setDriver
     */ 
    public static Object getDriver(String id, ComponentOperator operator) {
	return(getDriver(id, operator.getClass(), operator.getProperties()));
    }

    /**
     * Searches a driver.
     * Uses current JemmyProperties.
     * @param id Driver type id.
     * @param operatorClass Class to get an driver for.
     * @return a driver.
     * @see #setDriver
     */ 
    public static Object getDriver(String id, Class operatorClass) {
	Object result = getADriver(id, operatorClass, JemmyProperties.getProperties());
	if(result == null) {
	    throw(new JemmyException("No \"" + id + "\" driver registered for " +
				     operatorClass.getName() + " class!"));
	} else {
	    return(result);
	}
    }

    /**
     * Sets driver for an operator class.
     * @param id Driver type id.
     * @param driver A driver to be installed.
     * @param operatorClass Class to set driver for.
     * @see #getDriver
     */
    public static void setDriver(String id, Object driver, Class operatorClass) {
	JemmyProperties.
	    setCurrentProperty(makeID(id, operatorClass), driver);
	if(Boolean.getBoolean(DRIVER_ID + "trace_output")) {
	    JemmyProperties.getCurrentOutput().printLine("Installing " +
							 driver.getClass().getName() +
							 " drifer for " +
							 operatorClass.getName() +
							 " operators.");
	}
    }

    /**
     * Sets driver for an operator class name.
     * @param id Driver type id.
     * @param driver A driver to be installed.
     * @param operatorClassName A name of operator class.
     * @see #getDriver
     */
    public static void setDriver(String id, Object driver, String operatorClassName) {
	JemmyProperties.
	    setCurrentProperty(makeID(id, operatorClassName), driver);
	if(Boolean.getBoolean(DRIVER_ID + "trace_output")) {
	    JemmyProperties.getCurrentOutput().printLine("Installing " +
							 driver.getClass().getName() +
							 " drifer for " +
							 operatorClassName +
							 " operators.");
	}
    }

    /**
     * Sets driver for all classes supported by driver.
     * @param id Driver type id.
     * @param driver A driver to be installed.
     * @see #getDriver
     */
    public static void setDriver(String id, Driver driver) {
	Class[] supported = driver.getSupported();
	for(int i = 0; i < supported.length; i++) {
	    setDriver(id, driver, supported[i]);
	}
    }

    /**
     * Sets driver for all classes supported by driver.
     * @param id Driver type id.
     * @param driver A driver to be installed.
     * @see #getDriver
     */
    public static void setDriver(String id, LightDriver driver) {
	String[] supported = driver.getSupported();
	for(int i = 0; i < supported.length; i++) {
            setDriver(id, driver, supported[i]);
	}
    }

    /**
     * Removes driver for operator class.
     * @param id Driver type to remove.
     * @param operatorClass Class to remove driver for.
     */
    public static void removeDriver(String id, Class operatorClass) {
	JemmyProperties.
	    removeCurrentProperty(makeID(id, operatorClass));
	if(Boolean.getBoolean(DRIVER_ID + "trace_output")) {
	    JemmyProperties.getCurrentOutput().printLine("Uninstalling a drifer for " +
							 operatorClass.getName() +
							 " operators.");
	}
    }

    /**
     * Removes driver for operator class.
     * @param id Driver type to remove.
     * @param operatorClassName A name of operator class.
     */
    public static void removeDriver(String id, String operatorClassName) {
	JemmyProperties.
	    removeCurrentProperty(makeID(id, operatorClassName));
	if(Boolean.getBoolean(DRIVER_ID + "trace_output")) {
	    JemmyProperties.getCurrentOutput().printLine("Uninstalling a drifer for " +
							 operatorClassName +
							 " operators.");
	}
    }

    /**
     * Removes driver for operator classes.
     * @param id Driver type to remove.
     * @param operatorClasses Classes to remove driver for.
     */
    public static void removeDriver(String id, Class[] operatorClasses) {
	for(int i = 0; i < operatorClasses.length; i++) {
	    removeDriver(id, operatorClasses[i]);
	}
    }

    /**
     * Removes driver for operator classes.
     * @param id Driver type to remove.
     * @param operatorClassNames Names of operator classes.
     */
    public static void removeDriver(String id, String[] operatorClassNames) {
	for(int i = 0; i < operatorClassNames.length; i++) {
	    removeDriver(id, operatorClassNames[i]);
	}
    }

    /**
     * Removes driver for all supported classes.
     * @param id Driver type to remove.
     */
    public static void removeDrivers(String id) {
	String[] keys = JemmyProperties.getCurrentKeys();
	for(int i = 0; i < keys.length; i++) {
	    if(keys[i].startsWith(id)) {
		JemmyProperties.
		    removeCurrentProperty(keys[i]);
	    }
	}
    }

    /**
     * Returns <code>TREE_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setTreeDriver
     */
    public static TreeDriver getTreeDriver(Class operatorClass) {
	return((TreeDriver)getDriver(TREE_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>TREE_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setTreeDriver
     */
    public static TreeDriver getTreeDriver(ComponentOperator operator) {
	return((TreeDriver)getDriver(TREE_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>TREE_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getTreeDriver
     */
    public static void setTreeDriver(TreeDriver driver) {
	setDriver(TREE_DRIVER_ID, driver);
    }
    /**
     * Returns <code>TEXT_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setTextDriver
     */
    public static TextDriver getTextDriver(Class operatorClass) {
	return((TextDriver)getDriver(TEXT_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>TEXT_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setTextDriver
     */
    public static TextDriver getTextDriver(ComponentOperator operator) {
	return((TextDriver)getDriver(TEXT_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>TEXT_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getTextDriver
     */
    public static void setTextDriver(TextDriver driver) {
	setDriver(TEXT_DRIVER_ID, driver);
    }
    /**
     * Returns <code>KEY_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setKeyDriver
     */
    public static KeyDriver getKeyDriver(Class operatorClass) {
	return((KeyDriver)getDriver(KEY_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>KEY_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setKeyDriver
     */
    public static KeyDriver getKeyDriver(ComponentOperator operator) {
	return((KeyDriver)getDriver(KEY_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>KEY_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getKeyDriver
     */
    public static void setKeyDriver(KeyDriver driver) {
	setDriver(KEY_DRIVER_ID, driver);
    }
    /**
     * Returns <code>MOUSE_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setMouseDriver
     */
    public static MouseDriver getMouseDriver(Class operatorClass) {
	return((MouseDriver)getDriver(MOUSE_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>MOUSE_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setMouseDriver
     */
    public static MouseDriver getMouseDriver(ComponentOperator operator) {
	return((MouseDriver)getDriver(MOUSE_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>MOUSE_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getMouseDriver
     */
    public static void setMouseDriver(MouseDriver driver) {
	setDriver(MOUSE_DRIVER_ID, driver);
    }
    /**
     * Returns <code>SCROLL_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setScrollDriver
     */
    public static ScrollDriver getScrollDriver(Class operatorClass) {
	return((ScrollDriver)getDriver(SCROLL_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>SCROLL_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setScrollDriver
     */
    public static ScrollDriver getScrollDriver(ComponentOperator operator) {
	return((ScrollDriver)getDriver(SCROLL_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>SCROLL_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getScrollDriver
     */
    public static void setScrollDriver(ScrollDriver driver) {
	setDriver(SCROLL_DRIVER_ID, driver);
    }
    /**
     * Returns <code>BUTTON_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setButtonDriver
     */
    public static ButtonDriver getButtonDriver(Class operatorClass) {
	return((ButtonDriver)getDriver(BUTTON_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>BUTTON_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setButtonDriver
     */
    public static ButtonDriver getButtonDriver(ComponentOperator operator) {
	return((ButtonDriver)getDriver(BUTTON_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>BUTTON_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getButtonDriver
     */
    public static void setButtonDriver(ButtonDriver driver) {
	setDriver(BUTTON_DRIVER_ID, driver);
    }
    /**
     * Returns <code>LIST_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setListDriver
     */
    public static ListDriver getListDriver(Class operatorClass) {
	return((ListDriver)getDriver(LIST_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>LIST_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setListDriver
     */
    public static ListDriver getListDriver(ComponentOperator operator) {
	return((ListDriver)getDriver(LIST_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>LIST_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getListDriver
     */
    public static void setListDriver(ListDriver driver) {
	setDriver(LIST_DRIVER_ID, driver);
    }
    /**
     * Returns <code>MULTISELLIST_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setMultiSelListDriver
     */
    public static MultiSelListDriver getMultiSelListDriver(Class operatorClass) {
	return((MultiSelListDriver)getDriver(MULTISELLIST_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>MULTISELLIST_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setMultiSelListDriver
     */
    public static MultiSelListDriver getMultiSelListDriver(ComponentOperator operator) {
	return((MultiSelListDriver)getDriver(MULTISELLIST_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>MULTISELLIST_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getMultiSelListDriver
     */
    public static void setMultiSelListDriver(MultiSelListDriver driver) {
	setDriver(MULTISELLIST_DRIVER_ID, driver);
    }
    /**
     * Returns <code>ORDEREDLIST_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setOrderedListDriver
     */
    public static OrderedListDriver getOrderedListDriver(Class operatorClass) {
	return((OrderedListDriver)getDriver(ORDEREDLIST_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>ORDEREDLIST_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setOrderedListDriver
     */
    public static OrderedListDriver getOrderedListDriver(ComponentOperator operator) {
	return((OrderedListDriver)getDriver(ORDEREDLIST_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>ORDEREDLIST_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getOrderedListDriver
     */
    public static void setOrderedListDriver(OrderedListDriver driver) {
	setDriver(ORDEREDLIST_DRIVER_ID, driver);
    }
    /**
     * Returns <code>TABLE_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setTableDriver
     */
    public static TableDriver getTableDriver(Class operatorClass) {
	return((TableDriver)getDriver(TABLE_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>TABLE_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setTableDriver
     */
    public static TableDriver getTableDriver(ComponentOperator operator) {
	return((TableDriver)getDriver(TABLE_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>TABLE_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getTableDriver
     */
    public static void setTableDriver(TableDriver driver) {
	setDriver(TABLE_DRIVER_ID, driver);
    }
    /**
     * Returns <code>WINDOW_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setWindowDriver
     */
    public static WindowDriver getWindowDriver(Class operatorClass) {
	return((WindowDriver)getDriver(WINDOW_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>WINDOW_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setWindowDriver
     */
    public static WindowDriver getWindowDriver(ComponentOperator operator) {
	return((WindowDriver)getDriver(WINDOW_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>WINDOW_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getWindowDriver
     */
    public static void setWindowDriver(WindowDriver driver) {
	setDriver(WINDOW_DRIVER_ID, driver);
    }
    /**
     * Returns <code>FRAME_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setFrameDriver
     */
    public static FrameDriver getFrameDriver(Class operatorClass) {
	return((FrameDriver)getDriver(FRAME_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>FRAME_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setFrameDriver
     */
    public static FrameDriver getFrameDriver(ComponentOperator operator) {
	return((FrameDriver)getDriver(FRAME_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>FRAME_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getFrameDriver
     */
    public static void setFrameDriver(FrameDriver driver) {
	setDriver(FRAME_DRIVER_ID, driver);
    }
    /**
     * Returns <code>INTERNAL_FRAME_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setInternalFrameDriver
     */
    public static InternalFrameDriver getInternalFrameDriver(Class operatorClass) {
	return((InternalFrameDriver)getDriver(INTERNAL_FRAME_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>INTERNAL_FRAME_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setInternalFrameDriver
     */
    public static InternalFrameDriver getInternalFrameDriver(ComponentOperator operator) {
	return((InternalFrameDriver)getDriver(INTERNAL_FRAME_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>INTERNAL_FRAME_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getInternalFrameDriver
     */
    public static void setInternalFrameDriver(InternalFrameDriver driver) {
	setDriver(INTERNAL_FRAME_DRIVER_ID, driver);
    }
    /**
     * Returns <code>FOCUS_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setFocusDriver
     */
    public static FocusDriver getFocusDriver(Class operatorClass) {
	return((FocusDriver)getDriver(FOCUS_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>FOCUS_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setFocusDriver
     */
    public static FocusDriver getFocusDriver(ComponentOperator operator) {
	return((FocusDriver)getDriver(FOCUS_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>FOCUS_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getFocusDriver
     */
    public static void setFocusDriver(FocusDriver driver) {
	setDriver(FOCUS_DRIVER_ID, driver);
    }
    /**
     * Returns <code>MENU_DRIVER_ID</code> driver.
     * @param operatorClass Class to find driver for.
     * @return a driver
     * @see #setMenuDriver
     */
    public static MenuDriver getMenuDriver(Class operatorClass) {
	return((MenuDriver)getDriver(MENU_DRIVER_ID, operatorClass));
    }
    /**
     * Returns <code>MENU_DRIVER_ID</code> driver.
     * @param operator Operator to find driver for.
     * @return a driver
     * @see #setMenuDriver
     */
    public static MenuDriver getMenuDriver(ComponentOperator operator) {
	return((MenuDriver)getDriver(MENU_DRIVER_ID, operator.getClass()));
    }
    /**
     * Defines <code>MENU_DRIVER_ID</code> driver.
     * @param driver a driver
     * @see #getMenuDriver
     */
    public static void setMenuDriver(MenuDriver driver) {
	setDriver(MENU_DRIVER_ID, driver);
    }

    static void setDriver(String id, Object driver) {
        if(driver instanceof Driver) {
            setDriver(id, (Driver)driver);
        } else if(driver instanceof LightDriver) {
            setDriver(id, (LightDriver)driver);
        } else {
            throw(new JemmyException("Driver is neither Driver nor LightDriver " + 
                                     driver.toString()));
        }
    }

    //creates driver id
    private static String makeID(String id, Class operatorClass) {
	return(makeID(id, operatorClass.getName()));
    }

    private static String makeID(String id, String operatorClassName) {
	return(id + "." + operatorClassName);
    }

    //returns a driver
    private static Object getADriver(String id, Class operatorClass, JemmyProperties props) {
	Class superClass = operatorClass;
	Object drvr;
	do {
	    drvr = props.
		getProperty(makeID(id, superClass));
	    if(drvr != null) {
		return(drvr);
	    }
	} while(ComponentOperator.class.
		isAssignableFrom(superClass = superClass.getSuperclass()));
	return(null);
    }

    static {
	new InputDriverInstaller().install();
	new DefaultDriverInstaller().install();
    }
}
