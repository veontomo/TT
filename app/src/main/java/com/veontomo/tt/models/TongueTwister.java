package com.veontomo.tt.models;

import android.media.ToneGenerator;

/**
 * Tongue twister class.
 *
 * @author veontomo@gmail.com
 * @since 0.1
 */
public class TongueTwister {

    /**
     * identification number with which the tongue twister is saved in db.
     *
     * If it is set to -1, it means that it has not been saved yet.
     */
    public final short id;
    /**
     * The text of the tongue twister
     */
    public final String text;

    public TongueTwister(short id, String text){
        this.id = id;
        this.text = text;
    }

    public TongueTwister(String text){
        this.id = -1;
        this.text = text;
    }

}
