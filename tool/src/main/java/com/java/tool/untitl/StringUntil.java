package com.java.tool.untitl;

/**
 *
 *
 * @author xuweizhi
 * @date 2019/01/04 10:50
 */
public class StringUntil {

    private static final String PACKAGE_SEPARATOR_CHAR = ".";

    /**
     * Generates a simplified name from a {@link Class}.  Similar to {@link Class#getSimpleName()}, but it works fine
     * with anonymous classes. -- netty resoure
     */
    public static String simpleClassName(Class<?> clazz,String msg) {
        String className = checkNotNull(clazz, msg).getName();
        final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
        if (lastDotIdx > -1) {
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }

    /**
     * Checks that the given argument is not null. If it is, throws {@link NullPointerException}.
     * Otherwise, returns the argument.  -- netty resoure
     */
    public static <T> T checkNotNull(T arg, String msg) {
        if (arg == null) {
            throw new NullPointerException(msg);
        }
        return arg;
    }


}
