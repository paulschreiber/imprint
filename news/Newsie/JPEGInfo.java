/*
 * JPEGInfo.java 
 *
 * This class can be used to determine the height and width of a JPEG image
 * that is written in JPEG File Interchange Format (JFIF)
 *
 * This class is based on the file rdjpgcom.c contained in the Independent
 * JPEG Group's JPEG library distribution.  Most of the comments are taken from
 * rdjpgcom.c as well.
 *
 * For conditions of distribution and use of the JPEG library code,
 * see the accompanying README_JPEG.txt file.
 *
 */
import java.io.*;
import com.webobjects.foundation.*;

class JPEGInfo {
    private InputStream inFile = null;
    private int height;
    private int width;

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    /* Read one byte, testing for EOF */
    private int readOneByte() throws IOException {
        int c;

        // the read() method always returns a value between 0 and 255 so we don't
        // have to deal with the unsigned nature of Java bytes
        c = inFile.read();
        // read() returns -1 when EOF is reached
        if (c == -1)
            throw new IOException("Premature EOF in JPEG file");
        return c;
    }

    /* Read 2 bytes */
    /* All 2-byte quantities in JPEG markers are MSB first */
    private int readTwoBytes() throws IOException {
        int c1, c2;

        c1 = readOneByte();
        c2 = readOneByte();
        
        return (c1 << 8) + c2;
    }


/*
 * JPEG markers consist of one or more 0xFF bytes, followed by a marker
 * code byte (which is not an FF).  Here are the marker codes of interest
 * in this program.  (See jdmarker.c in the IJG JPEG distribution
 * for a more complete list.)
 */

    static final int M_SOF0 = 0xC0;  /* Start Of Frame N */
    static final int M_SOF1 = 0xC1;  /* N indicates which compression process */
    static final int M_SOF2 = 0xC2;  /* Only SOF0-SOF2 are now in common use */
    static final int M_SOF3 = 0xC3;
    static final int M_SOF5 = 0xC5;  /* NB: codes C4 and CC are NOT SOF markers */
    static final int M_SOF6 = 0xC6;
    static final int M_SOF7 = 0xC7;
    static final int M_SOF9  = 0xC9;
    static final int M_SOF10 = 0xCA;
    static final int M_SOF11 = 0xCB;
    static final int M_SOF13 = 0xCD;
    static final int M_SOF14 = 0xCE;
    static final int M_SOF15 = 0xCF;
    static final int M_SOI = 0xD8;   /* Start Of Image (beginning of datastream) */
    static final int M_EOI = 0xD9;   /* End Of Image (end of datastream) */
    static final int M_SOS = 0xDA;   /* Start Of Scan (begins compressed data) */

/*
 * Find the next JPEG marker and return its marker code.
 * We expect at least one FF byte, possibly more if the compressor used FFs
 * to pad the file.
 * There could also be non-FF garbage between markers.  The treatment of such
 * garbage is unspecified; we choose to skip over.
 * NB: this routine must not be used after seeing SOS marker, since it will
 * not deal correctly with FF/00 sequences in the compressed image data...
 */
    private int nextMarker() throws IOException {
        int c;

        /* Find 0xFF byte; count and skip any non-FFs. */
        c = readOneByte();
        while (c != 0xFF) {
            c = readOneByte();
        }
  /* Get marker code byte, swallowing any duplicate FF bytes.  Extra FFs
   * are legal as pad bytes.
   */
        do {
            c = readOneByte();
        } while (c == 0xFF);

        return c;
    }

/*
 * Read the initial marker, which should be SOI.
 * For a JFIF file, the first two bytes of the file should be literally
 * 0xFF M_SOI.  To be more general, we could use nextMarker, but if the
 * input file weren't actually JPEG at all, nextMarker might read the whole
 * file and then return a misleading error message...
 */
    private int firstMarker() throws IOException {
        int c1, c2;

        c1 = readOneByte();
        c2 = readOneByte();
        if (c1 != 0xFF || c2 != M_SOI) {
            throw new IOException("Not a JFIF file.");
        }

        return c2; // the M_SOI marker
    }

/*
 * Most types of marker are followed by a variable-length parameter segment.
 * This routine skips over the parameters for any marker we don't otherwise
 * want to process.
 * Note that we MUST skip the parameter segment explicitly in order not to
 * be fooled by 0xFF bytes that might appear within the parameter segment;
 * such bytes do NOT introduce new markers.
 */

/* Skip over an unknown or uninteresting variable-length marker */
    private void skipVariable() throws IOException {
        int length;

  /* Get the marker parameter length count */
        length = readTwoBytes();
  /* Length includes itself, so must be at least 2 */
        if (length < 2)
            throw new IOException("Erroneous JPEG marker length");
        length -= 2;
  /* Skip over the remaining bytes */
        while (length > 0) {
            readOneByte();
            length--;
        }
    }

/*
 * get image dimensions
 */
    void getHeightAndWidth() throws IOException {
        readTwoBytes();	 // length of marker, ignore

        readOneByte(); // the data precision byte, ignore
        height = readTwoBytes();
        width = readTwoBytes();
    }

/*
 * Parse the marker stream until SOFn is seen since we're only interested in the
 * image dimensions.
 */
    JPEGInfo(NSData imageData) throws IOException {
        boolean done = false;
        int marker;
        inFile = new ByteArrayInputStream(imageData.bytes(0, imageData.length()));;
        
        /* Expect SOI at start of file */
        if (firstMarker() != M_SOI)
            throw new IOException("Expected SOI marker first");

        /* Scan miscellaneous markers until we reach SOFn, SOS, or EOI. */
        while (!done) {
            marker = nextMarker();
            switch (marker) {
                /* Note that marker codes 0xC4, 0xC8, 0xCC are not, and must not be,
                * treated as SOFn.  C4 in particular is actually DHT.
                */
                case M_SOF0:		/* Baseline */
                case M_SOF1:		/* Extended sequential, Huffman */
                case M_SOF2:		/* Progressive, Huffman */
                case M_SOF3:		/* Lossless, Huffman */
                case M_SOF5:		/* Differential sequential, Huffman */
                case M_SOF6:		/* Differential progressive, Huffman */
                case M_SOF7:		/* Differential lossless, Huffman */
                case M_SOF9:		/* Extended sequential, arithmetic */
                case M_SOF10:		/* Progressive, arithmetic */
                case M_SOF11:		/* Lossless, arithmetic */
                case M_SOF13:		/* Differential sequential, arithmetic */
                case M_SOF14:		/* Differential progressive, arithmetic */
                case M_SOF15:		/* Differential lossless, arithmetic */
                    getHeightAndWidth();
                    done = true;
                    break;

                case M_SOS:             /* stop before hitting compressed data */
                    done = true;
                    break;

                case M_EOI:             /* in case it's a tables-only JPEG stream */
                    done = true;
                    break;

                default:		 /* Anything else just gets skipped */
                    skipVariable();	 /* we assume it has a parameter count... */
                    break;
            }
        } /* end loop */

        inFile.close();
        inFile = null;
    }

}
