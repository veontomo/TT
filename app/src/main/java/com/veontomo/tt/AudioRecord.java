package com.veontomo.tt;

import android.os.Environment;

/**
 * Audio record of given tongue twister
 *
 * @author veontomo@gmail.com
 * @since 0.1
 */
public class AudioRecord {
    /**
     * number under which this audio record is identified.
     * <p/>
     * If it equal to -1, it means that this instance has not been saved yet.
     */
    public final short id;
    /**
     * id of a tongue twister the current audio record refers to.
     */
    public final short tt_id;
    /**
     * Name of the file the current audio record will be saved to.
     */
    public final String fileName;
    /**
     * Time in milliseconds of
     */
    public final long time;

    /**
     * Constructor to use when one creates an audio record that has not yet been saved in db.
     * @param tongueTwisterId
     */
    public AudioRecord(short tongueTwisterId) {
        this.tt_id = tongueTwisterId;
        this.id = -1;
        this.time = System.currentTimeMillis();
        this.fileName = generateFileName(tt_id, String.valueOf(this.time));
    }

    /**
     * Constructor to use when one creates an audio record that has already been saved in db.
     * @param tongueTwisterId
     * @param id
     */
    public AudioRecord(short tongueTwisterId, short id) {
        this.tt_id = tongueTwisterId;
        this.id = id;
        this.time = System.currentTimeMillis();
        this.fileName = generateFileName(tt_id, String.valueOf(this.time));
    }

    /**
     * Generates a file path where the audio record should be saved.
     *
     * @param tt_id id of the tongue twister the current record refers to
     * @param marker unique marker by means of which the current record is distinguished
     *               from other records.
     * @return complete file path
     */
    private String generateFileName(short tt_id, String marker) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/" + String.valueOf(tt_id) +
                "/" + marker + ".3gp";
    }
}
