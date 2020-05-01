package grossary.cyron.com.grossarybrch.productList;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import grossary.cyron.com.grossarybrch.R;

@Layout(R.layout.child_layout)
public class ChildView {
    private static String TAG ="ChildView";

    @View(R.id.child_name)
    TextView textViewChild;

    @View(R.id.child_desc)
    TextView textViewDesc;

    @View(R.id.child_image)
    ImageView childImage;

    private Context mContext;
    private ProductlistModel movie;

    public ChildView(Context mContext, ProductlistModel movie) {
        this.mContext = mContext;
        this.movie = movie;
    }

    @Resolve
    private void onResolve(){
        Log.d(TAG,"onResolve");
        textViewChild.setText(movie.getProductName());
        textViewDesc.setText("₹"+movie.getMRPPrice().toString());
        }
}
