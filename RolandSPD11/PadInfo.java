package synthdrivers.RolandSPD11;

public final class PadInfo implements Cloneable {
    /**
     * Name of Pad. "Kick", "Ride", etc.
     */
    final String name;
    /**
     * Enable or not.  Disable a pad which is not connected.
     */
    boolean padActive;
    /**
     * Creates a new <code>PadInfo</code> instance.
     *
     * @param name a <code>String</code> value.
     * @param offset an <code>int</code> value.
     * @param dualTrigger a <code>boolean</code> value.
     * @param dualTriggerActive a <code>boolean</code> value.
     * @param padActive a <code>boolean</code> value
     */
    public PadInfo(String name, boolean padActive) {
    this.name = name;
    this.padActive = padActive;
    } // PadInfo constructor
    // clone() must be defined since super.clone() is a protected method.
    public Object clone () {
    try {
        return super.clone ();
    } catch (CloneNotSupportedException e) {
        throw new InternalError(e.toString());
    }
    }
}
