package com.github.Marko590;

public enum YoutubeEnum {
    NONE(0),
    HTTPS_REGULAR(1),
    REGULAR(2),
    HTTPS_SHORT(3),
    SHORT(4);
    private final int format;
    private YoutubeEnum(int format){this.format=format;}
    public static YoutubeEnum fromInt(int format){
        switch(format){
            case 1:return HTTPS_REGULAR;
            case 2:return REGULAR;
            case 3:return HTTPS_SHORT;
            case 4:return SHORT;
            default: return NONE;
        }

    }
}
