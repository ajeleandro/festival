package com.artefacto1971.festival.logic;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.artefacto1971.festival.R;
import com.artefacto1971.festival.classes.Promo;

public class PromoAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater = null;
    ArrayList<Promo> promoList;		
    public static final String TAG = PromoAdapter.class.getSimpleName();
    int lastPosition = -1;
    
    public PromoAdapter(Context context, ArrayList<Promo> promoList) {
        this.context = context;
        this.promoList = promoList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
    	return promoList.size();
    }

    @Override
    public Object getItem(int position) {
    	return promoList.get(position);
    }

    @Override
    public long getItemId(int position) {
    	return promoList.get(position).getId();
    }

    @Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
    	//return false;
	}

	@Override
    public View getView(int position, View view, ViewGroup parent) {
        
		view = inflater.inflate(R.layout.layout_promo_row, null);        
        
        TextView textViewTitle = (TextView) view.findViewById(R.id.Title);
        textViewTitle.setText(promoList.get(position).getTitle());
        
        TextView textViewBody = (TextView) view.findViewById(R.id.Body);        
        textViewBody.setText(promoList.get(position).getBody());
        
        TextView textViewDate = (TextView) view.findViewById(R.id.Date);
        textViewDate.setText(promoList.get(position).getDate().toString().split(" 00")[0]);
        
        ImageView imageView = (ImageView) view.findViewById(R.id.promoImage);
        new ImageDownloaderTask(imageView,view.getContext(),promoList.get(position).getId() + "","promo","picture").execute(promoList.get(position).getPicture());

     // ANIMATION
        if((position != 0) && (position > lastPosition)){
			Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.animation_up_from_bottom);
		    animation.setDuration(600);
			view.startAnimation(animation);
        }
	    lastPosition = position;
	    
        return view;
    }
}
