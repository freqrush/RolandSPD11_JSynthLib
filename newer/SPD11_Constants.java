/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package synthdrivers.RolandSPD11;
import core.*;

/**
 *
 * @author peter
 */
public class SPD11_Constants {
    /**
     * Used by <code>PadParamSender#generate(value)</code>
     * @param paramsender a RolandDT1 sysex byte Array
     * @return the calculated checksum int value to be placed on paramsender.sysex[10]
     */
    static int calculateChecksum(byte[] paramsender){
        int sum = paramsender[6]+paramsender[7]+paramsender[8]+paramsender[9];
        return 128-(sum % 128);
    };

    /**
     * SPD11PadDriver CheckSum calculation replaces the offset byte<br>
     * with the result of the formula 128 -(sum % 128)
     * @param p the patch we work with
     * @param start is where the values start adding to the sum
     * @param end  is where the values stops adding to the sum
     * @param offset is where the checksum value is replaced
     * @author Peter Geirnaert
     */
    static void calculateChecksum(Patch p, int start, int end, int offset) {
        int sum=0x00;
        //add all values of address and data
        for(int i=start;i<offset;i++){
            sum += p.sysex[i];
        }
        //calculate checkSum with a simple formula
        int chkSm = 128-(sum % 128);
        p.sysex[offset] = (byte)chkSm;
    }
    /** which parameter is affected by the "Fx Time" setting, for label of "Fx Time" ScrollbarWidget. */
    protected static final String[] FX_PARAMS = {
        "Reverb Time", "Reverb Time", "Reverb Time", "Reverb Time", "Reverb Time",
        "Reverb Time", "Reverb Time", "Reverb Time", "Reverb Time", "Reverb Time",
        "Chorus Rate", "Reverb Time", "Reverb Time", "Reverb Time", "Flanger Rate",
        "Reverb Time", "Flanger Rate", "Delay Pitch", "Delay Rate", "Delay Time",
        "Delay Time", "Delay Time", "Delay Time", "Delay Time", "Delay Time"
    };
    /** all drumkits: 01~64*/
    protected static final String[] SPD11_PATCHES1 = DriverUtil.generateNumbers(1, 64, "##"); 
    /** all drumpads bank A + bank B */
    protected static final String[] SPD11_PADS = {
        "Fx & Pedal Settings",
        "A-int-1", "A-int-2", "A-int-3", "A-int-4",
        "A-int-5", "A-int-6", "A-int-7", "A-int-8",
        "A-kick-h1", "A-ext-h2", "A-ext-h3", "A-HH-h4",
        "A-kick-r1", "A-ext-r2", "A-ext-r3", "A-HH-r4",
        "B-int-1", "B-int-2", "B-int-3", "B-int-4",
        "B-int-5", "B-int-6", "B-int-7", "B-int-8",
        "B-kick-h1", "B-ext-h2", "B-ext-h3", "B-HH-h4",
        "B-kick-r1", "B-ext-r2", "B-ext-r3", "B-HH-r4"
    };
    /** fx types*/
    protected static final String[] SPD11_FXTYPES = {
        "1.Room (Bright)", "2.Room (Standard)", "3.Room (Dark)",
        "4.Hall (Bright)", "5.Hall (Standard)", "6.Hall (Dark)",
        "7.Plate (Bright)", "8.Standard Plate", "9.Chorus+Reverb",
        "10.Tremolo Reverb", "11.Chorus", "12.Chorus+Room",
        "13.Chorus+Hall", "14.Chorus+Plate", "15.Flanger",
        "16.Flanger Reverb", "17.Flanger+Reverb", "18.Pitched Delay+Reverb",
        "19.Pitched Delay+Reverb", "20.Stereo Delay", "21.Stereo Delay",
        "22.Panning Delay", "23.Panning Delay", "24.Chorus+Delay",
        "25.Chorus+Delay"
    };
    /** pedal control uses*/
    protected static final String[] SPD11_PEDALCONTROL = {
        "HiHat control" , "FxSend" , "Up a fifth" , "Up 1 octave" ,
        "Up 2 octaves" , "Down a fourth" , "Down 1 octave" , "Down 2 octaves"
    };
    /** text for the about dialog for this device. */
    protected static final String INFO_TEXT =
        "This is the driver for Roland SPD-11\n .-<<## Total Percussion Pad ##>>-.\n\n"
        +"You can save and load \n\n"
        +"SystemSettings, ChainSetups, Patches, Pads, EffectSettings, \n"
        +"and the complete state of your SPD-11, called a 'Bank'.\n\n"
        +"Faderbanks are asigned to 'level' (1->32), 'pan'(33->64) and 'FxSend'(65->96)"
        +"For any questions,\n"
        +"Contact me: peter.geirnaert@gmail.com\n"
        +"or post to the JSynthLib mailinglist";
     /** for use with a simple ComboBoxWidget */
     protected static final String[] INSTRUMENTS = {
         // "Kick Drum" (b):
        "Dance Kick", "Deep Kick", "Deep Reverb Kick", "Dry Kick",
        "Electronic Kick", "House Kick", "Mondo Reverb Kick", "Mondo Kick",
        "Mondo Deep Kick", "Pillow Kick", "Rap Kick", "Real Kick",
        "Reverb Kick", "Room Kick 1", "Room Kick 2", "Solid Kick",
        "TR-808 Kick", "TR-909 Kick", "Reverb Solid Kick", "808 Electronic Kick",
        "909 Hard Kick",

        // "Snare Drum" (S):
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
        "Ambient Side Stick", "Hall Side Stick", "TR-808 Side Stick",

        // "Tom" (t):
        "Rock Tom 1", "Rock Tom 2", "TR-808 Tom", "Real Tom 1",
        "Real Tom 2", "Double Head Tom 1", "Double Head Tom 2", "Brush Slap Tom 1",
        "Brush Slap Tom 2", "Acoustic Tom 1", "Acoustic Tom 2",

        // "Hi-Hat Cymbal" (H):
        "Pop Closed Hi-Hat Inner", "Pop Closed Hi-Hat Outer", "Pop Open Hi-Hat Inner", "Pop Open Hi-Hat Outer",
        "Pop Pedal Hi-Hat", "Real Closed Hi-Hat Inner", "Real Closed Hi-Hat Outer", "Real Open Hi-Hat Inner",
        "Real Open Hi-Hat Outer", "Real Pedal Hi-Hat", "TR-808 Closed Hi-Hat Inner", "TR-808 Closed Hi-Hat Outer",
        "TR-808 Open Hi-Hat Inner", "TR-808 Open Hi-Hat Outer", "CR-78 Closed Hi-Hat", "CR-78 Open Hi-Hat",
        "Brush Closed Hi-Hat", "Brush Open Hi-Hat",

        // "Ride/Crash Cymbal" (C):
        "Crash Cymbal 1", "Crash Cymbal 2", "Chinese Cymbal 1", "Chinese Cymbal 2",
        "Brush Ride Cymbal", "Hand Cymbals", "Ride Cymbal", "Ride Bell Cymbal",
        "TR-808 Cymbal",

        // "Latin Percussion" (L):
        "Bongo High", "Bongo Low 1", "Bongo Low 2", "Conga High Mute",
        "Conga High Slap", "Conga High Open", "Conga Low Open 1", "Conga Low Open 2",
        "Cowbell 1", "Cowbell 2", "Claves 1", "Claves 2",
        "Guiro Short", "Guiro Long", "Maracas", "Shaker",
        "Tambourine", "Timbale High", "Timbale Low", "Timbale Paila",
        "Vibra-Slap", "Agogo", "Cabasa", "Cuica Mute 1",
        "Cuica Mute 2", "Cuica Open", "Pandiero Mute", "Pandiero Slap",
        "Pandiero Open 1", "Pandiero Open 2", "Surdo Rim", "Surdo Mute",
        "Surdo Open", "Tamborim 1", "Tamborim 2", "Whistle Short",
        "Whistle Long",

        // "Orchestral/Ethnic Percussion" (P):
        "Bell Tree", "Castanets", "Castanets with Hall Ambience", "Concert Bass Drum Mute",
        "Concert Bass Drum Open", "Gong large", "Gong Small", "Sleigh Bell",
        "Timpani Bend", "Triangle Mute", "Triangle Open", "Wood Block",
        "Chekere", "Djembe 1", "Djembe 2", "Djembe 3",
        "Talking Drum", "Tabla Na 1", "Tabla Na 2", "Tabla Tun",
        "Tabla Te", "Baya Ge", "Darbuk", "Monster Drum",
        "Taiko 1", "Taiko 2", "Taiko Rim", "Tsuzumi",
        "Can Drum", "Matsuri", "Rattle",

        // "Analog Percussion" (A):
        "DR-55 Claves", "CR-78 Cowbell", "CR-78 Metallic Beat", "CR-78 Guiro",
        "CR-78 Tambourine", "CR-78 Maracas", "TR-808 Conga", "TR-808-Claves",
        "TR-808 Maracas", "TR-808 Hand Clap", "TR-808 Cowbell",

        // "Melodic Percussion" (M):
        "Anvil", "Bamboo", "Drip", "Gamelan 1",
        "Gamelan 2", "Glass", "Glockenspiel", "Kalimba",
        "Log Drum", "Marimba 1", "Marimba 2", "Steel Drum 1",
        "Steel Drum 2", "Timpani", "Vibraphone", "Xylophone",
        "Brass Hit Short", "Brass Hit Long", "Orchestra Hit 1", "Orchestra Hit 2",

        // "Effect Sounds" (E):
        "Chink","Chop", "Crash 1", "Crash 2",
        "Drop", "Emergency", "Hand Clap 1", "Hand Clap 2",
        "High Q 1", "High Q 2", "Metal 1", "Metal 2",
        "Metal Side Stick", "Noise Accent 1", "Noise Accent 2", "Noise Accent 3",
        "Noise Accent 4", "Random Noise 1", "Random Noise 2", "Random Noise 3",
        "Random Noise 4", "Scratch Push", "Scratch Pull", "Scratch Stereo",
        "Shot 1", "Shot 2", "Shot 3", "Shot 4",
        "Snaps", "Stick Hit", "Uut?", "Woody 1",
        "Woody 2", "Kick Ambience", "Snare Ambience", "Tom Ambience",
        "Concert Ambience",

        // "Reversed Sounds" (r):
        "Reverse Ambience", "Reverse Beat", "Reverse Clap", "Reverse Cymbal",
        "Reverse High Q", "Reverse Kick", "Reverse Shot", "Reverse Snare",
        "Reverse Tom",

        // "Hi-Hat For Pedal Control" (PH),
        "Pop Hi-Hat Inner", "Pop Hi-Hat Outer", "Real Hi-Hat Inner", "Real Hi-Hat Outer",
        "Brush Hi-hat", "TR-808 Hi-Hat Inner", "TR-808 Hi-Hat Outer", "CR-78 Hi-Hat",

        "Off"
     };
     /** describes response, could be 16 images */
     protected static final String[] CURVES = {
         "Linear", "ExPonential1", "ExPonential2", "ExPonential3",
         "ExPonential4", "SPline1", "SPline2", "SoFt1",
         "SoFt2", "SoFt3", "SoFt4", "Hard1",
         "Hard2", "Hard3", "Hard4", "ConStant"
     };
     /** 0:C-1~127:G9 */
     protected static final String[] NOTENUMBERS = {
         "0:C-1","1:C#-1","2:D-1","3:D#-1","4:E-1","5:F-1","6:F#-1","7:G-1","8:G#-1",
         "9:A-1","10:A#-1","11:B-1",
         "12:C0","13:C#0","14:D0","15:D#0","16:E0","17:F0","18:F#0","19:G0","20:G#0",
         "21:A0","22:A#0","23:B0",
         "24:C1","25:C#1","26:D1","27:D#1","28:E1","29:F1","30:F#1","31:G1","32:G#1",
         "33:A1","34:A#1","35:B1",
         "36:C2","37:C#2","38:D2","39:D#2","40:E2","41:F2","42:F#2","43:G2","44:G#2",
         "45:A2","46:A#2","47:B2",
         "48:C3","49:C#3","50:D3","51:D#3","52:E3","53:F3","54:F#3","55:G3","56:G#3",
         "57:A3","58:A#3","59:B3",
         "60:C4","61:C#4","62:D4","63:D#4","64:E4","65:F4","66:F#4","67:G4","68:G#4",
         "69:A4","70:A#4","71:B4",
         "72:C5","73:C#5","74:D5","75:D#5","76:E5","77:F5","78:F#5","79:G5","80:G#5",
         "81:A5","82:A#5","83:B5",
         "84:C6","85:C#6","86:D6","87:D#6","88:E6","89:F6","90:F#6","91:G6","92:G#6",
         "93:A6","94:A#6","95:B6",
         "96:C7","97:C#7","98:D7","99:D#7","100:E7","101:F7","102:F#7","103:G7","104:G#7",
         "105:A7","106:A#7","107:B7",
         "108:C8","109:C#8","110:D8","111:D#8","112:E8","113:F8","114:F#8","115:G8","116:G#8",
         "117:A8","118:A#8","119:B8",
         "120:C9","121:C#9","122:D9","123:D#9","124:E9","125:F9","126:F#9","127:G9"
     };
     /** 1~16 + Off */
     protected static final String[] CHANNELNUMBERS = { // DriverUtil.generateNumbers(1, 16, "##") + "Off"; doesn't work
         "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","Off"
     };
     /** program change numbers 1~128 */
     protected static final String[] PROGNUMBERS = DriverUtil.generateNumbers(1, 128, "##");
     /** Pan positions or l7~central~r7~random */
     protected static final String[] PANPOSITIONS = {
         "l 7","l 6","l 5","l 4","l 3","l 2","l 1",
         "Central","r 1","r 2","r 3","r 4","r 5","r 6","r 7","Random"
     };
    /** all drumkits: 01~64 with their standard name */
     protected static final String[] SPD11_PATCHES = {
         "1=Standard","2=Room","3=Power 1","4=Jazz 1","5=Brush",
         "6=Electronic","7=Techno","8=TR-808","9=CR-78&TR-909",
         "10=Dynamic","11=Dance 1","12=Dance 2","13=Rock","14=Funk",
         "15=Rap","16=House","17=Ambient","18=Dry","19=Heavy","20=Light",
         "21=Real","22=Power 2","23=Dopey","24=Jazz 2",
         "25=Orchestra 1","26=Orchestra 2","27=Orchestra 3",
         "28=Cuban 1","29=Cuban 2","30=Cuban 3",
         "31=Brazilian 1","32=Brazilian 2","33=Brazilian 3",
         "34=African 1","35=African 2",
         "36=Asian 1","37=Asian 2","38=Asian 3",
         "39=Analog 1","40=Analog 2",
         "41=Effect 1","42=Effect 2","43=Effect 3",
         "44=Effect 4","45=Effect 5","46=Effect 6","47=Effect 7","48=Effect 8",
         "49=Marimba","50=Kalimba","51=Glockenspiel","52=Vibraphone",
         "53=Steel drum","54=Timpani","55=Xylophone","56=Glass",
         "57=Melodic 1","58=melodic 2","59=Melodic 3","60=melodic 4",
         "61=Patch Expand 1","62=Patch Expand 2","63=Patch Expand 3","64=Patch Expand 4"
     };
}
