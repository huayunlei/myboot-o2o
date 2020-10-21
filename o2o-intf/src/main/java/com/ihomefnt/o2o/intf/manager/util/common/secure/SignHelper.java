package com.ihomefnt.o2o.intf.manager.util.common.secure;

import com.ihomefnt.o2o.intf.domain.lechange.dto.LechangeResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SignHelper {
	
	private static final String APP_ID = "lc717789b7335b4f53";
	
	private static final String APP_SECRET = "378d46a0b5ae40289027f43aac8861";
	
	private static final String SYS_VERSION = "1.1";
	
	public static String combineParam(String data) {
		
		org.json.JSONObject jsonObject = new org.json.JSONObject();
		try {
			String system = SignHelper.getSystem(data);
			jsonObject.put("params", new org.json.JSONObject(data));
			jsonObject.put("system", new org.json.JSONObject(system));
			jsonObject.put("id", randomInt());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
	
	/**
	 * 签名参数生成sign，并生成system参数
	* @return String 
	* @author Charl
	 */
	public static String getSystem(String data) {
		String appId = APP_ID;
		String appSecret = APP_SECRET;
		String sysVersion = SYS_VERSION;
		StringBuffer sign = new StringBuffer();
		try {
			JSONObject params = new JSONObject(data);
			Iterator<?> it = params.keys();
			List<String> keyList = new ArrayList<String>();
			while (it.hasNext()) {
				keyList.add(it.next().toString());
			}
			Collections.sort(keyList);
			for (String key : keyList) {
				sign.append("").append(key).append(":")
						.append(params.get(key).toString()).append(",");
			}
			// System.out.println(sign);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String time = Long.toString(System.currentTimeMillis() / 1000);
		String nonce = randomString(32);
		sign.append("time").append(":").append(time).append(",");
		sign.append("nonce").append(":").append(nonce).append(",");
		sign.append("appSecret").append(":").append(appSecret);
		// System.out.println(sign);
		JSONObject system = new JSONObject();
		try {
			system.put("ver", sysVersion);
			system.put("sign", md5Hex(sign.toString()));
			system.put("appId", appId);
			system.put("time", time);
			system.put("nonce", nonce);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return system.toString();
	}
	 
	 public static String md5Hex(String str) {
			try {
				byte hash[] = MessageDigest.getInstance(ProtocolAlgConstants.MD5_ALG).digest(str.getBytes());
				StringBuilder hex = new StringBuilder(hash.length * 2);
				for (byte b : hash) {
					if ((b & 0xFF) < 0x10) {
						hex.append("0");
					}
					hex.append(Integer.toHexString(b & 0xFF));
				}
				return hex.toString();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	 
	 final static String VEC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	 private static Random rand;
	 public static String randomString(int length) {
			if (rand == null) {
				rand = new Random(System.currentTimeMillis());
			}
			String ret = "";
			for (int i = 0; i < length; i++) {
				ret = ret + VEC.charAt(rand.nextInt(VEC.length()));
			}
			return ret;
		}

		static Random random = new Random();

	 /**
	  * 随机生成id
	 * @param @return
	 * @return int 
	 * @author Charl
	  */
	 public static int randomInt() {
		long currentTimeMillis = System.currentTimeMillis();
		String str = new String(currentTimeMillis + "");
		String subStr = str.substring(str.length() - 3);
		int id = random.nextInt(1000) + 1 + Integer.parseInt(subStr);
		return id;
	 }
	 
	 public static LechangeResponse resultToJson(String result) {
		 LechangeResponse response = new LechangeResponse();
		 try {
			JSONObject resultJson = new JSONObject(result);
			response.setId(resultJson.getInt("id"));
			response.setResult(resultJson.get("result"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		 return null;
	 }
}
