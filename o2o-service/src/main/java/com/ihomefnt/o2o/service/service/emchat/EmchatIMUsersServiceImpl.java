package com.ihomefnt.o2o.service.service.emchat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ihomefnt.o2o.intf.dao.emchat.EmchatIMUsersDao;
import com.ihomefnt.o2o.intf.domain.emchat.doo.EmchatIMUserInfoDo;
import com.ihomefnt.o2o.intf.domain.emchat.dto.Constants;
import com.ihomefnt.o2o.intf.domain.emchat.vo.response.EmchatIMUserResponseVo;
import com.ihomefnt.o2o.intf.domain.user.doo.UserDo;
import com.ihomefnt.o2o.intf.manager.constant.emchat.*;
import com.ihomefnt.o2o.intf.manager.util.common.http.HTTPClientUtils;
import com.ihomefnt.o2o.intf.manager.util.common.secure.Base64Util;
import com.ihomefnt.o2o.intf.service.emchat.EmchatIMUsersService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Random;

@Service
public class EmchatIMUsersServiceImpl implements EmchatIMUsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmchatIMUsersServiceImpl.class);
    private static final JsonNodeFactory factory = new JsonNodeFactory(false);
    
    @Autowired
    EmchatIMUsersDao emchatIMUsersDao;
    
 // 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
            Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);
    
    @Override
    public int registerEmUser(String userName, String nickName) {
    	try{
            ObjectNode datanode = JsonNodeFactory.instance.objectNode();
            String password = initPassword();
            datanode.put("username", userName);
            datanode.put("password", password); //Constants.DEFAULT_PSD
            datanode.put("nickname", nickName);
            ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode);
            
            int result = saveEmchatIMUserInfo(createNewIMUserSingleNode, userName, password, nickName);
            return result;
    	}catch(Exception e){
    		LOGGER.error("EmchatIMUsersServiceImpl error:{}",e.getMessage());
    		 return -1;
    	}

    }
    
    @Override
    public EmchatIMUserResponseVo getEmchatIMUser(String userName) {
        String password = "";
        String encryptedPassword = emchatIMUsersDao.getEmchatIMUserPassword(userName);
        if(StringUtils.isBlank(encryptedPassword)){
        	return null;
        }
        try {
            byte[] byteArray = Base64Util.decode(encryptedPassword);
			if (byteArray == null) {
				return null;
			}
            password = new String(byteArray);
        } catch (Exception e) {
            LOGGER.error("解密环信IM用户密码失败！,ERROR:{}", e.getMessage());
            return null;
        }
        
        EmchatIMUserResponseVo emchatIMUser = new EmchatIMUserResponseVo();
        emchatIMUser.setUserName(userName);
        emchatIMUser.setPassword(password);
        
        return emchatIMUser;
    }
    
    @Override
    public int initEmchatIMUser() {
        for (int i=0; i<500; i++) {
            List<UserDo> userList = emchatIMUsersDao.getUserInfo();
            if (null != userList) {
                Object[] objArr = createNewIMUserBatchGen(userList, 20);
                ObjectNode objectNode = (ObjectNode) objArr[0];
                ArrayNode genericArrayNode = (ArrayNode) objArr[1];
                saveBatchEmchatIMUserInfo(objectNode, genericArrayNode);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            } else {
                return 1;
            }
        }
        
        return 1;
    }
    
    @Override
    public int deleteEmchatIMUser() {
        for (int i=0; i<2900; i++) {
            String userName = String.valueOf(i);
            deleteIMUserByuserName(userName);
        }
        
        return 1;
    }
    
    
    @Override
    public int modifyEmchatIMUserNickname(String userName, String nickName) {
        ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("nickname", nickName);
        ObjectNode objectNode = modifyIMUserNickname(userName, datanode);
        if (null != objectNode) {
            JsonNode jsonNode = objectNode.get("statusCode");
            int statusCode = 200;
            if (jsonNode.isInt()) {
                statusCode = jsonNode.intValue();
            }
            
            if (statusCode == 200) {
                int result = emchatIMUsersDao.modifyEmchatIMUserNickname(userName, nickName);
                if (result == 1) {
                    return 1;
                }
            }
        }
        
        return 0;
    }
    
    
    private Random random = new Random();
    private String initPassword() {
        int max = 99999999;
        int min = 10000000;
        int divider = max - min + 1;

        int password = random.nextInt(max) % (divider) + min;
        return String.valueOf(password);
    }
    
    
    
    
    /**
     * 注册IM用户[单个]
     * 
     * 给指定Constants.APPKEY创建一个新的用户
     * 
     * @param dataNode
     * @return
     */
    public static ObjectNode createNewIMUserSingle(ObjectNode dataNode) {

        ObjectNode objectNode = factory.objectNode();

        // check Constants.APPKEY format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
            LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

            objectNode.put("message", "Bad format of Constants.APPKEY");

            return objectNode;
        }

        objectNode.removeAll();

        // check properties that must be provided
        if (null != dataNode && !dataNode.has("username")) {
            LOGGER.error("Property that named username must be provided .");

            objectNode.put("message", "Property that named username must be provided .");

            return objectNode;
        }
        if (null != dataNode && !dataNode.has("password")) {
            LOGGER.error("Property that named password must be provided .");

            objectNode.put("message", "Property that named password must be provided .");

            return objectNode;
        }

        try {

            objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataNode,
                    HTTPMethod.METHOD_POST);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }
    
    /**
     * 保存单个IM用户信息到数据库
     * @param createNewIMUserSingleNode
     * @param userName
     * @param password
     * @param nickName
     * @return
     */
    private int saveEmchatIMUserInfo(ObjectNode createNewIMUserSingleNode, String userName, String password, String nickName) {
        if (null != createNewIMUserSingleNode) {
            JsonNode jsonNode = createNewIMUserSingleNode.get("statusCode");
            int statusCode = 200;
            if (jsonNode.isInt()) {
                statusCode = jsonNode.intValue();
            }
            
            if (statusCode == 200) {
                LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
                
                JsonNode entitiesJsonNode = createNewIMUserSingleNode.get("entities");
                JSONArray jsonArray = JSONArray.fromObject(entitiesJsonNode.toString());
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String uuid = jsonObject.getString("uuid");
                boolean actBool = jsonObject.getBoolean("activated");
                int activated = actBool == true ? 1 : 0;

                String encryptedPassword =  password;
                try {
                    encryptedPassword = Base64Util.encode(password.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("加密环信IM用户密码失败！", e.getMessage());
                }

                EmchatIMUserInfoDo emchatIMUserInfo = new EmchatIMUserInfoDo(userName, statusCode, nickName, encryptedPassword, activated, uuid);
                return emchatIMUsersDao.addEmchatIMUser(emchatIMUserInfo);
            } else {
            	LOGGER.info("userName: {},statusCode:{}" ,userName,statusCode);
				
/*                EmchatIMUserInfo emchatIMUserInfo = new EmchatIMUserInfo(userName, statusCode);
                emchatIMUsersDao.addEmchatIMUser(emchatIMUserInfo);*/
                return -1;
            }
            
        } else {
            EmchatIMUserInfoDo emchatIMUserInfo = new EmchatIMUserInfoDo(userName, -1);
            emchatIMUsersDao.addEmchatIMUser(emchatIMUserInfo);
            return -1;
        }
    }
    

    /**
     * 批量保存环信IM用户信息到数据库.
     * @param createNewIMUserSingleNode
     * @param genericArrayNode
     */
    private void saveBatchEmchatIMUserInfo(ObjectNode createNewIMUserSingleNode, ArrayNode genericArrayNode) {
        if (null != createNewIMUserSingleNode) {
            JsonNode jsonNode = createNewIMUserSingleNode.get("statusCode");
            int statusCode = 200;
            if (jsonNode.isInt()) {
                statusCode = jsonNode.intValue();
            }
            
            if (statusCode == 200) {
                JsonNode entitiesJsonNode = createNewIMUserSingleNode.get("entities");
                JSONArray jsonArray = JSONArray.fromObject(entitiesJsonNode.toString());
                int size = jsonArray.size();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String uuid = jsonObject.getString("uuid");
                    boolean actBool = jsonObject.getBoolean("activated");
                    int activated = actBool == true ? 1 : 0;
                    
                    Long username = jsonObject.getLong("username");
                    
                    String password = "";
                    String nickName = "";
                    int genericSize = genericArrayNode.size();
                    for (int j=0; j<genericSize; j++) {
                        JsonNode genericJsonNode = genericArrayNode.get(i);
                        JsonNode genericUsernameNode = genericJsonNode.get("username");
                        Long genericUsername = genericUsernameNode.longValue();
                        
                        if (Long.compare(username, genericUsername) == 0) {
                            JsonNode genericPasswordNode = genericJsonNode.get("password");
                            password = genericPasswordNode.textValue();
                            JsonNode genericNicknameNode = genericJsonNode.get("nickname");
                            nickName = genericNicknameNode.textValue();
                        }
                    }
                    
                    String encryptedPassword =  password;
                    try {
                        encryptedPassword = Base64Util.encode(password.getBytes());   
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("加密环信IM用户密码失败！", e.getMessage());
                    }
                    
                    String userName = String.valueOf(username);
                    
                    EmchatIMUserInfoDo emchatIMUserInfo = new EmchatIMUserInfoDo(userName, statusCode, nickName, encryptedPassword, activated, uuid);
                    emchatIMUsersDao.addEmchatIMUser(emchatIMUserInfo);
                }
            } else {
                EmchatIMUserInfoDo emchatIMUserInfo = new EmchatIMUserInfoDo("0", statusCode);
                emchatIMUsersDao.addEmchatIMUser(emchatIMUserInfo);
            }
            
        } else {
            EmchatIMUserInfoDo emchatIMUserInfo = new EmchatIMUserInfoDo("0", -1);
            emchatIMUsersDao.addEmchatIMUser(emchatIMUserInfo);
        }
    }


    /**
     * 注册IM用户[批量生成用户然后注册]
     * 
     * 给指定Constants.APPKEY创建一批用户
     * 
     * @param userList
     *            需要批量注册的用户数据
     * @param perNumber
     *            批量注册时一次注册的数量
     * @return
     */
    public Object[] createNewIMUserBatchGen(List<UserDo> userList, int perNumber) {
        ObjectNode objectNode = factory.objectNode();
        Object[] objArr = new Object[2];
        int totalNumber = userList.size();
        if (totalNumber == 0 || perNumber == 0) {
            return objArr;
        }
        LOGGER.info("你即将注册" + totalNumber + "个用户，如果大于" + perNumber + "了,会分批注册,每次注册" + perNumber + "个");
        ArrayNode genericArrayNode = genericArrayNode(userList);
        if (totalNumber <= perNumber) {
            objectNode = createNewIMUserBatch(genericArrayNode);
        } else {

            ArrayNode tmpArrayNode = factory.arrayNode();
            
            for (int i = 0; i < genericArrayNode.size(); i++) {
                tmpArrayNode.add(genericArrayNode.get(i));
                if ((i + 1) % perNumber == 0) {
                    objectNode = createNewIMUserBatch(tmpArrayNode);
                    if(objectNode!=null){
                        LOGGER.info("注册IM用户[批量]: " + objectNode.toString());
                    }
                    tmpArrayNode.removeAll();
                    continue;
                }

                if (i > (genericArrayNode.size() / perNumber * perNumber - 1)) {
                    objectNode = createNewIMUserBatch(tmpArrayNode);
                    if(objectNode!=null){
                        LOGGER.info("注册IM用户[批量]: " + objectNode.toString());
                    }
                    tmpArrayNode.removeAll();
                }
            }
        }

        objArr[0] = objectNode;
        objArr[1] = genericArrayNode;
        return objArr;
    }





    /**
     * 生成用户基本数据
     * 
     * @param userList
     * @return
     */
    public ArrayNode genericArrayNode(List<UserDo> userList) {
        ArrayNode arrayNode = factory.arrayNode();
        int number = userList.size();
        for (int i = 0; i < number; i++) {
            UserDo user = userList.get(i);
            ObjectNode userNode = factory.objectNode();
            userNode.put("username", user.getuId());
            userNode.put("password", initPassword());
            userNode.put("nickname", user.getMobile());

            arrayNode.add(userNode);
        }

        return arrayNode;
    }




    /**
     * 注册IM用户[批量]
     * 
     * 给指定Constants.APPKEY创建一批用户
     * 
     * @param dataArrayNode
     * @return
     */
    public static ObjectNode createNewIMUserBatch(ArrayNode dataArrayNode) {

        ObjectNode objectNode = factory.objectNode();

        // check Constants.APPKEY format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
            LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

            objectNode.put("message", "Bad format of Constants.APPKEY");

            return objectNode;
        }

        // check properties that must be provided
        if (dataArrayNode.isArray()) {
            for (JsonNode jsonNode : dataArrayNode) {
                if (null != jsonNode && !jsonNode.has("username")) {
                    LOGGER.error("Property that named username must be provided .");

                    objectNode.put("message", "Property that named username must be provided .");

                    return objectNode;
                }
                if (null != jsonNode && !jsonNode.has("password")) {
                    LOGGER.error("Property that named password must be provided .");

                    objectNode.put("message", "Property that named password must be provided .");

                    return objectNode;
                }
            }
        }

        try {

            objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataArrayNode,
                    HTTPMethod.METHOD_POST);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }






    /**
     * 删除IM用户[单个]
     * 
     * 删除指定Constants.APPKEY下IM单个用户
     *
     * 
     * @param userName
     * @return
     */
    public ObjectNode deleteIMUserByuserName(String userName) {
        ObjectNode objectNode = factory.objectNode();

        // check Constants.APPKEY format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
            LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

            objectNode.put("message", "Bad format of Constants.APPKEY");

            return objectNode;
        }

        try {

            URL deleteUserPrimaryUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"
                    + userName);
            objectNode = HTTPClientUtils.sendHTTPRequest(deleteUserPrimaryUrl, credential, null,
                    HTTPMethod.METHOD_DELETE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }


   
    /**
     * 修改IM用户昵称
     * 
     * 修改指定Constants.APPKEY下IM的用户昵称
     * 
     * @param dataNode
     * @return
     */
    public static ObjectNode modifyIMUserNickname(String userName, ObjectNode dataNode) {

        ObjectNode objectNode = factory.objectNode();

        // check Constants.APPKEY format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
            LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

            objectNode.put("message", "Bad format of Constants.APPKEY");

            return objectNode;
        }

        objectNode.removeAll();

        // check properties that must be provided
        if (null != dataNode && !dataNode.has("nickname")) {
            LOGGER.error("Property that named nickname must be provided .");

            objectNode.put("message", "Property that named nickname must be provided .");

            return objectNode;
        }

        try {
            URL deleteUserPrimaryUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"
                    + userName);
            objectNode = HTTPClientUtils.sendHTTPRequest(deleteUserPrimaryUrl, credential, dataNode,
                    HTTPMethod.METHOD_PUT);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }

    





 
    
    

}
