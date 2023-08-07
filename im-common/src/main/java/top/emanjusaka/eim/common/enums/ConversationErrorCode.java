package top.emanjusaka.eim.common.enums;


import top.emanjusaka.eim.common.exception.ApplicationExceptionEnum;

/**
 * @author xiongwei
 * @description:
 **/
public enum ConversationErrorCode implements ApplicationExceptionEnum {

    CONVERSATION_UPDATE_PARAM_ERROR(50000, "會話修改參數錯誤"),


    ;

    private int code;
    private String error;

    ConversationErrorCode(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public int getCode() {
        return this.code;
    }

    public String getError() {
        return this.error;
    }

}
