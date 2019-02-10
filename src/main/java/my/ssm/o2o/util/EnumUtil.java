package my.ssm.o2o.util;

import java.lang.reflect.InvocationTargetException;

import my.ssm.o2o.enums.OperStateEnum;

/**  
 * <p>枚举工具类</p>
 * <p>Date: 2019年2月9日</p>
 * @author Wanghui    
 */  
public final class EnumUtil {
    private EnumUtil() {}
    /**  
     * <p>返回指定状态对应的枚举值</p>  
     * @param state 状态
     * @param enumType 枚举类型
     * @return 枚举值
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException  
     */  
    public static OperStateEnum valueOf(Integer state, Class<? extends OperStateEnum> enumType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        OperStateEnum[] values = (OperStateEnum[]) enumType.getMethod("values").invoke(null);
        for(OperStateEnum value : values) {
            if(value.getState().equals(state)) {
                return value;
            }
        }
        return null;
    }
}
