/* GIFInfo.java created by a.m.ingraldi on Fri 20-Apr-2001 */

/*
 * GIFInfo.java
 *
 * This class can be used to determine the height and width of a GIF image.
 *
 */
import com.webobjects.foundation.*;

class GIFInfo {
    private int height;
    private int width;

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }
    
    private int unsignedByteToInt(byte b) {
        return (int)b & 0xFF;
    }

    // assumes bytes are arranged with the LSB in position 0
    private int twoBytesToInt(byte[] bytes) {
        // left-shift each byte appropriately and add to get the result
        int theInteger = 0;
        theInteger += unsignedByteToInt(bytes[1]) << 8;
        theInteger += unsignedByteToInt(bytes[0]) << 0;
        return theInteger;
    }

    GIFInfo(NSData imageData) {
        /* GIF images are structured as follows
         * GIF8xaWWHH ...
         * where:
         *    x is either 7 or 9 (for GIF87a or GIF89a format)
         *    WW are two bytes for image width
         *    HH are two bytes for image height
         */
        byte[] widthBytes = imageData.bytes(6, 2);
        byte[] heightBytes = imageData.bytes(8, 2);
       
        // GIF image size info is stored least-significant byte first
        width = twoBytesToInt(widthBytes);
        height = twoBytesToInt(heightBytes);
    }

}
