/*
 *
 *     Copyright 2013-2015 Yohann Bianchi
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *     or see <http://www.gnu.org/licenses/>.
 *
 */

package com.zion.htf.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zion.htf.Application;
import com.zion.htf.R;

public class InfoActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
	private static final String TAG = "InfoActivity";
	private static final Context  context = Application.getContext();
	private static final String[] items   = InfoActivity.context.getResources().getStringArray(R.array.infos);
	private ListAdapter listAdapter;
    private ListView listview;

	@Override
	public void onCreate(Bundle savedState){
		super.onCreate(savedState);
        this.setContentView(R.layout.activity_info);

        // Set up ActionBar
		this.getSupportActionBar().setHomeButtonEnabled(true);

		// Set up ListView
		this.listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, InfoActivity.items);
        this.listview = (ListView)this.findViewById(R.id.info_listview);
		this.listview.setAdapter(this.listAdapter);
		this.listview.setOnItemClickListener(this);
	}

	@Override
    // TODO: Create an AboutActivity instead of displaying the about info in a WebView
	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		String item = (String)parent.getAdapter().getItem(position);

		Intent intent = null;

		int strId;
		if(item.equals(this.getString(R.string.action_donate))){
			intent = new Intent(this, DonateActivity.class);
		}
		else if(
                item.equals(this.getString(strId = R.string.info_edito)) ||
                item.equals(this.getString(strId = R.string.info_news)) ||
				item.equals(this.getString(strId = R.string.info_transport)) ||
				item.equals(this.getString(strId = R.string.info_camp)) ||
				item.equals(this.getString(strId = R.string.info_dogs)) ||
				item.equals(this.getString(strId = R.string.info_faq)) ||
				item.equals(this.getString(strId = R.string.info_about)) ||
                item.equals(this.getString(strId = R.string.info_volunteers))||
                item.equals(this.getString(strId = R.string.info_partners))||
                item.equals(this.getString(strId = R.string.info_open_source))||
				item.equals(this.getString(strId = R.string.info_village))
	    ){
			try{
				String name = this.getResources().getResourceEntryName(strId);
				intent = new Intent(this, InfoDetailsActivity.class);
				intent.putExtra(InfoDetailsActivity.name, name);
			}
			catch(Resources.NotFoundException e){
				Log.e(InfoActivity.TAG, String.format("Resource entry name not found for string \"%s\" (id: %d)", item, strId), e);
			}
		}

		if(null != intent){
			this.startActivity(intent);
			this.overridePendingTransition(R.anim.slide_and_fade_in_right, R.anim.slide_and_fade_out_left);
		}
	}
}
