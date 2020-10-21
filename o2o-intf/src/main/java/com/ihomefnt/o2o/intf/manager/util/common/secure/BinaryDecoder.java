package com.ihomefnt.o2o.intf.manager.util.common.secure;

public interface BinaryDecoder extends Decoder {
    byte[] decode(byte[] var1) throws DecoderException;
}