# Bucket-Key-Value Database for Android
 [ ![Download](https://api.bintray.com/packages/madhavanmalolan/android/bucketkeyvaluedb/images/download.svg) ](https://bintray.com/madhavanmalolan/android/bucketkeyvaluedb/_latestVersion)

This is a very simple Key-Value Database for Android.
It is a wrapper around the inbuilt SQLite Db.

It is fast, easy to integrate, and useful for every project.


## Gradle Build
Add this to the app level build.gradle
```
    compile 'com.madhavanmalolan.android:bucketkeyvaluedb:0.0.1'
```

## Usage

Every record stored contains
- Bucket
- Key
- Value

Initiate object :
```
    KeyValDbHelper dbHelper = KeyValDbHelper(context);
```

Put:
```
    dbHelper.put(BUCKET_NAME,KEY,VALUE);
    dbHelper.put(BUCKET_NAME,KEY); // equivalent to dbHelper.put(BUCKET_NAME, KEY,"")
```

Get :
```
    String value = dbHelper.get(BUCKET_NAME, KEY);
    List<String> keys = dbHelper.getKeys(BUCKET_NAME);
```

## Coming soon
Support for more data types

## Contribute
PRs open!

## License
MIT
