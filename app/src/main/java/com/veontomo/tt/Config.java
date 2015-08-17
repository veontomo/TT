package com.veontomo.tt;
/**
 * Configuration parameters
 *
 */
public abstract class Config {
    public static final String TAG = "TT";
    /**
     * token name with which the tongue-twister id is passed from one activity to another
     */
    public final static String TT_ID_KEY = "tt_id";

    /**
     * token name with which the tongue-twister text is passed from one activity to another
     */
    public final static String TT_TEXT_KEY = "tt_text";

    /**
     * Name of the directory in which audio records of the tongue-twisters are stored.
     * (for the moment this directory is supposed to be inside the external storage)
     */
    public final static String DIR_NAME = "TongueTwisters";
}
