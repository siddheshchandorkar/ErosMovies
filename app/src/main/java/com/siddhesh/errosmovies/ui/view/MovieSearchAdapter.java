package com.siddhesh.errosmovies.ui.view;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.erostest.model.MovieListItem;
import com.siddhesh.errosmovies.R;
import com.siddhesh.errosmovies.ui.MovieSearchAPI;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class MovieSearchAdapter extends ArrayAdapter<MovieListItem> {

    private ArrayList<MovieListItem> alOriginalMovie;
    private ArrayList<MovieListItem> alMovies;
    private MovieListFilter filter;
    private Context context;
    private MovieSearchAPI movieSearchAPI = new MovieSearchAPI();


    public MovieSearchAdapter(Context context, int textViewResourceId,
                           ArrayList<MovieListItem> alMovies) {
        super(context, textViewResourceId, alMovies);
        this.context = context;

        this.alOriginalMovie = new ArrayList<MovieListItem>();
        this.alOriginalMovie.addAll(alMovies);
        this.alMovies = new ArrayList<MovieListItem>();
        this.alMovies.addAll(alMovies);
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new MovieListFilter();
        }
        return filter;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    private class ViewHolder {
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            MovieSearchAdapter.ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.item_movie_search, null);

                holder = new MovieSearchAdapter.ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.tv_movie_title);


                convertView.setTag(holder);

            } else {
                holder = (MovieSearchAdapter.ViewHolder) convertView.getTag();
            }
            if (!alMovies.isEmpty() && position< alMovies.size()) {
                holder.name.setText(alMovies.get(position).getTitle());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return convertView;
    }

//    @Override
//    public int getCount() {
//        if (alMovies == null) {
//            return 0;
//        } else
//            return alMovies.size();
//    }

    private class MovieListFilter extends Filter {
        @Override
        synchronized protected FilterResults performFiltering(CharSequence constraint) {
            try {
//                Utils.log("Siddhesh Check performFiltering: " + constraint);
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    synchronized (results) {
                        if (alMovies != null) {
                            alMovies.clear();
                            alMovies = new ArrayList<>();
                        }
                        if (constraint.length() > 2) {
                            alMovies = movieSearchAPI.autocomplete(context, constraint.toString(), context.getString(R.string.app_key));
                            results.values = alMovies;
                            results.count = alMovies.size();
                        }
                        return results;
                    }
                } else
                    return null;
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }


        @SuppressWarnings("unchecked")
        @Override
        synchronized protected void publishResults(CharSequence constraint, FilterResults results) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                if (results != null) {
                    try {
                        ArrayList<MovieListItem> filterList = (ArrayList<MovieListItem>) results.values;
                        getContext();
                        if (filterList != null && results.count > 0 && filterList != null && filterList.size() > 0) {
                            clear();
                            for (MovieListItem people : filterList) {
                                add(people);
                            }
                            notifyDataSetChanged();
                        }
                    } catch (ConcurrentModificationException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }
}