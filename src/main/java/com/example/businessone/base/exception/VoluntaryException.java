package com.example.businessone.base.exception;

/**
 * @description: 这里用一句话描述这个类的作用 <br>
 * @version: 1.0 <br>
 * @author: leevi.li <br>
 * @create: 2023/3/16 15:02 <br>
 * @update: 2023/3/16 15:02 <br>
 * @since jdk11.0.5_10
 */
public class VoluntaryException extends RuntimeException{

    private static final long serialVersionUID = 5044938065901970022L;

    public VoluntaryException() {
    }

    public VoluntaryException(String message) {
        super(message);
    }

    public VoluntaryException(String message, Throwable cause) {
        super(message, cause);
    }

}
