package me.sr1.omanyte.base.util;

/**
 * @author SR1
 */

public class UnitTestBase {
    static {
        LogUtil.setLogProxy(new UnitTestLogProxy());
    }
}
