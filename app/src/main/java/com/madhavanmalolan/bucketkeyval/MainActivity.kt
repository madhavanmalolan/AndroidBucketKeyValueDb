package com.madhavanmalolan.bucketkeyval

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.madhavanmalolan.bucketkeyvaluedb.KeyValDbHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var keyValDbHelper = KeyValDbHelper(this)

        // Inserting Key-value
        val KEYVAL_BUCKET = "KEYVAL_BUCKET"
        keyValDbHelper.put(KEYVAL_BUCKET, "hello", "world")
        val value = keyValDbHelper.get(KEYVAL_BUCKET, "hello")

        /*Inserting only keys*/
        val LIST_BUCKET = "LISTBUCKET"
        keyValDbHelper.put(LIST_BUCKET, "item1")
        keyValDbHelper.put(LIST_BUCKET, "item2", "val2")
        keyValDbHelper.put(LIST_BUCKET, "item3", "val3")
        val items = keyValDbHelper.getKeys(LIST_BUCKET)
        //items = ["item1", "item2", "item3"]
        val itemValue1 = keyValDbHelper.get(LIST_BUCKET,"item1")
        //itemValue1 = ""
        val itemValue2 = keyValDbHelper.get(LIST_BUCKET, "item2")
        //itemValue2 = "val2"

    }
}
