package synthdrivers.RolandSPD11;

import core.Device;
import java.util.prefs.Preferences;
/**
 * RolandSPD11Device.java
 * Device class for Roland SPD-11 Total Percussion Pad.
 * @author <a href="mailto:peter.geirnaert@gmail.com">Peter Geirnaert</a>
 * @version $Id: RolandSPD11Device.java,v 0.1 ${date} ${time}
 */
public class RolandSPD11Device extends Device {    
    private static final String infoText =
    "Here you can put some info about your driver and the usage like e.g.\n\n"
    + "the Pad driver can request inf about a single pad from the patch and pad that \n"
    + "you can choose out of the lists 'bank' and 'patch'\n"
    ;
    /** Constructor for DeviceListWriter. */
    public RolandSPD11Device() {
    super("Roland", "SPD11", null ,infoText,
        "Peter Geirnaert <peter.geirnaert@gmail.com>");
    };
  /** Constructor for actual work to be done.<br>
   * Creates a new <code>RolandSPD11Device</code> instance.<p>
   **/
    public RolandSPD11Device(Preferences prefs) {
    this();
    this.prefs = prefs;
    // Channel is used as Device ID, using value -1 assures the program will send to the right SPD11 in case there's more than one SPD11 in a setup
    setDeviceID(-1);
    // set the Channel by default at channel 10
    setChannel(10);
    // add single drivers  
    //**  
    SPD11PadDriver padDriver = new SPD11PadDriver();
    addDriver(padDriver);
    SPD11SettingsDriver settingsDriver = new SPD11SettingsDriver();
    addDriver(settingsDriver);
    SPD11SystemDriver systemDriver = new SPD11SystemDriver();
    addDriver(systemDriver);
    SPD11ChainDriver chainDriver = new SPD11ChainDriver();
    addDriver(chainDriver);
    //*/
    //add bank drivers
    //**
    SPD11PatchDriver patchDriver = new SPD11PatchDriver(padDriver,settingsDriver);
    addDriver(patchDriver);
    addDriver(new SPD11BankDriver(systemDriver,chainDriver,patchDriver));
    //*/   
    //add editors
    /** 
    addDriver(new SPD11PatchEditor());
    addDriver(new SPD11BankEditor());
    */
    }
}
