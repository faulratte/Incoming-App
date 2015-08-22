package fhws.marcelgross.incoming.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fhws.marcelgross.incoming.Objects.ContactObject;
import fhws.marcelgross.incoming.Objects.EventsObject;
import fhws.marcelgross.incoming.Objects.LinksObject;
import fhws.marcelgross.incoming.Objects.NavigationObject;
import fhws.marcelgross.incoming.Objects.NewsObject;
import fhws.marcelgross.incoming.R;


public class DBAdapter extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "incoming_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CONTACT = "contact";
    private static final String COLUMN_CONTACT_ID = "contactId";
    private static final String COLUMN_CONTACT_TITLE = "contactTitle";
    private static final String COLUMN_CONTACT_STATUS = "contactStatus";
    private static final String COLUMN_CONTACT_FIRSTNAME = "contactFirstname";
    private static final String COLUMN_CONTACT_LASTNAME = "contactLastname";
    private static final String COLUMN_CONTACT_PHONE = "contactPhone";
    private static final String COLUMN_CONTACT_FAX = "contactFax";
    private static final String COLUMN_CONTACT_EMAIL = "contactEmail";
    private static final String COLUMN_CONTACT_HOMEPAGE = "contactHomepage";
    private static final String COLUMN_CONTACT_STREET = "contactStreet";
    private static final String COLUMN_CONTACT_ZIP = "contactZip";
    private static final String COLUMN_CONTACT_CITY = "contactCity";
    private static final String COLUMN_CONTACT_ROOM = "contactRoom";
    private static final String COLUMN_CONTACT_CONSULTATIONHOUR = "contactConsultationHour";
    private static final String COLUMN_CONTACT_PHOTO = "contactPhoto";
    private static final String COLUMN_CONTACT_FULLNAME = "contactFullname";

    private static final String TABLE_EVENT = "event";
    private static final String COLUMN_EVENT_ID = "eventId";
    private static final String COLUMN_EVENT_TITLE = "eventTitle";
    private static final String COLUMN_EVENT_TEXT = "eventText";
    private static final String COLUMN_EVENT_TERMIN = "eventTermin";
    private static final String COLUMN_EVENT_TIME = "eventTime";
    private static final String COLUMN_EVENT_CATEGORY = "eventCategory";
    private static final String COLUMN_EVENT_FAVORITE = "eventFavorite";
    private static final String COLUMN_EVENT_LOCATION = "eventLocation";
    private static final String COLUMN_EVENT_CONTACTPERSON = "eventContactPerson";
    private static final String COLUMN_EVENT_CONTACTDATA = "eventContactData";
    private static final String COLUMN_EVENT_CREATED = "eventCreated";

    private static final String TABLE_LINK = "link";
    private static final String COLUMN_LINK_ID = "linkId";
    private static final String COLUMN_LINK_TITLE = "linkTitle";
    private static final String COLUMN_LINK_URL = "linkUrl";
    private static final String COLUMN_LINK_CREATED = "linkCreated";

    private static final String TABLE_NAVIGATION = "navigation";
    private static final String COLUMN_NAVIGATION_ID = "navigationId";
    private static final String COLUMN_NAVIGATION_STREET = "navigationStreet";
    private static final String COLUMN_NAVIGATION_HOUSENUMBER = "navigationHousNumber";
    private static final String COLUMN_NAVIGATION_ZIP = "navigationZip";
    private static final String COLUMN_NAVIGATION_CITY = "navigationCity";
    private static final String COLUMN_NAVIGATION_FAVORITE = "navigationFavorite";
    private static final String COLUMN_NAVIGATION_CATEGORY = "navigationCategory";
    private static final String COLUMN_NAVIGATION_NAME = "navigationName";
    private static final String COLUMN_NAVIGATION_PHONE = "navigationPhone";
    private static final String COLUMN_NAVIGATION_CONTACTPERSON = "navigationContactPerson";
    private static final String COLUMN_NAVIGATION_OPENINGHOURS = "navigationOpeningHours";
    private static final String COLUMN_NAVIGATION_LONGITUDE = "navigationLongitude";
    private static final String COLUMN_NAVIGATION_LATITUDE = "navigationLatitude";
    private static final String COLUMN_NAVIGATION_PICTURE = "navigationPicture";

    private static final String TABLE_NEWS = "news";
    private static final String COLUMN_NEWS_ID = "newsId";
    private static final String COLUMN_NEWS_TITLE = "newsTitle";
    private static final String COLUMN_NEWS_TEXT = "newsText";
    private static final String COLUMN_NEWS_DATE = "newsDate";
    private static final String COLUMN_NEWS_VALIDTO = "newsValidTo";
    private static final String COLUMN_NEWS_CREATED = "newsCreated";

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private String sqlQueryContact(){
        return "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACT
                + "("
                + COLUMN_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CONTACT_TITLE + " TEXT, "
                + COLUMN_CONTACT_STATUS + " TEXT, "
                + COLUMN_CONTACT_FIRSTNAME + " TEXT, "
                + COLUMN_CONTACT_LASTNAME  + " TEXT, "
                + COLUMN_CONTACT_PHONE + " TEXT, "
                + COLUMN_CONTACT_FAX + " TEXT, "
                + COLUMN_CONTACT_EMAIL + " TEXT, "
                + COLUMN_CONTACT_HOMEPAGE + " TEXT, "
                + COLUMN_CONTACT_STREET + " TEXT, "
                + COLUMN_CONTACT_ZIP + " INTEGER, "
                + COLUMN_CONTACT_CITY + " TEXT, "
                + COLUMN_CONTACT_ROOM + " TEXT, "
                + COLUMN_CONTACT_CONSULTATIONHOUR + " TEXT, "
                + COLUMN_CONTACT_PHOTO + " TEXT "
                + ")";
    }
    private String sqlQueryEvent(){
        return "CREATE TABLE IF NOT EXISTS " + TABLE_EVENT
                + "("
                + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EVENT_TITLE + " TEXT, "
                + COLUMN_EVENT_TEXT + " TEXT, "
                + COLUMN_EVENT_TERMIN + " TEXT, "
                + COLUMN_EVENT_TIME + " TEXT, "
                + COLUMN_EVENT_CATEGORY + " TEXT, "
                + COLUMN_EVENT_FAVORITE + " TEXT, "
                + COLUMN_EVENT_LOCATION + " TEXT, "
                + COLUMN_EVENT_CONTACTPERSON + " TEXT, "
                + COLUMN_EVENT_CONTACTDATA + " TEXT, "
                + COLUMN_EVENT_CREATED + " TEXT "
                + ")";
    }
    private String sqlQueryLinks(){
        return "CREATE TABLE IF NOT EXISTS " + TABLE_LINK
                + "("
                + COLUMN_LINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LINK_TITLE + " TEXT, "
                + COLUMN_LINK_URL + " TEXT, "
                + COLUMN_LINK_CREATED + " TEXT "
                + ")";
    }
    private String sqlQueryNavigation(){
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAVIGATION
                + "("
                + COLUMN_NAVIGATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAVIGATION_FAVORITE + " TEXT, "
                + COLUMN_NAVIGATION_CATEGORY + " TEXT, "
                + COLUMN_NAVIGATION_NAME + " TEXT, "
                + COLUMN_NAVIGATION_STREET + " TEXT, "
                + COLUMN_NAVIGATION_HOUSENUMBER + " INTEGER, "
                + COLUMN_NAVIGATION_ZIP + " INTEGER, "
                + COLUMN_NAVIGATION_CITY + " TEXT, "
                + COLUMN_NAVIGATION_PHONE+ " TEXT, "
                + COLUMN_NAVIGATION_CONTACTPERSON + " TEXT, "
                + COLUMN_NAVIGATION_OPENINGHOURS + " TEXT, "
                + COLUMN_NAVIGATION_LONGITUDE + " REAL, "
                + COLUMN_NAVIGATION_LATITUDE + " REAL, "
                + COLUMN_NAVIGATION_PICTURE + " TEXT "
                + ")";
    }
    private String sqlQueryNews(){
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NEWS
                + "("
                + COLUMN_NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NEWS_TITLE + " TEXT, "
                + COLUMN_NEWS_TEXT + " TEXT, "
                + COLUMN_NEWS_DATE + " TEXT, "
                + COLUMN_NEWS_VALIDTO + " TEXT, "
                + COLUMN_NEWS_CREATED + " TEXT "
                + ")";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("create database", DATABASE_NAME);
        db.execSQL(sqlQueryContact());
        db.execSQL(sqlQueryEvent());
        db.execSQL(sqlQueryLinks());
        db.execSQL(sqlQueryNavigation());
        db.execSQL(sqlQueryNews());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.d("update database", DATABASE_NAME);
        db.execSQL("DROP DATABASE");
        onCreate(db);
    }

    //save date into DB
    public void saveContacts(List<ContactObject> contacts){
        deleteTable(TABLE_CONTACT);
        SQLiteDatabase db = this.getWritableDatabase();
        int counter = 0;
        for (ContactObject current : contacts){
            ContentValues values = new ContentValues();
            values.put(COLUMN_CONTACT_ID, current.getId());
            values.put(COLUMN_CONTACT_TITLE, current.getTitle());
            values.put(COLUMN_CONTACT_STATUS, current.getStatus());
            values.put(COLUMN_CONTACT_FIRSTNAME, current.getFirstname());
            values.put(COLUMN_CONTACT_LASTNAME, current.getLastname());
            values.put(COLUMN_CONTACT_PHONE, current.getTel());
            values.put(COLUMN_CONTACT_FAX, current.getFax());
            values.put(COLUMN_CONTACT_EMAIL, current.getEmail());
            values.put(COLUMN_CONTACT_HOMEPAGE, current.getHomepage());
            values.put(COLUMN_CONTACT_STREET, current.getStreet());
            values.put(COLUMN_CONTACT_ZIP, current.getZip());
            values.put(COLUMN_CONTACT_CITY, current.getCity());
            values.put(COLUMN_CONTACT_ROOM, current.getRoom());
            values.put(COLUMN_CONTACT_CONSULTATIONHOUR, current.getConsultationhour());
            values.put(COLUMN_CONTACT_PHOTO, current.getPhoto());
            db.insert(TABLE_CONTACT, null, values);
            ++counter;
        }
        db.close();
        Log.d("Save Contacts", counter + " from " + contacts.size());
    }
    public void saveEvents(List<EventsObject> events){
        deleteTable(TABLE_EVENT);
        SQLiteDatabase db = this.getWritableDatabase();
        int counter = 0;
        for (EventsObject current : events){
            ContentValues values = new ContentValues();
            values.put(COLUMN_EVENT_ID, current.getId());
            values.put(COLUMN_EVENT_TITLE, current.getTitle());
            values.put(COLUMN_EVENT_TEXT, current.getText());
            values.put(COLUMN_EVENT_TERMIN, current.getTermin());
            values.put(COLUMN_EVENT_TIME, current.getTime());
            values.put(COLUMN_EVENT_CATEGORY, current.getCategory());
            values.put(COLUMN_EVENT_FAVORITE, current.getFavorite());
            values.put(COLUMN_EVENT_LOCATION, current.getLocation());
            values.put(COLUMN_EVENT_CONTACTPERSON, current.getContactperson());
            values.put(COLUMN_EVENT_CONTACTDATA, current.getContactdata());
            values.put(COLUMN_EVENT_CREATED, current.getCreated());
            db.insert(TABLE_EVENT, null, values);
            ++counter;
        }
        db.close();
        Log.d("Save Events", counter + " from " + events.size());
    }
    public void saveLinks(List<LinksObject> links){
        deleteTable(TABLE_LINK);
        SQLiteDatabase db = this.getWritableDatabase();
        int counter = 0;
        for (LinksObject current : links){
            ContentValues values = new ContentValues();
            values.put(COLUMN_LINK_ID, current.getId());
            values.put(COLUMN_LINK_TITLE, current.getTitle());
            values.put(COLUMN_LINK_URL, current.getUrl());
            values.put(COLUMN_LINK_CREATED, current.getCreated());
            db.insert(TABLE_LINK, null, values);
            ++counter;
        }
        db.close();
        Log.d("Save Links", counter + " from " + links.size());
    }
    public void savePois(List<NavigationObject> pois){
        deleteTable(TABLE_NAVIGATION);
        SQLiteDatabase db = this.getWritableDatabase();
        int counter = 0;
        for (NavigationObject current : pois){
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAVIGATION_ID, current.getId());
            values.put(COLUMN_NAVIGATION_FAVORITE, current.getFavorite());
            values.put(COLUMN_NAVIGATION_CATEGORY, current.getCategory());
            values.put(COLUMN_NAVIGATION_NAME, current.getName());
            values.put(COLUMN_NAVIGATION_STREET, current.getStreet());
            values.put(COLUMN_NAVIGATION_HOUSENUMBER, current.getHouse_NR());
            values.put(COLUMN_NAVIGATION_ZIP, current.getZipcode());
            values.put(COLUMN_NAVIGATION_CITY, current.getCity());
            values.put(COLUMN_NAVIGATION_PHONE, current.getPhone());
            values.put(COLUMN_NAVIGATION_CONTACTPERSON, current.getContactperson());
            values.put(COLUMN_NAVIGATION_OPENINGHOURS, current.getOeffnungszeiten());
            values.put(COLUMN_NAVIGATION_LONGITUDE, current.getLongitude());
            values.put(COLUMN_NAVIGATION_LATITUDE, current.getLatitude());
            values.put(COLUMN_NAVIGATION_PICTURE, current.getPic());
            db.insert(TABLE_NAVIGATION, null, values);
            ++counter;
        }
        db.close();
        Log.d("Save Pois", counter + " from " + pois.size());
    }
    public void saveNews(List<NewsObject> news){
        deleteTable(TABLE_NEWS);
        SQLiteDatabase db = this.getWritableDatabase();
        int counter = 0;
        for (NewsObject current : news){
            ContentValues values = new ContentValues();
            values.put(COLUMN_NEWS_ID, current.getId());
            values.put(COLUMN_NEWS_TITLE, current.getTitle());
            values.put(COLUMN_NEWS_TEXT, current.getText());
            values.put(COLUMN_NEWS_DATE, String.valueOf(current.getDate()));
            values.put(COLUMN_NEWS_VALIDTO, String.valueOf(current.getValidTo()));
            values.put(COLUMN_NEWS_CREATED, current.getCreated());
            db.insert(TABLE_NEWS, null, values);
            ++counter;
        }
        db.close();
        Log.d("Save News", counter + " from " + news.size());
    }

    //get collection of data from DB
    public ArrayList<ContactObject> getAllContacts(){
        ArrayList<ContactObject> contacts = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ContactObject contact = new ContactObject();
                contact.setId(cursor.getLong(0));
                contact.setTitle(cursor.getString(1));
                contact.setStatus(cursor.getString(2));
                contact.setFirstname(cursor.getString(3));
                contact.setLastname(cursor.getString(4));
                contact.setTel(cursor.getString(5));
                contact.setFax(cursor.getString(6));
                contact.setEmail(cursor.getString(7));
                contact.setHomepage(cursor.getString(8));
                contact.setStreet(cursor.getString(9));
                contact.setZip(cursor.getInt(10));
                contact.setCity(cursor.getString(11));
                contact.setRoom(cursor.getString(12));
                contact.setConsultationhour(cursor.getString(13));
                contact.setPhoto(cursor.getString(14));
                contact.setFullName(cursor.getString(3) + " " + cursor.getString(4));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("return contacts", String.valueOf(contacts.size()));
        return contacts;
    }
    public ArrayList<EventsObject> getAllEvents(boolean[] checkBoxen){
        ArrayList<EventsObject> events = new ArrayList<>();
        String[] categoryArray = new String[]{context.getResources().getString(R.string.fhws), context.getResources().getString(R.string.uni), context.getResources().getString(R.string.isc), context.getResources().getString(R.string.holiday)};
        boolean completeFalse = true;
        for (boolean c : checkBoxen){
            if (c)
                completeFalse = false;
        }

        if (!completeFalse){
            String query =getCategorizedSqlStatement(checkBoxen, categoryArray, TABLE_EVENT, COLUMN_EVENT_CATEGORY);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do {
                    EventsObject event = new EventsObject();
                    event.setId(cursor.getLong(0));
                    event.setTitle(cursor.getString(1));
                    event.setText(cursor.getString(2));
                    event.setTermin(cursor.getString(3));
                    event.setTime(cursor.getString(4));
                    event.setCategory(cursor.getString(5));
                    event.setFavorite(cursor.getString(6));
                    event.setLocation(cursor.getString(7));
                    event.setContactperson(cursor.getString(8));
                    event.setContactdata(cursor.getString(9));
                    event.setCreated(cursor.getString(10));
                    events.add(event);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            Log.d("return events", String.valueOf(events.size()));
        }



        return events;
    }
    public ArrayList<LinksObject> getAllLinks(){
        ArrayList<LinksObject> links = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_LINK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                LinksObject link = new LinksObject();
                link.setId(cursor.getLong(0));
                link.setTitle(cursor.getString(1));
                link.setUrl(cursor.getString(2));
                link.setCreated(cursor.getString(3));
                links.add(link);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("return links", String.valueOf(links.size()));
        return links;
    }
    public ArrayList<NavigationObject> getAllPois(boolean[] checkBoxen){
        ArrayList<NavigationObject> pois = new ArrayList<>();
        String[] categoryArray = new String[]{context.getResources().getString(R.string.libary), context.getResources().getString(R.string.eatDrink), context.getResources().getString(R.string.dormitory), context.getResources().getString(R.string.fhws_building)};
        boolean completeFalse = true;
        for (boolean c : checkBoxen){
            if (c)
                completeFalse = false;
        }
        if (!completeFalse){
            String query = getCategorizedSqlStatement(checkBoxen, categoryArray, TABLE_NAVIGATION, COLUMN_NAVIGATION_CATEGORY);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                do {
                    NavigationObject poi = new NavigationObject();
                    poi.setId(cursor.getLong(0));
                    poi.setFavorite(cursor.getString(1));
                    poi.setCategory(cursor.getString(2));
                    poi.setName(cursor.getString(3));
                    poi.setStreet(cursor.getString(4));
                    poi.setHouse_NR(cursor.getInt(5));
                    poi.setZipcode(cursor.getInt(6));
                    poi.setCity(cursor.getString(7));
                    poi.setPhone(cursor.getString(8));
                    poi.setContactperson(cursor.getString(9));
                    poi.setOeffnungszeiten(cursor.getString(10));
                    poi.setLongitude(cursor.getDouble(11));
                    poi.setLatitude(cursor.getDouble(12));
                    poi.setPic(cursor.getString(13));
                    pois.add(poi);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            Log.d("return pois", String.valueOf(pois.size()));
        }

        return pois;
    }
    public ArrayList<NewsObject> getAllNews(){
        ArrayList<NewsObject> news = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NEWS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                NewsObject newsObject = new NewsObject();
                newsObject.setId(cursor.getLong(0));
                newsObject.setTitle(cursor.getString(1));
                newsObject.setText(cursor.getString(2));
                newsObject.setDate(cursor.getString(3));
                newsObject.setValidTo(cursor.getString(4));
                newsObject.setCreated(cursor.getString(5));
                news.add(newsObject);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("return news", String.valueOf(news.size()));
        return news;
    }


    public ArrayList<Long> getAllNewsIDs(){
        ArrayList<Long> ids = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NEWS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ids.add(cursor.getLong(cursor.getColumnIndex(COLUMN_NEWS_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("return newsIDs", String.valueOf(ids.size()));
        return ids;
    }
    public ArrayList<Long> getAllEventsIDs(){
        ArrayList<Long> ids = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_EVENT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ids.add(cursor.getLong(cursor.getColumnIndex(COLUMN_EVENT_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("return eventIDs", String.valueOf(ids.size()));
        return ids;
    }
    public ArrayList<Long> getAllLinksIDs(){
        ArrayList<Long> ids = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_LINK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ids.add(cursor.getLong(cursor.getColumnIndex(COLUMN_LINK_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("return linksIDs", String.valueOf(ids.size()));
        return ids;
    }
    public ArrayList<Long> getAllContactIDs(){
        ArrayList<Long> ids = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ids.add(cursor.getLong(cursor.getColumnIndex(COLUMN_CONTACT_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("return contactIDs", String.valueOf(ids.size()));
        return ids;
    }
    public ArrayList<Long> getAllPoiIDs(){
        ArrayList<Long> ids = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAVIGATION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ids.add(cursor.getLong(cursor.getColumnIndex(COLUMN_NAVIGATION_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("return navigationIDs", String.valueOf(ids.size()));
        return ids;
    }

    public void deleteTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
    }


    private static String getCategorizedSqlStatement(boolean[] checkboxen, String[] category, String tableName, String columnName){
        {
            String result = "";

            boolean completeFalse = true;
            for (boolean c : checkboxen){
                if (c)
                    completeFalse = false;
            }


            if (!completeFalse){
                for (int i = 0; i < checkboxen.length; i++) {
                    if (checkboxen[i])
                    {
                        String next = category[i];

                        if (result.equals(""))
                        {
                            result += " WHERE " + columnName + " = '" + next + "'";
                        }
                        else
                        {
                            result += " OR " + columnName + " = '" + next + "'";
                        }
                    }
                }

                result = "SELECT * FROM " + tableName + result;
            }
            else
                result = null;

            return result;
        }
    }
}
