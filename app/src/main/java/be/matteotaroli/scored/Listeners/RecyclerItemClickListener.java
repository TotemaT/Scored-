/*
Scored! allows to keep tracks of each player's score during a game.
Copyright (C) 2016  Matteo Taroli

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package be.matteotaroli.scored.Listeners;

import android.view.View;

/**
 * Interface that handles click on any RecyclerView item.
 */
public interface RecyclerItemClickListener {
    /**
     * Handles a click on a given item.
     *
     * @param v        View on which the user clicked.
     * @param position The position of the item in the list of items.
     */
    void onClick(View v, int position);
}
