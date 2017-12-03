package me.sr1.omanyte.model;

/**
 * 网络业务回调
 * @author SR1
 */

public interface BusinessCallback<DataType, ErrorType> {

    void onSuccess(DataType data);

    void onError(ErrorType error);
}
