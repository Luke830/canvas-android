/*
 * Copyright (C) 2017 - present  Instructure, Inc.
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
 */
package com.instructure.teacher.fragments

import android.graphics.Color
import com.instructure.canvasapi2.models.Course
import com.instructure.pandautils.fragments.BasePresenterFragment
import com.instructure.pandautils.utils.ViewStyler
import com.instructure.teacher.R
import com.instructure.teacher.factory.CourseBrowserEmptyViewFactory
import com.instructure.teacher.presenters.CourseBrowserEmptyPresenter
import com.instructure.pandautils.utils.ParcelableArg
import com.instructure.pandautils.utils.color
import com.instructure.teacher.viewinterface.CourseBrowserEmptyView
import instructure.androidblueprint.PresenterFactory
import kotlinx.android.synthetic.main.fragment_course_browser_empty.*

class CourseBrowserEmptyFragment: BasePresenterFragment<
        CourseBrowserEmptyPresenter,
        CourseBrowserEmptyView>(), CourseBrowserEmptyView {

    private var mCourse: Course by ParcelableArg(Course())

    override fun layoutResId(): Int {
        return R.layout.fragment_course_browser_empty
    }

    private fun setupToolbar(courseColor: Int?) {
        if(courseColor != null) {
            ViewStyler.themeToolbar(requireActivity(), toolbar, courseColor, Color.WHITE)
        }
    }

    override fun onRefreshFinished() {}

    override fun onRefreshStarted() {}

    override fun onReadySetGo(presenter: CourseBrowserEmptyPresenter?) {
        textCourseName.text = presenter?.course?.name
        textCourseTerm.text = presenter?.course?.term?.name
        setupToolbar(presenter?.course?.color)
    }

    override fun getPresenterFactory(): PresenterFactory<CourseBrowserEmptyPresenter> {
        return CourseBrowserEmptyViewFactory(mCourse)
    }

    override fun onPresenterPrepared(presenter: CourseBrowserEmptyPresenter?) {}

    companion object {
        @JvmStatic
        fun newInstance(course: Course) = CourseBrowserEmptyFragment().apply {
            mCourse = course
        }
    }
}
