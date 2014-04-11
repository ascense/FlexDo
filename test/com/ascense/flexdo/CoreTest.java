package com.ascense.flexdo;

import com.ascense.flexdo.core.PasswordHandler;
import org.junit.Test;
import static org.junit.Assert.*;

public class CoreTest {
    public CoreTest() {}

    @Test
    public void testToHexString() {
        assertEquals(
            "c4d4e4f4",
            PasswordHandler.toHexString(
                new byte[]{(byte) 0xc4, (byte) 0xd4, (byte) 0xe4, (byte) 0xf4}
            )
        );
        assertEquals(
            "00000000",
            PasswordHandler.toHexString(
                new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}
            )
        );
    }

    @Test
    public void testFromHexString() {
        assertArrayEquals(
            new byte[]{(byte) 0xc4, (byte) 0xd4, (byte) 0xe4, (byte) 0xf4},
            PasswordHandler.fromHexString("c4d4e4f4")
        );
        assertArrayEquals(
            new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00},
            PasswordHandler.fromHexString("00000000")
        );
    }
}
