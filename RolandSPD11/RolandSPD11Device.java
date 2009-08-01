package synthdrivers.RolandSPD11;

import core.Device;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import synthdrivers.RolandSPD11.PadInfo;
/**
 * RolandSPD11Device.java
 * Device class for Roland SPD-11 Total Percussion Pad.
 * @author <a href="mailto:peter.geirnaert@gmail.com">Peter Geirnaert</a>
 * @version $Id: RolandSPD11Device.java,v 0.1 ${date} ${time}
 */
public class RolandSPD11Device extends Device { 
    /** Array of all pads including inactive ones. */
    private PadInfo[] padinfo = {
    //          offset, DT pad, DT active,active
    new PadInfo("Int1",true),
    new PadInfo("Int2",true),
    new PadInfo("Int3",true),
    new PadInfo("Int4",true),
    new PadInfo("Int5",true),
    new PadInfo("Int6",true),
    new PadInfo("Int7",true),
    new PadInfo("Int8",true),
    new PadInfo("Ext1",false),
    new PadInfo("Ext2",false),
    new PadInfo("Ext3",false),
    new PadInfo("Ext4",false),
    };
    // Preferences node for persistent data
    private Preferences prefsPad;
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
    /**
     * Create a configuration pannel.
     *
     * @return a <code>JPanel</code> value
     */
    public JPanel config() {
    JPanel panel = new JPanel();
    GridBagLayout gridbag = new GridBagLayout();
    panel.setLayout(gridbag);
    GridBagConstraints c = new GridBagConstraints();
    //c.anchor = GridBagConstraints.WEST;
    //c.anchor = c.WEST;
    c.weightx = 1;
    //c.fill = c.HORIZONTAL;
    //c.gridwidth = 2;
    //c.gridheight = 1;

    // top labels
    c.gridx = 0; c.gridy = 0;
    panel.add(new JLabel("Pad Name"), c);
    c.gridx = 1; c.gridy = 0;
    panel.add(new JLabel("Enable"), c);
    c.gridx = 2; c.gridy = 0;
    panel.add(new JLabel("Dual Trigger"), c);

    for (int i = 0; i < padinfo.length; i++) {
        final Preferences p = prefsPad.node(padinfo[i].name);
        // pad name label
        c.gridx = 0; c.gridy = 1 + i;
        panel.add(new JLabel(padinfo[i].name), c);

        // check box for pad enable
        JCheckBox cbox = new JCheckBox();
        cbox.setSelected(p.getBoolean("padActive", padinfo[i].padActive));
        final int   index = i;
        cbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            JCheckBox cb = (JCheckBox) e.getSource();
            padinfo[index].padActive = cb.isSelected();
            p.putBoolean("padActive", cb.isSelected());
            }
        });
        c.gridx = 1; c.gridy = 1 + i;
        panel.add(cbox, c);


    }
    return panel;
    }
    
    /**
     * Return an array of PadInfo for pads which are enabled.
     *
     * @return a <code>PadInfo[]</code> value
     */
    PadInfo[] activePadInfo() {
    // count number of active pads
    int padNum = 0;
    for (int i = 0; i < padinfo.length; i++) {
        Preferences p = prefsPad.node(padinfo[i].name);
        if (p.getBoolean("padActive", padinfo[i].padActive)) {
        padNum++;
        }
    }

    // create array of padInfo
    PadInfo[] activePad = new PadInfo[padNum];

    // create array of active padInfo
    int n = 0;
    for (int i = 0; i < padinfo.length; i++) {
        Preferences p = prefsPad.node(padinfo[i].name);
        if (p.getBoolean("padActive", padinfo[i].padActive)) {
        PadInfo d = (PadInfo) padinfo[i].clone();
        d.padActive = true;
        activePad[n++] = d;
        }
    }
    return activePad;
    }
}
