/* ImageChunk.java created by a.m.ingraldi on Mon 18-Sep-2000 */

import com.webobjects.foundation.*;
import java.io.*;

public class ImageChunk {
    protected NSData image = null;
    protected int width = 0;
    protected int height = 0;
    protected String type = null;
    
    public ImageChunk() {
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public String type() {
        return type;
    }
    
    public void setType(String newValue) {
        type = newValue;
    }

    private boolean isSWF() {
        // SWF files always start with:  0x53 0x57 0x46  (FWS)
        byte[] swfSig = {0x53, 0x57, 0x46};
        if (image().length() > 10) { // do we have enough bytes to bother?
            byte[] firstBytes = image().bytes(0,3);
            short matchingBytes = 0;
            for (int i = 0; i < 3; ++i) {
                if (firstBytes[i] == swfSig[i])
                    ++matchingBytes;
            }
            if (matchingBytes == 3) // it's a SWF
                return true;
            else
                return false;
        } else {
            return false;
        }
    }


    private boolean isGIF() {
        // GIF files always start with:  0x47 0x49 0x46 0x38  (GIF8)
        byte[] gifSig = {0x47, 0x49, 0x46, 0x38};
        if (image().length() > 10) { // do we have enough bytes to bother?
            byte[] firstBytes = image().bytes(0,4);
            short matchingBytes = 0;
            for (int i = 0; i < 4; ++i) {
                if (firstBytes[i] == gifSig[i])
                    ++matchingBytes;
            }
            if (matchingBytes == 4) // it's a GIF
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

    private boolean isJPEG() {
        // JPEG files always start with: 0xFF 0xd8
        byte[] jpegSig = {(byte)0xff, (byte)0xd8};
        if (image().length() > 10) { // do we have enough bytes to bother
            byte[] firstBytes = image().bytes(0,2);
            short matchingBytes = 0;
            for (int i = 0; i < 2; ++i) {
                if (firstBytes[i] == jpegSig[i])
                    ++matchingBytes;
            }
            if (matchingBytes == 2) // it's a JPEG
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

    private boolean isPNG() {
        // PNG files always start with:  0x89 0x50 0x4e 0x47 0x0d 0x0a 0x1a 0x0a
        byte[] pngSig = {(byte)0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a};
        if (image().length() > 10) { // do we have enough bytes to bother
            byte[] firstBytes = image().bytes(0,8);
            short matchingBytes = 0;
            for (int i = 0; i < 8; ++i) {
                if (firstBytes[i] == pngSig[i])
                    ++matchingBytes;
            }
            if (matchingBytes == 8) // it's a PNG
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

    public NSData image() {
        return image;
    }   
	 
    public void setImage(NSData newValue) {
        image = newValue;
        if (image != null && isGIF()) {
            setType("GIF");
            try {
                GIFInfo imageInfo = new GIFInfo(image);
                width = imageInfo.width();
                height = imageInfo.height();
            } catch (Exception e) {
                // bad GIF?
            }
        } else if (image != null && isSWF()) {
            setType("SWF");
            try {
                SWFInfo imageInfo = new SWFInfo(image);
                width = imageInfo.width();
                height = imageInfo.height();
            } catch (Exception e) {
                // bad GIF?
            }
        } else if (image != null && isJPEG()) {
            setType("JPEG");
            try {
                JPEGInfo imageInfo = new JPEGInfo(image);
                width = imageInfo.width();
                height = imageInfo.height();
            } catch (Exception e) {
                // bad JPEG?
            }
        } else if (image != null && isPNG()) {
            setType("PNG");
            try {
                PNGInfo imageInfo = new PNGInfo(image);
                width = imageInfo.width();
                height = imageInfo.height();
            } catch (Exception e) {
                // bad PNG?
            }
        } else { 
            // an unsupported or bogus image
            image = null;
            width = 0;
            height = 0;
            type = null;
        }
    }

    public String mimeType() {
        if (type() != null)
            return "image/" + type().toLowerCase();
        else
            return null;
    }
}
