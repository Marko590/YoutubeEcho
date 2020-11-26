package com.github.Marko590;

public enum YoutubeEnum {
    NONE(0),
    HTTPS(1),
    REGULAR(2);
    private final int format;
    private YoutubeEnum(int format){this.format=format;}
    public static YoutubeEnum fromInt(int format){
        switch(format){
            case 1:return HTTPS;
            case 2:return REGULAR;
            default: return NONE;
        }

    }
}
