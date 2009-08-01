package synthdrivers.RolandSPD11;

import core.TreeWidget;

public class Instrument implements TreeWidget.Nodes {
    private static final Object[] ROOT = {
    "Instrument",
    new Object[] {
      "Kick Drum",
      "Dance Kick", "Deep Kick", "Deep Reverb Kick", "Dry Kick",
      "Electronic Kick", "House Kick", "Mondo Reverb Kick", "Mondo Kick",
      "Mondo Deep Kick", "Pillow Kick", "Rap Kick", "Real Kick",
      "Reverb Kick", "Room Kick 1", "Room Kick 2", "Solid Kick",
      "TR-808 Kick", "TR-909 Kick", "Reverb Solid Kick", "808 Electronic Kick",
      "909 Hard Kick" },
    new Object[] {
      "Snare Drum (S)",
      "Acoustic Snare", "Big Shot Snare", "Brush Roll Snare 1", "Brush Roll Snare 2",
      "Brush Slap Snare 1", "Brush Slap Snare 2", "Brush Slap Snare 3", "Brush Swish Snare",
      "Cracker Snare", "Cruddy Snare", "Digital Snare", "Dopin' Snare",
      "Electronic Snare", "Fat Snare", "FX Snare", "House Snare",
      "House Dopin' Snare", "Hype Snare", "L.A. Snare", "L.A. Fat Snare",
      "Light Snare", "Loose Snare", "Rocker Snare", "Rockin' Snare",
      "Rock Light SNare", "Rock Rim Shot Snare", "Rock Splatter Snare", "Real Snare",
      "Reggae Snare 1", "Reggae Snare 2", "Ring Snare", "Rock Snare",
      "Roll Snare", "Splatter Snare", "Super Light Snare", "Super Whack Snare",
      "Swing Snare", "TR-808 Snare", "TR-909 Snare", "90's Snare",
      "Ambient Side Stick", "Hall Side Stick", "TR-808 Side Stick" },
    new Object[] {
      "Tom (t)",
      "Rock Tom 1", "Rock Tom 2", "TR-808 Tom", "Real Tom 1",
      "Real Tom 2", "Double Head Tom 1", "Double Head Tom 2", "Brush Slap Tom 1",
      "Brush Slap Tom 2", "Acoustic Tom 1", "Acoustic Tom 2" },
    new Object[] {
      "Hi-Hat Cymbal (H)",
      "Pop Closed Hi-Hat Inner", "Pop Closed Hi-Hat Outer", "Pop Open Hi-Hat Inner", "Pop Open Hi-Hat Outer",
      "Pop Pedal Hi-Hat", "Real Closed Hi-Hat Inner", "Real Closed Hi-Hat Outer", "Real Open Hi-Hat Inner",
      "Real Open Hi-Hat Outer", "Real Pedal Hi-Hat", "TR-808 Closed Hi-Hat Inner", "TR-808 Closed Hi-Hat Outer",
      "TR-808 Open Hi-Hat Inner", "TR-808 Open Hi-Hat Outer", "CR-78 Closed Hi-Hat", "CR-78 Open Hi-Hat",
      "Brush Closed Hi-Hat", "Brush Open Hi-Hat" },
    new Object[] {
      "Ride/Crash Cymbal (C)",
      "Crash Cymbal 1", "Crash Cymbal 2", "Chinese Cymbal 1", "Chinese Cymbal 2",
      "Brush Ride Cymbal", "Hand Cymbals", "Ride Cymbal", "Ride Bell Cymbal",
      "TR-808 Cymbal" },
    new Object[] {
      "Latin Percussion (L)",
      "Bongo High", "Bongo Low 1", "Bongo Low 2", "Conga High Mute",
      "Conga High Slap", "Conga High Open", "Conga Low Open 1", "Conga Low Open 2",
      "Cowbell 1", "Cowbell 2", "Claves 1", "Claves 2",
      "Guiro Short", "Guiro Long", "Maracas", "Shaker",
      "Tambourine", "Timbale High", "Timbale Low", "Timbale Paila",
      "Vibra-Slap", "Agogo", "Cabasa", "Cuica Mute 1",
      "Cuica Mute 2", "Cuica Open", "Pandiero Mute", "Pandiero Slap",
      "Pandiero Open 1", "Pandiero Open 2", "Surdo Rim", "Surdo Mute",
      "Surdo Open", "Tamborim 1", "Tamborim 2", "Whistle Short",
      "Whistle Long" },
    new Object[] {
      "Orchestral/Ethnic Percussion (P)",
      "Bell Tree", "Castanets", "Castanets with Hall Ambience", "Concert Bass Drum Mute",
      "Concert Bass Drum Open", "Gong large", "Gong Small", "Sleigh Bell",
      "Timpani Bend", "Triangle Mute", "Triangle Open", "Wood Block",
      "Chekere", "Djembe 1", "Djembe 2", "Djembe 3",
      "Talking Drum", "Tabla Na 1", "Tabla Na 2", "Tabla Tun",
      "Tabla Te", "Baya Ge", "Darbuk", "Monster Drum",
      "Taiko 1", "Taiko 2", "Taiko Rim", "Tsuzumi",
      "Can Drum", "Matsuri", "Rattle" },
    new Object[] {
      "Analog Percussion (A)",
      "DR-55 Claves", "CR-78 Cowbell", "CR-78 Metallic Beat", "CR-78 Guiro",
      "CR-78 Tambourine", "CR-78 Maracas", "TR-808 Conga", "TR-808-Claves",
      "TR-808 Maracas", "TR-808 Hand Clap", "TR-808 Cowbell" },
    new Object[] {
      "Melodic Percussion (M)",
      "Anvil", "Bamboo", "Drip", "Gamelan 1",
      "Gamelan 2", "Glass", "Glockenspiel", "Kalimba",
      "Log Drum", "Marimba 1", "Marimba 2", "Steel Drum 1",
      "Steel Drum 2", "Timpani", "Vibraphone", "Xylophone",
      "Brass Hit Short", "Brass Hit Long", "Orchestra Hit 1", "Orchestra Hit 2" },
    new Object[] {
      "Effect Sounds (E)",
      "Chink","Chop", "Crash 1", "Crash 2",
      "Drop", "Emergency", "Hand Clap 1", "Hand Clap 2",
      "High Q 1", "High Q 2", "Metal 1", "Metal 2",
      "Metal Side Stick", "Noise Accent 1", "Noise Accent 2", "Noise Accent 3",
      "Noise Accent 4", "Random Noise 1", "Random Noise 2", "Random Noise 3",
      "Random Noise 4", "Scratch Push", "Scratch Pull", "Scratch Stereo",
      "Shot 1", "Shot 2", "Shot 3", "Shot 4",
      "Snaps", "Stick Hit", "Uut?", "Woody 1",
      "Woody 2", "Kick Ambience", "Snare Ambience", "Tom Ambience",
      "Concert Ambience" },
    new Object[] {
      "Reversed Sounds (r)",
      "Reverse Ambience", "Reverse Beat", "Reverse Clap", "Reverse Cymbal",
      "Reverse High Q", "Reverse Kick", "Reverse Shot", "Reverse Snare",
      "Reverse Tom" },
    new Object[] {
      "Hi-Hat For Pedal Control (PH)",
      "Pop Hi-Hat Inner", "Pop Hi-Hat Outer", "Real Hi-Hat Inner", "Real Hi-Hat Outer",
      "Brush Hi-hat", "TR-808 Hi-Hat Inner", "TR-808 Hi-Hat Outer", "CR-78 Hi-Hat" },
    "Off"
  };
    private static final int[] OFFSET = {
    0,     // Kick Drum
    21,    // Snare Drum (S)
    64,    // Tom
    75,    // Hi Hat cymbal
    93,    // Ride/Crash Cymbal (C)
    102,   // Latin Percussion (L)
    139,   // Orchestral/Ethnic Percussion (P)
    170,   // Analog Percussion (A)
    181,   // Melodic Percussion (M)
    201,   // Effect Sounds (E)
    238,   // Reversed Sounds (r)
    247,   // Hi-Hat For Pedal Control (PH)
    255    // Off
};
    public Object[] getRoot() {
    return ROOT;
    }
 
    public int[] getIndices(int n) {
    int[] d;
    int i;
    for (i = 0; i < OFFSET.length - 1; i++) {
        if (n < OFFSET[i + 1]) {
        d = new int[] {i, n - OFFSET[i]};
        return d;
        }
    }
    // if (i != N_INSTRUMENT) throw error
    d = new int[] {i};
    return d;
    }
 
    public int getValue(int[] indices) {
    return (indices[0] < OFFSET.length - 1)
        ? OFFSET[indices[0]] + indices[1] : OFFSET[OFFSET.length - 1];
    }
}

    