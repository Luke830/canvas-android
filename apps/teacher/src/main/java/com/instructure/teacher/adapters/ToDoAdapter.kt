/*
 * Copyright (C) 2017 - present Instructure, Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.instructure.teacher.adapters

import android.content.Context
import android.view.View
import com.instructure.canvasapi2.models.ToDo
import com.instructure.teacher.holders.ToDoViewHolder
import com.instructure.teacher.interfaces.AdapterToFragmentCallback
import com.instructure.teacher.presenters.ToDoPresenter
import instructure.androidblueprint.SyncRecyclerAdapter

class ToDoAdapter(context: Context,
                  presenter: ToDoPresenter,
                  private val mCallback: AdapterToFragmentCallback<ToDo>) :
        SyncRecyclerAdapter<ToDo, ToDoViewHolder>(context,presenter) {

    override fun createViewHolder(v: View, viewType: Int) = ToDoViewHolder(v)

    override fun itemLayoutResId(viewType: Int) = ToDoViewHolder.HOLDER_RES_ID


    override fun bindHolder(model: ToDo, holder: ToDoViewHolder, position: Int) {
        context?.let { holder.bind(it, model, mCallback, position) }
    }
}
