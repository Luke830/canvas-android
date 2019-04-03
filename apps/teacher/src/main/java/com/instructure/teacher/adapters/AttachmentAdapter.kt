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
import com.instructure.canvasapi2.models.Attachment
import com.instructure.teacher.holders.AttachmentViewHolder
import com.instructure.teacher.presenters.SpeedGraderFilesPresenter
import instructure.androidblueprint.SyncRecyclerAdapter

class AttachmentAdapter(
        private val mContext: Context,
        presenter: SpeedGraderFilesPresenter,
        private val mCallback: (Attachment) -> Unit) : SyncRecyclerAdapter<Attachment, AttachmentViewHolder>(mContext, presenter){

    private var mSelectedAttachmentPosition = 0 //default of 0
    private val mSelectionCallback: (Int) -> Unit = {
        val oldPosition = mSelectedAttachmentPosition
        mSelectedAttachmentPosition = it
        notifyItemChanged(oldPosition)
        notifyItemChanged(mSelectedAttachmentPosition)
    }

    fun setSelectedPosition(newPosition: Int) = mSelectionCallback(newPosition)

    override fun bindHolder(model: Attachment, holder: AttachmentViewHolder, position: Int) {
        holder.bind(mContext, position, model, position == mSelectedAttachmentPosition, mCallback, mSelectionCallback)
    }

    override fun createViewHolder(v: View, viewType: Int) = AttachmentViewHolder(v)
    override fun itemLayoutResId(viewType: Int) = AttachmentViewHolder.HOLDER_RES_ID
}
