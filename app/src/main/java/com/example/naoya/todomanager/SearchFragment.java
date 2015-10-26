package com.example.naoya.todomanager;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class SearchFragment extends Fragment {

    private static final String TAG = SearchFragment.class.getSimpleName();
    private final SearchFragment self = this;

    private SearchView searchView;
    private String searchWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Menuの設定
        inflater.inflate(R.menu.menu_main, menu);

        // app:actionViewClass="android.support.v7.widget.SearchView"のItemの取得
        MenuItem menuItem = menu.findItem(R.id.search_menu_search_view);

        // ActionViewの取得
        this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        // 虫眼鏡アイコンを最初表示するかの設定
        this.searchView.setIconifiedByDefault(true);

        // Submitボタンを表示するかどうか
        this.searchView.setSubmitButtonEnabled(false);


        if (!this.searchWord.equals("")) {
            // TextView.setTextみたいなもの
            this.searchView.setQuery(this.searchWord, false);
        } else {
            // placeholderみたいなもの
            this.searchView.setQueryHint("ヒント");
        }
        this.searchView.setOnQueryTextListener(self.onQueryTextListener);
    }



    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String searchWord) {
            // SubmitボタンorEnterKeyを押されたら呼び出されるメソッド
            return self.setSearchWord(searchWord);
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            // 入力される度に呼び出される
            return false;
        }
    };

    private boolean setSearchWord(String searchWord) {
        ActionBar actionBar = ((AppCompatActivity)this.getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(searchWord);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        if (searchWord != null && !searchWord.equals("")) {
            // searchWordがあることを確認
            this.searchWord = searchWord;
        }
        // 虫眼鏡アイコンを隠す
        this.searchView.setIconified(false);
        // SearchViewを隠す
        this.searchView.onActionViewCollapsed();
        // Focusを外す
        this.searchView.clearFocus();
        return false;
    }


}
