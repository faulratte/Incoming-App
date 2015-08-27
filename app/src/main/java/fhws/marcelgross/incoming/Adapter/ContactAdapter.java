package fhws.marcelgross.incoming.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fhws.marcelgross.incoming.Objects.ContactObject;
import fhws.marcelgross.incoming.R;
import fhws.marcelgross.incoming.Volley.AppController;

/**
 * Created by Marcel on 20.05.2015.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<ContactObject> contactObjects;
    private int rowLayout;
    private Context context;

    public ContactAdapter(List<ContactObject> contactObjects, int rowLayout, Context context) {
        this.contactObjects = contactObjects;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ContactObject contactObject = contactObjects.get(position);

        Log.d( "Person", "Show person " + contactObject.getEmail() );
        holder.name_tv.setText(contactObject.getTitle()+" "+contactObject.getFirstname()+ " "+contactObject.getLastname());
        holder.status_tv.setText(contactObject.getStatus());
        holder.consultationHour_tv.setText(contactObject.getConsultationhour());
        holder.office_tv.setText(contactObject.getStreet());
        holder.room_tv.setText(contactObject.getRoom());
        holder.zipCode_tv.setText(String.valueOf(contactObject.getZip()));
        holder.city_tv.setText(contactObject.getCity());

        if (!contactObject.getHomepage().trim().isEmpty()) {
            holder.homepage_tv.setText(R.string.homepage);
            holder.homepage_tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(contactObject.getHomepage()));
                    context.startActivity(i);
                }
            });
        } else {
            holder.homepage_tv.setText(contactObject.getHomepage());
        }

        if( contactObject.getPhoto().startsWith("http" ))
        {
            AppController.getInstance( this.context ).getImageLoader().get(contactObject.getPhoto(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.profile_pic.setImageBitmap( response.getBitmap() );
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        else
        {
            holder.profile_pic.setImageResource( R.mipmap.pic_placeholder );
        }

        holder.call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                String p = "tel:" + contactObject.getTel();
                i.setData(Uri.parse(p));
                context.startActivity(i);
            }
        });
        holder.mail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { contactObject.getEmail() });
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactObjects == null ? 0 : contactObjects.size();
    }

    public void animateTo(ArrayList<ContactObject> contacts) {
        applyAndAnimateRemovals(contacts);
        applyAndAnimateAdditions(contacts);
        applyAndAnimateMovedItems(contacts);
    }

    private void applyAndAnimateRemovals(ArrayList<ContactObject> newContacts){
        for (int i = contactObjects.size() - 1; i >= 0; i--) {
            final ContactObject contact = contactObjects.get(i);
            if (!newContacts.contains(contact)) {
                removeItem(i);
            }
        }
    }
    private void applyAndAnimateAdditions(ArrayList<ContactObject> newContacts) {
        for (int i = 0; i < newContacts.size(); i++) {
            final ContactObject contact = newContacts.get(i);
            if (!contactObjects.contains(contact)) {
                addItem(i, contact);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<ContactObject> newContacts) {
        for (int toPosition = newContacts.size() - 1; toPosition >= 0; toPosition--) {
            final ContactObject contact = newContacts.get(toPosition);
            final int fromPosition = contactObjects.indexOf(contact);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public ContactObject removeItem(int position) {
        final ContactObject contactObject = contactObjects.remove(position);
        notifyItemRemoved(position);
        return contactObject;
    }

    public void addItem(int position, ContactObject contactObject) {
        contactObjects.add(position, contactObject);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final ContactObject contactObject = contactObjects.remove(fromPosition);
        contactObjects.add(toPosition, contactObject);
        notifyItemMoved(fromPosition, toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name_tv, status_tv, consultationHour_tv, office_tv, room_tv, zipCode_tv, city_tv, homepage_tv;
        public ImageView profile_pic;
        public ImageButton mail_btn, call_btn;

       public ViewHolder(View itemView) {
           super(itemView);
           name_tv = (TextView) itemView.findViewById(R.id.contactCard_name_tv);
           status_tv = (TextView) itemView.findViewById(R.id.contactCard_status_tv);
           consultationHour_tv = (TextView) itemView.findViewById(R.id.contactCard_consultationHour_tv);
           office_tv = (TextView) itemView.findViewById(R.id.contactCard_office_tv);
           room_tv = (TextView) itemView.findViewById(R.id.contactCard_room_tv);
           zipCode_tv = (TextView) itemView.findViewById(R.id.contactCard_zipCode_tv);
           city_tv = (TextView) itemView.findViewById(R.id.contactCard_city_tv);
           homepage_tv = (TextView) itemView.findViewById(R.id.contactCard_homepage_tv);

           profile_pic = (ImageView) itemView.findViewById(R.id.profile_pic);

           mail_btn = (ImageButton) itemView.findViewById(R.id.contactCard_mail_btn);
           call_btn = (ImageButton) itemView.findViewById(R.id.contactCard_call_btn);
       }
   }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
        }
    }
}

