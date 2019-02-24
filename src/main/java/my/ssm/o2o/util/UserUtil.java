package my.ssm.o2o.util;

/**  
 * <p>本地用户工具类</p>
 * <p>Date: 2019年2月24日</p>
 * @author Wanghui    
 */  
public final class UserUtil {
    private UserUtil() {}
    public static boolean isCustomer(Integer userType) {
        if(userType == null) {
            return false;
        }
        return (userType & 0x01) == 1;
    }
    public static boolean isShopOwner(Integer userType) {
        if(userType == null) {
            return false;
        }
        return (userType & 0x02) == 2;
    }
    public static boolean isSuperAdministrator(Integer userType) {
        if(userType == null) {
            return false;
        }
        return (userType & 0x08) == 8;
    }
    public static Integer appendCustomer(Integer oldUserType, Integer addedUserType) {
        if(oldUserType == null) {
            return addedUserType;
        }
        return oldUserType | addedUserType;
    }
}
