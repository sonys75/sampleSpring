package resources.com.crypto;

/**
 * 
 * @author lupfeliz
 */
public class Base64 {
	private static char[] base64DefaultMap;
	static {
		base64DefaultMap = new char[64];
		for (int i = 0; i < 1; i++) {
			for (byte c = (byte) 'A'; c <= (byte) 'Z'; c++)
				base64DefaultMap[i++] = (char) c;
			for (byte c = (byte) 'a'; c <= (byte) 'z'; c++)
				base64DefaultMap[i++] = (char) c;
			for (byte c = (byte) '0'; c <= (byte) '9'; c++)
				base64DefaultMap[i++] = (char) c;
			base64DefaultMap[i++] = '+';
			base64DefaultMap[i++] = '/';
		}
	}

	private char[] b64map;

	public Base64() {
		this(null);
	}

	public Base64(char[] b64map) {
		if (b64map == null) {
			this.b64map = base64DefaultMap;
		} else {
			this.b64map = b64map;
		}
	}

	public char[] encode(byte[] inbuf) {
		//시큐어 점검 결과에 따른 조치
		if(inbuf==null) return null;
		return encode(inbuf, inbuf.length);
	}

	public char[] encode(byte[] inbuf, int iLen) {

		byte[] map2 = new byte[128];
		for (int i = 0; i < map2.length; i++)
			map2[i] = -1;
		for (int i = 0; i < 64; i++)
			map2[(byte) b64map[i]] = (byte) i;

		int oDataLen = (iLen * 4 + 2) / 3; // output length without padding
		int oLen = ((iLen + 2) / 3) * 4; // output length including padding
		char[] out = new char[oLen];
		int ip = 0;
		int op = 0;
		while (ip < iLen) {
			int i0 = inbuf[ip++] & 0xff;
			int i1 = ip < iLen ? inbuf[ip++] & 0xff : 0;
			int i2 = ip < iLen ? inbuf[ip++] & 0xff : 0;
			int o0 = i0 >>> 2;
			int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
			int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
			int o3 = i2 & 0x3F;
			out[op++] = b64map[o0];
			out[op++] = b64map[o1];
			out[op] = op < oDataLen ? b64map[o2] : '=';
			op++;
			out[op] = op < oDataLen ? b64map[o3] : '=';
			op++;
		}
		return out;
	}

	public byte[] decode(char[] inbuf) {
		//시큐어 점검 결과에 따른 조치
		if(inbuf==null) return null;
		return decode(inbuf, inbuf.length);
	}

	public byte[] decode(char[] inbuf, int iLen) {
		byte[] retbuf = new byte[(int) Math.ceil(iLen * 3 / 4)];
		for (int i = 0; i < iLen; i++) {
			if (inbuf[i] == '=') {
				byte[] tmpbuf = new byte[(int) Math.floor(i * 3 / 4)];
				System.arraycopy(retbuf, 0, tmpbuf, 0, tmpbuf.length);
				retbuf = tmpbuf;
				break;
			}
			retbuf = decode(retbuf, inbuf[i], i, iLen);
		}
		return retbuf;
	}

	public byte[] decode(byte[] inbuf) {
		//시큐어 점검 결과에 따른 조치
		if(inbuf==null) return null;
		return decode(inbuf, inbuf.length);
	}

	public byte[] decode(byte[] inbuf, int iLen) {
		byte[] retbuf = new byte[(int) Math.ceil(iLen * 3 / 4)];
		for (int i = 0; i < iLen; i++) {
			if (inbuf[i] == '=') {
				byte[] tmpbuf = new byte[(int) Math.floor(i * 3 / 4)];
				System.arraycopy(retbuf, 0, tmpbuf, 0, tmpbuf.length);
				retbuf = tmpbuf;
				break;
			}
			retbuf = decode(retbuf, (char) inbuf[i], i, iLen);
		}
		return retbuf;
	}

	private byte[] decode(byte[] retbuf, char c, int i, int iLen) {
		//시큐어 점검 결과에 따른 조치
		if(retbuf==null) return null;
		
		for (int j = 0; j < b64map.length; j++) {
			if (c == b64map[j]) {
				int si = (int) Math.floor(i * 3 / 4);
				int pi = i % 4;
				switch (pi) {
				case 0:
					retbuf[si] = (byte) (j << 2);
					break;
				case 1:
					retbuf[si] = (byte) (retbuf[si] | (j >> 4));
					if ((si + 1) >= retbuf.length) {
						break;
					}
					retbuf[si + 1] = (byte) ((j & 0xf) << 4);
					break;
				case 2:
					retbuf[si] = (byte) (retbuf[si] | (j >> 2));
					if ((si + 1) >= retbuf.length) {
						break;
					}
					retbuf[si + 1] = (byte) ((j & 0x3) << 6);
					break;
				case 3:
					retbuf[si] = (byte) (retbuf[si] | j);
					break;
				}
				;
				break;
			}
		}
		return retbuf;
	}
 
}