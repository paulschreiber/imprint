/* PNGInfo.java created by a.m.ingraldi on Fri 20-Apr-2001 */

/*
 * PNGInfo.java
 *
 * This class can be used to determine the height and width of a PNG image
 *
 */
import com.webobjects.foundation.*;

class PNGInfo {
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

    // assumes bytes are arranged with the MSB in position 0
    private int fourBytesToInt(byte[] bytes) {
        int theInteger = 0;
        theInteger += unsignedByteToInt(bytes[0]) << 24;
        theInteger += unsignedByteToInt(bytes[1]) << 16;
        theInteger += unsignedByteToInt(bytes[2]) << 8;
        theInteger += unsignedByteToInt(bytes[3]) << 0;
        return theInteger;
    }
        
    PNGInfo(NSData imageData) {
        /* PNG images are structured as follows
         * xxxxxxxxNNNNIHDRWWWWHHHH
         * where:
         *    xxxxxxxx is the PNG signature
         *    NNNN is the size of the IHDR chunk
         *    IHDR is the literal 'IHDR'
         *    WWWW are four bytes for image width
         *    HHHH are four bytes for image height
         */
        byte[] widthBytes = imageData.bytes(16, 4);
        byte[] heightBytes = imageData.bytes(20, 4);

        // PNG image size info is stored most-significant byte first
        width = fourBytesToInt(widthBytes);
        height = fourBytesToInt(heightBytes);
    }

}
