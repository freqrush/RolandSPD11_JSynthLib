/*
 * Copyright 2009 Peter Geirnaert
 *
 * This file is part of JSynthLib.
 *
 * JSynthLib is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * JSynthLib is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JSynthLib; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package synthdrivers.RolandSPD11;

import core.*;

//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

//import javax.swing.JCheckBox;
//import javax.swing.JLabel;
//import javax.swing.JPanel;

import java.util.prefs.Preferences;

/**
 * RolandSPD11Device.java
 * Device class for Roland SPD-11 Total Percussion Pad.
 * @author <a href="mailto:peter.geirnaert@gmail.com">Peter Geirnaert</a>
 * @version $Id: RolandSPD11Device.java,v 0.1 ${date} ${time}
 */
public class RolandSPD11Device extends Device {
    //private patchNames[] patchnames;
        // Preferences node for persistent data
    //private Preferences prefsPad;
    private static final String infoText = SPD11_Constants.INFO_TEXT;
    /** Constructor for DeviceListWriter. */
    public RolandSPD11Device() {
    super("Roland", "SPD11", null ,infoText,
        "Peter Geirnaert <peter.geirnaert@gmail.com>");
    };
            /** Constructor for actual work to be done.<br>
    * Creates a new <code>RolandSPD11Device</code> instance.<p>
             *
             * @param prefs
             */
    public RolandSPD11Device(Preferences prefs) {
    this();
    this.prefs = prefs;
    //prefsPad = prefs.node("patchname");
    // Channel is used as Device ID, using value -1 assures the program will send to the right SPD11 in case there's more than one SPD11 in a setup
    setDeviceID(-1);
    // set the Channel by default at channel 10
    setChannel(10);
    // declare and instantiate drivers for Converters
    SPD11SettingsDriver settingsDriver = new SPD11SettingsDriver();
    SPD11PadDriver padDriver = new SPD11PadDriver();
    SPD11Patch2Driver patch2Driver = new SPD11Patch2Driver(padDriver,settingsDriver);
    SPD11ChainDriver chainDriver = new SPD11ChainDriver();
    SPD11SystemDriver systemDriver = new SPD11SystemDriver();

    // always first add converters!
    //SPD11CompletePatchConverter completePatchConverter = new SPD11CompletePatchConverter(pdDriver, sttngsDriver);
    //addDriver(completePatchConverter);
    //SPD11DumpAllConverter dumpAllConverter = new SPD11DumpAllConverter(ptch2Driver, chnDriver, sstmDriver);
    //addDriver(dumpAllConverter);

    // add single drivers
    addDriver(settingsDriver);
    addDriver(padDriver);
    addDriver(systemDriver);
    addDriver(chainDriver);
    addDriver(patch2Driver);

    //add bank drivers
    SPD11PatchDriver patchDriver = new SPD11PatchDriver(padDriver,settingsDriver);
    addDriver(patchDriver);
    SPD11DumpAllDriver dumpAllDriver = new SPD11DumpAllDriver(systemDriver,chainDriver,padDriver,settingsDriver,patch2Driver);
    addDriver(dumpAllDriver);
    }

}
